package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.sleeper.Sleeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The object for Turtle(亀)
 * @author akinari.tsuji
 */
public class Turtle extends Animal implements Sleeper {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final Logger logger = LoggerFactory.getLogger(Turtle.class);


    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Turtle() {}

    public String getBarkWord() {
        return "pii?";
    }

    @Override
    public void sleep() {
        logger.debug("...Sleeping now");

    }
}
