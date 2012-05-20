package org.jdto.jdtowicketdemo;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.jdto.jdtowicketdemo.pages.TakeOrderPage;
import org.jdto.jdtowicketdemo.pages.ViewOrdersPage;

/**
 * This is the main web application.
 * @author juancavallotti
 */
public class JDTOPizzaApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();
        //integration with the spring framework.
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
        
        //mount the pages
        mountPages();
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return TakeOrderPage.class;
    }

    private void mountPages() {
        mountPage("/neworder", TakeOrderPage.class);
        mountPage("/orders", ViewOrdersPage.class);
    }

}
