package org.jdto.jdtowicketdemo.pages;

import java.util.ArrayList;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.jdto.DTOBinder;
import org.jdto.demo.dtos.PizzaDTO;
import org.jdto.demo.dtos.PizzaOrderForm;
import org.jdto.demo.service.PizzaOrderService;
import org.jdto.mergers.SumMerger;

/**
 * Page for taking new pizza orders.
 *
 * @author juancavallotti
 */
public class TakeOrderPage extends WebPage {

    private static final long serialVersionUID = 1L;
    
    @SpringBean
    private PizzaOrderService service;
    
    @SpringBean
    private DTOBinder binder;
    
    //references to some components
    private Form<PizzaOrderForm> orderForm;
    private FeedbackPanel feedback;
    
    public TakeOrderPage() {

        //add the menu
        addMenu();
        addPizzaOrderForm();
        addFeedbackPanel();
    }

    /**
     * Add the menu to the page.
     */
    private void addMenu() {
        add(new PropertyListView<PizzaDTO>("pizzaList", service.listAvailablePizzas()) {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final ListItem<PizzaDTO> item) {
                item.add(new Label("description"));
                item.add(new AjaxFallbackLink("addPizzaLink") {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        orderForm.getModelObject().getPizzas().add(item.getModelObject());
                        updateTotal();
                        target.add(orderForm);
                    }
                });
            }
        });
    }
    
    //the price of the order, used for display purposes.
    private double total;

    private void updateTotal() {
        total = 0;
        //reuse the sum merger.
        SumMerger merger = binder.getPropertyValueMerger(SumMerger.class);
        total = merger.mergeObjects(orderForm.getModelObject().getPizzas(), new String[] {"price"});
    }
    
    /**
     * Add the pizza order form.
     */
    private void addPizzaOrderForm() {
        PizzaOrderForm form = new PizzaOrderForm();

        form.setPizzas(new ArrayList<PizzaDTO>());

        //create the order form.
        orderForm = new Form<PizzaOrderForm>(
                "orderForm",
                new CompoundPropertyModel<PizzaOrderForm>(form));

        //for the case you want to add pizzas and you have already filled the form elements.
        //this is only necessary because I left out the menu list from the form.
        orderForm.add(new TextField("customerName").add(new BlurAjaxFormSubmitBehavior()));
        orderForm.add(new TextField("customerPhone").add(new BlurAjaxFormSubmitBehavior()));
        orderForm.add(new TextField("customerAddress").add(new BlurAjaxFormSubmitBehavior()));

        orderForm.add(new PropertyListView<PizzaDTO>("pizzas") {

            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(final ListItem<PizzaDTO> item) {
                item.add(new Label("description"));
                item.add(new Label("rating"));
                item.add(new AjaxFallbackLink("removeItemButton") {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        orderForm.getModelObject().getPizzas().remove(item.getModelObject());
                        updateTotal();
                        target.add(orderForm);
                    }
                });
            }
        });
        
        orderForm.add(new Label("orderTotal", new PropertyModel<Double>(this, "total")));
        orderForm.setOutputMarkupId(true);
        
        //add the submit button.
        orderForm.add(new AjaxSubmitLink("placeOrderButton") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                //save the order.
                PizzaOrderForm order = (PizzaOrderForm) form.getModelObject();
                boolean result = service.placeOrder(order);
                if (result) {
                    //navigate to the orders list page.
                    setResponsePage(ViewOrdersPage.class);
                } else {
                    //display the error.
                    error("Could not create the order.");
                    target.add(feedback);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                //process the error.
                error("Could not create the order.");
                target.add(feedback);
            }
        });
        
        add(orderForm);
    }

    private void addFeedbackPanel() {
        feedback = new FeedbackPanel("feedbackPanel");
        feedback.setOutputMarkupId(true);
        add(feedback);
    }
    
    private class BlurAjaxFormSubmitBehavior extends AjaxFormSubmitBehavior {
        private static final long serialVersionUID = 1L;

        public BlurAjaxFormSubmitBehavior() {
            super("onblur");
        }

        @Override
        protected void onSubmit(AjaxRequestTarget target) {
            //do nothing
        }

        @Override
        protected void onError(AjaxRequestTarget target) {
            //nothing here
        }

    }
}
