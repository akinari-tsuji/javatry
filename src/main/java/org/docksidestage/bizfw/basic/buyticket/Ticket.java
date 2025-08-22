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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author jflute
 * @author akinari.tsuji
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // done tsuji [いいね] インスタンス変数とコンストラクターでのset順序が同じなのでわかりやすい by jflute (2025/08/15)
    // ありがとうございます！ by akinari.tsuji
//    private final int displayPrice; // written on ticket, park guest can watch this
//    private final int entranceLimit;
    private final TicketType ticketType;
    // done tsuji Countという概念自体は一つしか無いので、複数形にしない方がいいかなと by jflute (2025/08/15)
    // もし、EntrancesだったらCountという言葉を省略した入園回数という概念にはなると思う。
    // TODO [修正] jflute 1on1でご指摘いただいたようにtotalEntrancesCountに修正しました akinari.tsuji  (2025/08/22)
    // 複数回入園することを表現しながら、値としては一つであることを表現
    // done tsuji [読み物課題] プログラマーに求められるデザイン脳 by jflute (2025/08/15)
    // TODO jflute [読み物課題メモ] akinari.tsuji  (2025/08/22)
    // 1. 命名デザイン : 周りの名前・全体の中での位置付けからつける
    // 2. 構図デザイン：人が見て・手を加えるための構造デザイン
    // 3. コメントデザイン：読む人が嬉しいコメントを残す
    // 4. DBデザイン：誤魔化しが効かない
    // TODO jflute [質問] 後から変更がしやすいDBというのは開発されなかったのでしょうか？　akinari.tsuji  (2025/08/22)
    // 変更しにくいというのはDB全体の性質なのか、RDBに特に顕著な性質なのかが気になりました
    // 変更しやすい種類のDBを利用すればテーブル設計に多少問題があってもいいのになぁと思った次第です
    // 5. アーキテクチャデザイン
    // https://jflute.hatenadiary.jp/entry/20170623/desigraming
    private int totalEntrancesCount;
    private boolean alreadyIn;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    // done tsuji [いいね] 複数のコンストラクターに対して、コメントで役割を書いているのGood by jflute (2025/08/15)
    // #1on1: staticのFactoryメソッドのお話もちょこっと
    // done tsuji 一方で、Booth側で実際にOneDayでもこっちがnewされていない問題 by jflute (2025/08/15)
    // TODO jflute [修正しました] 使っていないコンストラクタを削除しました　akinari.tsuji  (2025/08/22)
    // one-day ticket
//    public Ticket(int displayPrice) {
//        this.displayPrice = displayPrice;
//        this.entranceLimit = 1;
//        this.totalEntrancesCount = 0;
//        this.alreadyIn = false;
//    }

    // two or more day ticket
    public Ticket(TicketType type) {
        ticketType = type;
        this.totalEntrancesCount = 0;
        this.alreadyIn = false;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void doInPark() {
        // ナイトパスを夜以外に使おうとした場合
        if (ticketType == TicketType.NIGHT) {
            LocalTime borderTime = LocalTime.of(18, 0);
            LocalTime nowTime = LocalTime.now();
            // LocalTime nowTime = LocalTime.of(17, 59); // 日中使用した場合を確認するためのコード
            if (nowTime.isBefore(borderTime)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
                throw new NotNightException("You can't buy this passport before " + borderTime.format(formatter) + ".");
            }
        }

        // 入園回数を超えた場合
        if (totalEntrancesCount >= ticketType.getEntranceLimit()) {
            throw new IllegalStateException("This ticket is already exhausted: displayedPrice=" + ticketType.getPrice());
        }
        totalEntrancesCount++;
        this.alreadyIn = true;
    }

    public static class NotNightException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public NotNightException(String msg) {
            super(msg);
        }
    }



    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return ticketType.getPrice();
    }

    public int getRemainingEntranceCounts() { return ticketType.getEntranceLimit() - totalEntrancesCount; }

    public int getEntranceLimit() { return ticketType.getEntranceLimit(); }

    public TicketType getTicketType() { return ticketType; }

    public boolean isAlreadyIn() { return alreadyIn; }
}
