package org.jdto.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author juancavallotti
 */
@Entity
@Table(name="pizza_db")
public class Pizza extends PersistentObject {
    private static final long serialVersionUID = 1L;
    
    private String pizzaName;
    private Integer rating;
    private Double price;

    public Pizza() {
    }

    public Pizza(String pizzaName, Integer rating, Double price) {
        this.pizzaName = pizzaName;
        this.rating = rating;
        this.price = price;
    }
    
    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    
}
