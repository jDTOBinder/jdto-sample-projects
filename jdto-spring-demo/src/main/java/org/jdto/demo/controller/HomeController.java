package org.jdto.demo.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.jdto.demo.dtos.PizzaDTO;
import org.jdto.demo.dtos.PizzaOrderForm;
import org.jdto.demo.service.PizzaOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author juancavallotti
 */
@Controller
@Scope("session")
public class HomeController implements Serializable {
    private static final long serialVersionUID = 1L;
    
    List<PizzaDTO> pizzas;
    
    private PizzaOrderForm currentOrder;
    
    @PostConstruct
    private void init() {
        pizzas = service.listAvailablePizzas();
        currentOrder = new PizzaOrderForm();
        currentOrder.setPizzas(new ArrayList<PizzaDTO>());
    }
     
    @Autowired
    private PizzaOrderService service;
    
    @RequestMapping(value={"/", "/home.htm"},method=RequestMethod.GET)
    public ModelAndView home() {
        return home(false);
    }
    private ModelAndView home(boolean redirect) {
        
        String view = redirect ? "redirect:home.htm" : "home";
        
        return new ModelAndView(view, buildResponseModel());
    }
    
    @RequestMapping(value="/addToOrder")
    public ModelAndView addToOrder(@RequestParam("pizzaID") Long pizzaId) {
        
        if (pizzaId == null) {
            return home(true);
        }
        
        for (PizzaDTO pizzaDTO : pizzas) {
            if (pizzaId.equals(pizzaDTO.getPizzaId())) {
                currentOrder.getPizzas().add(pizzaDTO);
            }
        }
        
        
        return home(true);
    }
    
    @RequestMapping("/removeFromOrder")
    public ModelAndView removeFromOrder(@RequestParam("itemID") Integer item) {
        if (item != null && item < currentOrder.getPizzas().size()) {
            currentOrder.getPizzas().remove((int)item);
        }
        return home(true);
    }
    
    @RequestMapping("/placeOrder")
    public ModelAndView placeOrder(@ModelAttribute("order") PizzaOrderForm form) {
        
        boolean placed = service.placeOrder(form);
        
        if (placed) {
            //redirect to the orders list
            
            //reset the form
            init();
            return new ModelAndView("redirect:/orders.htm");
        } else {
            HashMap<String, Object> model = buildResponseModel();
            model.put("valid", Boolean.FALSE);
            return new ModelAndView("home", model);
        }
        
    }
    
    private HashMap<String, Object> buildResponseModel() {
        HashMap<String, Object> modelValues = new HashMap<String, Object>();
        modelValues.put("pizzas", pizzas);
        modelValues.put("total", calculateOrderTotal());
        modelValues.put("valid", Boolean.TRUE);
        return modelValues;
    }

    private Object calculateOrderTotal() {
        double total = 0.0;
        
        for (PizzaDTO pizzaDTO : currentOrder.getPizzas()) {
            total += pizzaDTO.getPrice();
        }
        
        return String.format("$ %.2f", total);
    }
    
    @ModelAttribute("order")
    public PizzaOrderForm getOrder() {
        return currentOrder;
    }
    
}
