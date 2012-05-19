package org.jdto.demo.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.Validator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.jdto.DTOBinder;
import org.jdto.demo.dtos.OrderDTO;
import org.jdto.demo.dtos.PizzaDTO;
import org.jdto.demo.dtos.PizzaOrderForm;
import org.jdto.demo.entities.Pizza;
import org.jdto.demo.entities.PizzaOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author juancavallotti
 */
@Service
@Transactional
class PizzaOrderServiceImpl implements PizzaOrderService, Serializable {

    private static final long serialVersionUID = 1L;
    @Autowired
    private HibernateTemplate hibernate;
    @Autowired
    private DTOBinder binder;
    @Autowired
    private Validator validator;
    
    
    @Override
    public List<PizzaDTO> listAvailablePizzas() {

        DetachedCriteria criteria = DetachedCriteria.forClass(Pizza.class);
        criteria.addOrder(Order.asc("pizzaName"));

        return binder.bindFromBusinessObjectList(PizzaDTO.class, hibernate.findByCriteria(criteria));
    }

    @Override
    public boolean placeOrder(PizzaOrderForm form) {

        //may validate with hibernate validator
        if (!validator.validate(form).isEmpty()) {
            return false;
        }

        PizzaOrder order = binder.extractFromDto(PizzaOrder.class, form);


        order.setOrderDate(new Date());
        order.setPizzas(new ArrayList<Pizza>());

        for (PizzaDTO pizzaDTO : form.getPizzas()) {
            Pizza pizza = hibernate.get(Pizza.class, pizzaDTO.getPizzaId());
            order.getPizzas().add(pizza);
        }

        hibernate.save(order);

        return true;
    }

    @Override
    public List<OrderDTO> listOrders() {
        DetachedCriteria criteria = DetachedCriteria.forClass(PizzaOrder.class);
        criteria.addOrder(Order.desc("orderDate"));

        return binder.bindFromBusinessObjectList(OrderDTO.class, hibernate.findByCriteria(criteria));
    }
}
