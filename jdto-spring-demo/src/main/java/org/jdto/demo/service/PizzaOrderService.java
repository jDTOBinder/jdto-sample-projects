package org.jdto.demo.service;

import java.util.List;
import org.jdto.demo.dtos.OrderDTO;
import org.jdto.demo.dtos.PizzaDTO;
import org.jdto.demo.dtos.PizzaOrderForm;

/**
 *
 * @author Juan Alberto Lopez Cavallotti
 */
public interface PizzaOrderService {
    
    
    List<PizzaDTO> listAvailablePizzas();

    boolean placeOrder(PizzaOrderForm form);
    
    List<OrderDTO> listOrders();
}
