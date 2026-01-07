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

/**
 * The object for zombie(ゾンビ).
 * @author jflute
 */
public class Zombie extends Animal {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    protected final ZombieDiary zombieDiary = new ZombieDiary();

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Zombie() {
    }

    @Override
    protected int getInitialHitPoint() {
        return -1; // magic number for infinity hit point
    }

    public static class ZombieDiary {

        private int breatheInCount;

        public void countBreatheIn() {
            ++breatheInCount;
        }

        public int getBreatheInCount() {
            return breatheInCount;
        }
    }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    // TODO tsuji 元々オーバーライドしてたということは...bark()したときの息を吸うでcount処理が入ってた... by jflute (2026/01/07)
    // わけですが、いまZombieをnewしてbark()してもcountされないのでは？
    // Animal@bark() から BarkingProcess に処理が移って、BarkingProcessのbreatheIn()が呼ばれるけど...
    // ↓ここのオーバーライドでもない単発メソッドのbreatheIn()が呼ばれることはない。(デグレが発生している)
    // Zombieの挙動をしっかり復旧しましょう。
    protected void breatheIn() {
        barkingProcess.breatheIn();
        zombieDiary.countBreatheIn();
    }

    // 元々のコードがこちら: AnimalにいたbreatheIn()をオーバーライドしていた。
    // そして、Animalのbark()が↓このオーバーライドのbreatheIn()を呼び出していた。
    //@Override
    //protected void breatheIn() {
    //    barkingProcess.breatheIn();
    //    zombieDiary.countBreatheIn();
    //}
    
    @Override
    public String getBarkWord() {
        return "uooo"; // what in English?
    }

    // ===================================================================================
    //                                                                           Hit Point
    //                                                                           =========
    @Override
    public void downHitPoint() {
        // do nothing, infinity hit point
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public ZombieDiary getZombieDiary() {
        return zombieDiary;
    }
}
