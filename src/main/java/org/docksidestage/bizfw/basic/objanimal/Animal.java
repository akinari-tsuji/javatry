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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO tsuji せっっかくなのでauthorを by jflute (2026/01/07)
/**
 * The object for animal(動物).
 * @author jflute
 */
public abstract class Animal implements Loudable {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    // TODO tsuji loggerがすでにunusedになっている by jflute (2026/01/07)
    private static final Logger logger = LoggerFactory.getLogger(Animal.class);
    // TODO tsuji インスタンス変数は Attribute でお願いします。 by jflute (2026/01/07)
    protected final BarkingProcess barkingProcess = new BarkingProcess(this);

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected int hitPoint; // is HP

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

    // TODO tsuji 修行++: publicになっちゃいましたが、protectedに戻せるようにしましょう by jflute (2026/01/07)
    public abstract String getBarkWord();

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    protected void upHitPoint() {
        ++hitPoint;
        if (hitPoint > 10) {
            throw new IllegalStateException("I have slept too much that I can't more " + getBarkWord());
        }
    }

    // TODO tsuji 修行#: publicになっちゃいましたが、protectedに戻せるようにしましょう by jflute (2026/01/07)
    // #1on1: protected は、「サブクラス、もしくは、同じパッケージ」なら呼べるという可視性
    // なので、現状だと、AnimalとBarkingProcessは別パッケージなので、protectedのままだと呼べない。
    // ということで、publicにされたのだと思われますが、カプセル化的には避けたいところ。
    // 外部からは呼ばれたくないけど、内部のBarkingProcessからは呼ばれたい...
    // でもBarkingProcessもパッケージ的には外部扱いになっちゃう。(近いのに)
    public void downHitPoint() {
        --hitPoint;
        if (hitPoint <= 0) {
            throw new IllegalStateException("I'm very tired, so I want to sleep" + getBarkWord());
        }
    }

    // ===================================================================================
    //                                                                               Loud
    //                                                                              ======
    @Override
    public String soundLoudly() {
        return barkingProcess.bark().getBarkWord();
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getHitPoint() {
        return hitPoint;
    }

    public void hoge() {}
}
