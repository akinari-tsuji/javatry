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
package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author jflute
 * @author akinari.tsuji
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // TODO tsuji [いいね] インスタンス変数とコンストラクターでのset順序が同じなのでわかりやすい by jflute (2025/08/15)
    private final int displayPrice; // written on ticket, park guest can watch this
    private final int entranceLimit;
    // TODO tsuji Countという概念自体は一つしか無いので、複数形にしない方がいいかなと by jflute (2025/08/15)
    // もし、EntrancesだったらCountという言葉を省略した入園回数という概念にはなると思う。
    // TODO tsuji [読み物課題] プログラマーに求められるデザイン脳 by jflute (2025/08/15)
    // https://jflute.hatenadiary.jp/entry/20170623/desigraming
    private int totalEntranceCounts;
    private boolean alreadyIn;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    // TODO tsuji [いいね] 複数のコンストラクターに対して、コメントで役割を書いているのGood by jflute (2025/08/15)
    // #1on1: staticのFactoryメソッドのお話もちょこっと
    // TODO tsuji 一方で、Booth側で実際にOneDayでもこっちがnewされていない問題 by jflute (2025/08/15)
    // one-day ticket
    public Ticket(int displayPrice) {
        this.displayPrice = displayPrice;
        this.entranceLimit = 1;
        this.totalEntranceCounts = 0;
        this.alreadyIn = false;
    }

    // two or more day ticket
    public Ticket(int displayPrice, int entranceLimit) {
        this.displayPrice = displayPrice;
        this.entranceLimit = entranceLimit;
        this.totalEntranceCounts = 0;
        this.alreadyIn = false;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void doInPark() {
        if (totalEntranceCounts >= entranceLimit) {
            throw new IllegalStateException("This ticket is already exhausted: displayedPrice=" + displayPrice);
        }
        totalEntranceCounts++;
        this.alreadyIn = true;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return displayPrice;
    }

    public int getRemainingEntranceCounts() { return entranceLimit - totalEntranceCounts; }

    public int getEntranceLimit() { return entranceLimit; }

    public boolean isAlreadyIn() { return alreadyIn; }
}
