package org.jdto.demo.controller;

import java.io.Serializable;
import org.jdto.demo.service.PizzaOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author juancavallotti
 */
@Controller
public class OrdersController implements Serializable {
    
    @Autowired
    private PizzaOrderService ordersService;
    
    @RequestMapping({"/orders.htm", "/orders"})
    public ModelAndView viewOrders() {
        return new ModelAndView("orders", "orderList", ordersService.listOrders());
    }
    
}
