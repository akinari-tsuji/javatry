package org.docksidestage.bizfw.basic.objanimal.barking;

import org.docksidestage.bizfw.basic.objanimal.BarkingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// done tsuji せっかくなのでjavadoc by jflute (2025/12/17)
// 説明を書いてみたものの、微妙な感じが否めないのはなぜでしょう...笑
// #1on1: クラス名だけよりかは、まだ日本語で書かれた概要みたいな一行があるのは嬉しい (2026/01/07)
//
// e.g.
//  吠える能力をまとめたクラス。<br>
//  吠える処理の流れをここでまとめている。吠える固有のサブ処理もここに実装(e.g. 息を吸うなど)。
//  元々Animalにあった処理を、抽象クラス肥大化を避けるために別クラスに切り出した。
//  なので内部クラス扱いとして、外部ではあまり直接使わないように。
//
//  (概要)
//  (クラスに何を実装するのか？)
//  (クラスを作るきっかけ)
//  (使用上の注意点)
//
// さんざんJavaDocを書いてきて試行錯誤たくさんしてきた経験と、
// さんざんJavaDocを読んできて何が書いてあると嬉しいって経験。
// なので、せめてjavatryでは、JavaDocをがっつり書くチャレンジしてもらいたい。
// 最初は、書きすぎるくらい書いていいと思っている。
// 書きすぎて「ここは無駄だな」って思う経験が大事。
// そうするとだんだん必要なことだけ書けるようになってくる。
// (ちょっとAI時代の自然言語能力の話も少し)

/**
 * 吠える能力をまとめたクラス
 * @author akinari.tsuji
 */
public class BarkingProcess {

    private static final Logger logger = LoggerFactory.getLogger(BarkingProcess.class);
    
    // #1on1: つじさんの言う通り、他のAnimalじゃないクラスがbarkしたいこと万が一あったとき... (2025/12/17)
    // 再利用できない状態になってしまうので、理想的には Barker の Animal 依存を排除したいところではある。
    // 優先度は低くてOK。いずれ最終的には依存が排除できたらいいね、という感じ。
    private final BarkingContext barkingContext;

    public BarkingProcess(BarkingContext barkingContext) {
        this.barkingContext = barkingContext;
    }
    
    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    public BarkedSound bark() {
        breatheIn();
        prepareAbdominalMuscle();
        String barkWord = barkingContext.getBarkWord();
        BarkedSound barkedSound = doBark(barkWord);
        return barkedSound;
    }

    public void breatheIn() { // actually depends on barking
        logger.debug("...Breathing in for barking"); // dummy implementation
        // animal.downHitPoint();
        barkingContext.downHitPoint();
    }

    protected void prepareAbdominalMuscle() { // also actually depends on barking
        logger.debug("...Using my abdominal muscle for barking"); // dummy implementation
        // animal.downHitPoint();
        barkingContext.downHitPoint();
    }

    protected BarkedSound doBark(String barkWord) {
        // animal.downHitPoint();
        barkingContext.downHitPoint();
        return new BarkedSound(barkWord);
    }
}