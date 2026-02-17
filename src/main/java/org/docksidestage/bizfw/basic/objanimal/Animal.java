/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.objanimal;

import org.docksidestage.bizfw.basic.objanimal.barking.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkingProcess;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;

// TODO tsuji せっっかくなのでauthorを by jflute (2026/01/07)
/**
 * The object for animal(動物).
 * @author jflute
 * @author akinari.tsuji
 */
public abstract class Animal implements Loudable {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    // done tsuji loggerがすでにunusedになっている by jflute (2026/01/07)
    // private static final Logger logger = LoggerFactory.getLogger(Animal.class);
    // done tsuji インスタンス変数は Attribute でお願いします。 by jflute (2026/01/07)

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final BarkingProcess barkingProcess = new BarkingProcess(createBarkingContext());
    protected int hitPoint; // is HP

    /**
     * BarkingContextを作成するメソッド
     * これを通じて、privateなdownHitPointを呼び出させる
     */
    private BarkingContext createBarkingContext() {
        return new BarkingContext(this);
    }

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Animal() {
        hitPoint = getInitialHitPoint();
    }

    protected int getInitialHitPoint() {
        return 10; // as default
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    public BarkedSound bark() {
        return barkingProcess.bark();
    }
    
    // #1on1: BarkingContextから...Animalしかnewできない仲介役を作るまで (2026/02/04)
    // 1: まずstaticのインナークラスで、privateコンストラクターにする。
    // (privateでもインナークラスなら、外側のクラスがnewできる)
    // e.g. public static class InnerBarkingContext {
    //          private InnerBarkingContext(Animal animal) {
    //
    // 2: さらに、それをstaticじゃなく、インスタンス所属のインナークラスにする。
    // 直接Animalのメソッドを呼べる。Animalをコンストラクターで受け取らなくていい。
    // e.g. public class InnerBarkingContext {
    //          private InnerBarkingContext() {
    //
    // でも、1と2は、なんか隠蔽はできてるけど、BarkingProcessがAnimalの持ち物に深く依存する。
    //
    // 3: したら、interfaceを用意する (BarkingProcessを呼ぶ時はそのインターフェースを満たす)
    // private class InnerBarkingContext implements BarkingRequiredCall {
    // ↑こうすると、class自体もprivateにできちゃう。BarkingProcess側で直接参照しないから。
    // (インスタンス自体は、privateとかは関係ないので。privateはあくまでコンパイル時の話)
    //
    // 4: もはや、Animalで閉じた具象クラスで、一箇所でしか使わないので、名前をつける必要もない。
    // 無名インナークラスにしてしまっても良い。(これは、private class, private constructor と同じ)
    /* e.g.
    return new BarkingRequiredCall() {
        @Override
        public String getBarkWord() {
            return Animal.this.getBarkWord();
        }
        @Override
        public void downHitPoint() {
            Animal.this.downHitPoint();
        }
    };
     */
    // 5: そして、そもそもgetBarkWord()は引数で渡してしまえば良いものなの、Callに入れる必要がない。
    // すると、Callのメソッドは、downHitPoint()のみ。
    /*
     return new BarkingRequiredCall() {
        @Override
        public void downHitPoint() {
            Animal.this.downHitPoint();
        }
     }
     */
    // 6: すると、Java8から入ったLambda式の形に当てはまる。インターフェースにメソッドが一個。
    /*
    return () -> {
        downHitPoint();
    }
    
    　↓ (expression styleに)
    
    return () -> downHitPoint();
    
    　↓ (Factoryメソッドやめる)
    
    ... = new BarkingProcess(getBarkWord(), () -> downHitPoint());
     */
    // 補足: 5のところで、getBarkWord()を外してメソッドを一つにしましたが、
    // 仲介役を個別に用意すれば、それぞれLambda式で書くこともできる。
    // e.g. ... = new BarkingProcess(() -> getBarkWord(), () -> downHitPoint());
    // これをやるかどうかは別。あまりに引数多くなると、逆に見辛くなるかも。

    // done tsuji 修行++: publicになっちゃいましたが、protectedに戻せるようにしましょう by jflute (2026/01/07)
    // #1on1: BarkingProcessは、getBarkWord()を呼びたいのではなく、barkWordが欲しいだけ。
    // Animalに対して、更新とかはせず、(文字列の)参照だけなので、メソッドを呼ぶ必要はない。
    // 文字列そのものを何かしらでもらってしまえば良い。「引数/戻り値デザイン」。
    // 「あっ、なんだ、そんな簡単なことか!?」って体験の思い出を得ることが大事。灯台下暗し。
    protected abstract String getBarkWord();

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    protected void upHitPoint() {
        ++hitPoint;
        if (hitPoint > 10) {
            throw new IllegalStateException("I have slept too much that I can't more " + getBarkWord());
        }
    }

    // done tsuji 修行#: publicになっちゃいましたが、protectedに戻せるようにしましょう by jflute (2026/01/07)
    // #1on1: protected は、「サブクラス、もしくは、同じパッケージ」なら呼べるという可視性
    // なので、現状だと、AnimalとBarkingProcessは別パッケージなので、protectedのままだと呼べない。
    // ということで、publicにされたのだと思われますが、カプセル化的には避けたいところ。
    // 外部からは呼ばれたくないけど、内部のBarkingProcessからは呼ばれたい...
    // でもBarkingProcessもパッケージ的には外部扱いになっちゃう。(近いのに)
    // done jflute やったこと by akinari.tsuji (2026/02/04)
    // 1. Contextクラスを作成する。メンバ変数にAnimalのインスタンスを持つ。コンストラクタをpackage-privateにすることで、他での作成・利用を回避する。
    // 2. Animalクラスにpackage-privateなメソッド（downHitPointForContext)を作成し、privateなdownHitPointをラップする
    // 3. Contextクラスに、publicなdownHitPointメソッドを作成し、内部でanimal.downHitPointContextを呼び出すようにする
    // 4. BarkingProcessクラスはコンストラクタで、Contextのインスタンスを受け取り、これを通じてanimalの体力を減らすようにする
    // 5. 最後に、AnimalがBarkingProcessのインスタンスを作成できるように、Contextを作成するprivateなメソッドを作成し、これを使ってBarkingProcessを作るようにする

    // やりたかったこと：吠える、という振る舞いの中で、動物の体力を消費させたかった
    // Before
    // - BarkingProcessのbarkを呼び出す
    // - barkの中でAnimalの体力を減らしたいがprotectedなので呼び出せない
    // - publicにすると、誰でも体力を減らせてしまう...

    // After
    // - BarkingProcessのbarkを呼び出す
    // - Contextを経由して体力を減らす
    // - 他がAnimalの体力を減らすには？
    //     1. Contextが必要(contextを使わないと、downHitPointを呼び出せない）
    //     2. Contextを作成できるのはobjanimalパッケージ中のみ（コンストラクタがpackage-privateなので）
    //     -> objanimal中で、Animalインスタンスを用いて、Contextを作成し、downHitPointを呼び出せば、減らせてはしまう...
    // これ以上、防ぐ方法は（あるかもですが）、一旦、理解追いついてないのでここまでになります...

    protected void downHitPoint() {
        --hitPoint;
        if (hitPoint <= 0) {
            throw new IllegalStateException("I'm very tired, so I want to sleep" + getBarkWord());
        }
    }

    void downHitPointForContext() { downHitPoint(); }

    // ===================================================================================
    //                                                                               Loud
    //                                                                              ======
    // TODO akinari.tsuji getBarkWordをprotectedにするために、bark()の引数でgetBarkWordを渡すようにしたら、↓がキモくなってしまった (2026/02/04)
    // そもそも、barkingProcess.barkの返り値が、getBarkWord()を呼び出せることまで知っていていいのかな...メソッドチェーンあんまり良くないっていうけど
    // -> barkと同じなのでそっちを使えばいいや, でもこれだとbark()とSoundLoudlyでほとんど違いがないや...
    @Override
    public String soundLoudly() {
        // return barkingProcess.bark(getBarkWord()).getBarkWord();
        BarkedSound barkedSound = bark();
        return barkedSound.getBarkWord();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getHitPoint() {
        return hitPoint;
    }

    public void hoge() {}
}


