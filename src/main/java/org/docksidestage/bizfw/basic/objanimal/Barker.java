package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Barker {

    private static final Logger logger = LoggerFactory.getLogger(Barker.class);
    private final Animal animal;

    public Barker(Animal animal) {
        this.animal = animal;
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    public BarkedSound bark() {
        breatheIn();
        prepareAbdominalMuscle();
        String barkWord = animal.getBarkWord();
        BarkedSound barkedSound = doBark(barkWord);
        return barkedSound;
    }
    protected void breatheIn() { // actually depends on barking
        logger.debug("...Breathing in for barking"); // dummy implementation
        animal.downHitPoint();
    }
    protected void prepareAbdominalMuscle() { // also actually depends on barking
        logger.debug("...Using my abdominal muscle for barking"); // dummy implementation
        animal.downHitPoint();
    }
    protected BarkedSound doBark(String barkWord) {
        animal.downHitPoint();
        return new BarkedSound(barkWord);
    }
}