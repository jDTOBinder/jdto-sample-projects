package org.jdto.demo;

import javax.annotation.PostConstruct;
import org.jdto.demo.entities.Pizza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author juancavallotti
 */
class InitialDataLoader {
    
    private static final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);
    
    @Autowired
    private HibernateTemplate template;
    
    @PostConstruct
    @Transactional(propagation= Propagation.REQUIRES_NEW)
    private void init() {
        createPizzas();
    }

    private void createPizzas() {
        logger.info("Creating initial set of pizzas!");
        
        Pizza pepperoni = new Pizza("Pepperoni Pizza", 4, 8.50);
        Pizza mozzarella = new Pizza("Mozzarella Pizza", 3, 7.50);
        Pizza special = new Pizza("Special Ham Pizza", 5, 10.0);
        Pizza onion = new Pizza("Onion Pizza", 4, 9.0);
        Pizza anchovy = new Pizza("Anchovy Pizza", 2, 7.95);
        Pizza california = new Pizza("California Eggs & Bacon", null, 14.75);
        
        template.save(pepperoni);
        template.save(mozzarella);
        template.save(special);
        template.save(onion);
        template.save(anchovy);
        template.save(california);
    }
}
