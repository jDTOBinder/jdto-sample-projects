package org.jdto.demo.dtos;

import java.io.Serializable;
import org.jdto.annotation.Source;
import org.jdto.mergers.DateFormatMerger;
import org.jdto.mergers.MethodCallMerger;
import org.jdto.mergers.SumMerger;

/**
 *
 * @author juancavallotti
 */
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    @Source("whoOrderedName")
    private String customerName;
    
    private String customerAddress;
    
    @Source("contactNumber")
    private String customerPhone;
    
    @Source(value="orderDate", merger=DateFormatMerger.class, mergerParam="MM/dd/yyyy hh:mm")
    private String orderDate;
    
    @Source(value="pizzas", merger=SumMerger.class, mergerParam="price")
    private Double orderPrice;
    
    /** New feature in 1.1 */
    @Source(value="pizzas",  merger=MethodCallMerger.class, mergerParam="size")
    private int pizzaCount;
    
    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPizzaCount() {
        return pizzaCount;
    }

    public void setPizzaCount(int pizzaCount) {
        this.pizzaCount = pizzaCount;
    }
    
}
