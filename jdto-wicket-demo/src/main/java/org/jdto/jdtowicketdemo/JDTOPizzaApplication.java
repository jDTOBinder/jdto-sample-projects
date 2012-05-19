package org.jdto.jdtowicketdemo;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;
import org.jdto.jdtowicketdemo.pages.TakeOrderPage;

/**
 * This is the main web application.
 * @author juancavallotti
 */
public class JDTOPizzaApplication extends WebApplication {

    @Override
    public Class<? extends Page> getHomePage() {
        return TakeOrderPage.class;
    }

}
