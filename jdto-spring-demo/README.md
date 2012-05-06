Spring MVC with jDTO Binder.
============================

This project uses a basic Spring Web MVC setup with Hibernate and H2 in-memory database. You are free to use this project as a starting point for bigger projects.

This simple application has the following domain model:

The Pizza class represents a simple product.

```java
    @Entity
    @Table(name="pizza_db")
    public class Pizza extends PersistentObject {
        private static final long serialVersionUID = 1L;

        private String pizzaName;
        private Integer rating;
        private Double price;

        ...//getters && setters
    }
```

Then we have the PizzaOrder which represents an order from a customer:

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

        ...//getters and setters
    }

We use the following DTOs to isolate the domain model from the presentation tier:

The order DTO is used to populate a ui table showing the list of orders:

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

        ... //getters and setters
    }

The Pizza DTO is used to display the list of pizzas available for ordering:

    public class PizzaDTO implements Serializable {

        @Source("id")
        private Long pizzaId;


        @Sources(value={@Source("pizzaName"), 
            @Source("price")}, merger=StringFormatMerger.class, mergerParam="%s ($ %.2f)")
        private String description;

        private double price;
        private int rating;

        ...//getters and setters
    }

Finally, we use a pizza order form, to contain the data used to create new pizza orders. This DTO is used with reverse DTO Binding.

    public class PizzaOrderForm implements Serializable {

        @NotEmpty //validation annotation
        @Source("whoOrderedName")
        private String customerName;

        @NotEmpty //validation
        @Source("contactNumber")
        private String customerPhone;

        @NotEmpty //validation
        private String customerAddress;

        @Size(min=1) //validation
        @DTOTransient
        private List<PizzaDTO> pizzas;

        ...//getters and setters.
    }

This DTO includes validation annotations from the HibernateValidator framework.