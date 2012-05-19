package org.jdto.demo.entities;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author juancavallotti
 */
@Entity
@Table(name="orders")
public class PizzaOrder extends PersistentObject {
    
    private static final long serialVersionUID = 1L;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    
    private String whoOrderedName;
    
    private String customerAddress;
    
    private String contactNumber;
    
    @ManyToMany
    private List<Pizza> pizzas;

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public String getWhoOrderedName() {
        return whoOrderedName;
    }

    public void setWhoOrderedName(String whoOrderedName) {
        this.whoOrderedName = whoOrderedName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
    
}
