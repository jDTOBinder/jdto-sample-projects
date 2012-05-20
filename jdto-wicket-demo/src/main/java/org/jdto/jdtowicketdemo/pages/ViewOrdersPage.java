package org.jdto.jdtowicketdemo.pages;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.jdto.demo.dtos.OrderDTO;
import org.jdto.demo.service.PizzaOrderService;

/**
 * This page is designed to show all the pizza orders.
 * @author juancavallotti
 */
public class ViewOrdersPage extends WebPage {
    private static final long serialVersionUID = 1L;

    @SpringBean
    private PizzaOrderService service;
    
    public ViewOrdersPage() {
        addOrdersTable();
    }

    private void addOrdersTable() {
                
        DataProvider provider = new DataProvider();
        
        List<IColumn<OrderDTO>> cols = new LinkedList<IColumn<OrderDTO>>();
        cols.add(new PropertyColumn<OrderDTO>(Model.of("Customer Name"), "customerName", "customerName"));
        cols.add(new PropertyColumn<OrderDTO>(Model.of("Customer Address"), "customerAddress", "customerAddress"));
        cols.add(new PropertyColumn<OrderDTO>(Model.of("Customer Phone"), "customerPhone", "customerPhone"));
        cols.add(new PropertyColumn<OrderDTO>(Model.of("Order Date"), "date", "orderDate"));
        cols.add(new PropertyColumn<OrderDTO>(Model.of("Pizzas"), "pizzaCount", "pizzaCount"));
        cols.add(new PropertyColumn<OrderDTO>(Model.of("Price"), "orderPrice", "orderPrice"));
        
        AjaxFallbackDefaultDataTable<OrderDTO> table = new AjaxFallbackDefaultDataTable<OrderDTO>("ordersTable", cols, provider, 15);
        add(table);
    }
    
    /**
     * Data provider to show all the orders. In an ideal world, this would be
     * paged on the database.
     */
    private class DataProvider extends SortableDataProvider<OrderDTO> {
        private static final long serialVersionUID = 1L;
        
        List<OrderDTO> dtos;

        public DataProvider() {
            dtos = service.listOrders();
        }
        
        @Override
        public Iterator<? extends OrderDTO> iterator(int first, int count) {
            return dtos.listIterator(first);
        }

        @Override
        public int size() {
            return dtos.size();
        }

        @Override
        public IModel<OrderDTO> model(OrderDTO object) {
            return Model.of(object);
        }
        
    }
    
}
