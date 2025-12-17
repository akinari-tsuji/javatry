package org.docksidestage.bizfw.basic.objanimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO tsuji せっかくなのでjavadoc by jflute (2025/12/17)
public class Barker {

    private static final Logger logger = LoggerFactory.getLogger(Barker.class);
    
    // #1on1: つじさんの言う通り、他のAnimalじゃないクラスがbarkしたいこと万が一あったとき... (2025/12/17)
    // 再利用できない状態になってしまうので、理想的には Barker の Animal 依存を排除したいところではある。
    // 優先度は低くてOK。いずれ最終的には依存が排除できたらいいね、という感じ。
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