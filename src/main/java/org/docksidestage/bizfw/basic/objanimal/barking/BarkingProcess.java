package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO done tsuji せっかくなのでjavadoc by jflute (2025/12/17)
// 説明を書いてみたものの、微妙な感じが否めないのはなぜでしょう...笑

/**
 * 吠える能力をまとめたクラス
 * @author akinari.tsuji
 */
public class BarkingProcess {

    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);
    
    // #1on1: つじさんの言う通り、他のAnimalじゃないクラスがbarkしたいこと万が一あったとき... (2025/12/17)
    // 再利用できない状態になってしまうので、理想的には Barker の Animal 依存を排除したいところではある。
    // 優先度は低くてOK。いずれ最終的には依存が排除できたらいいね、という感じ。
    private final Animal animal;

    public BarkingProcess(Animal animal) {
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
    public void breatheIn() { // actually depends on barking
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