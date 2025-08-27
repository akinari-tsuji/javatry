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
 * 遊園地のチケットを表すクラス。
 * データとしては、チケット種別（金額・回数）、今までの利用回数、現在入園中かを表す変数を持つ。
 * 操作としては、入園を行うメソッド、メンバ変数のゲッターを持つ。
 * 使用方法としては、TicketBoothクラスの購入用メソッドを通じたインスタンスの作成を想定している。
 * そのため、他クラスから直接Ticketインスタンスを作成することは非推奨。
 * @author jflute
 * @author akinari.tsuji
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // TODO tsuji コメント形式がjavadoc形式になってないです。最初スラアスタアスタというように * が二つ必要です by jflute (2025/08/27)
    /* チケットの種類 */
    private final TicketType ticketType;

    /* 今までのこのチケットを利用した回数 */
    private int totalEntrancesCount;

    /* 現在、入園中かを表す。true: 入園中, false： 非入園中 */
    private boolean alreadyIn;

    // done tsuji [いいね] インスタンス変数とコンストラクターでのset順序が同じなのでわかりやすい by jflute (2025/08/15)
    // ありがとうございます！ by akinari.tsuji
//    private final int displayPrice; // written on ticket, park guest can watch this
//    private final int entranceLimit;
    // done tsuji Countという概念自体は一つしか無いので、複数形にしない方がいいかなと by jflute (2025/08/15)
    // もし、EntrancesだったらCountという言葉を省略した入園回数という概念にはなると思う。
    // done [修正] jflute 1on1でご指摘いただいたようにtotalEntrancesCountに修正しました akinari.tsuji  (2025/08/22)
    // 複数回入園することを表現しながら、値としては一つであることを表現
    // done tsuji [読み物課題] プログラマーに求められるデザイン脳 by jflute (2025/08/15)
    // done jflute [読み物課題メモ] akinari.tsuji  (2025/08/22)
    // 1. 命名デザイン : 周りの名前・全体の中での位置付けからつける
    // 2. 構図デザイン：人が見て・手を加えるための構造デザイン
    // 3. コメントデザイン：読む人が嬉しいコメントを残す
    // 4. DBデザイン：誤魔化しが効かない
    // TODO done jflute [質問] 後から変更がしやすいDBというのは開発されなかったのでしょうか？　akinari.tsuji  (2025/08/22)
    // 変更しにくいというのはDB全体の性質なのか、RDBに特に顕著な性質なのかが気になりました
    // 変更しやすい種類のDBを利用すればテーブル設計に多少問題があってもいいのになぁと思った次第です
    // 5. アーキテクチャデザイン
    // https://jflute.hatenadiary.jp/entry/20170623/desigraming
    // TODO tsuji [回答] 半分はRDBの特性で半分はDBなら避けられないみたいなところでしょうか... by jflute (2025/08/27)
    // RDBは確かに、堅いことをウリにしているというのもあり比較的他のDBに比べて変更しにくい面はあるかと思います。
    // (ただそこはトレードオフではあるので、変更しやすい仕組みだと堅いことのウリを失うので、ケースバイケースで)
    // 一方で、RDBだろうが他のDBだろうが、アプリのコードがそのDBのデータ形式に依存するので、
    // それが変わればアプリも変わらないといけないです。一般手に参照されている側というのは変更がしづらいもので。
    //
    // そこで、堅いことをウリにしているRDBのメリットを得つつ、アプリ側をDB変更に追従しやすくしたい、
    // というコンセプトがDBFluteにつながるという感じですね(^^。
    // TODO tsuji git conflictのマージ作業で重複しちゃったんじゃないかと。変数が二重になってます。 by jflute (2025/08/27)
    private int totalEntrancesCount;
    private boolean alreadyIn;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * Ticketクラスのコンストラクタ。
     * TicketBoothクラスからの呼び出しを想定。
     * @param type 作成するチケットの種類。
     */
    public Ticket(TicketType type) {
        ticketType = type;
        totalEntrancesCount = 0;
        alreadyIn = false;
    }

    // done tsuji [いいね] 複数のコンストラクターに対して、コメントで役割を書いているのGood by jflute (2025/08/15)
    // #1on1: staticのFactoryメソッドのお話もちょこっと
    // done tsuji 一方で、Booth側で実際にOneDayでもこっちがnewされていない問題 by jflute (2025/08/15)
    // TODO done jflute [修正しました] 使っていないコンストラクタを削除しました　akinari.tsuji  (2025/08/22)
    // one-day ticket
//    public Ticket(int displayPrice) {
//        this.displayPrice = displayPrice;
//        this.entranceLimit = 1;
//        this.totalEntrancesCount = 0;
//        this.alreadyIn = false;
//    }

    // two or more day ticket
//     public Ticket(TicketType type) {
        // TODO tsuji this.を使ってたり使ってなかったりが不統一なのでどうにかしましょう by jflute (2025/08/27)
        // (this.が必要な場面だけで使うってのも一つの選択肢ですが、現状はそれでもなさそうなので)
//        ticketType = type;
//        this.totalEntrancesCount = 0;
//        this.alreadyIn = false;
//    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    /**
     * チケットを使用するメソッド。
     * 入園回数を増加させ、入園中のステータスに切り替える。
     * ナイトパスの利用は18時以降。
     * @throws NotNightException ナイトパスを日中に利用とした場合
     * @throws IllegalStateException チケットの入園回数を超えて利用しようとした場合
     */
    public void doInPark() {
        // ナイトパスを夜以外に使おうとした場合
        if (ticketType == TicketType.NIGHT) {
            // TODO tsuji 修行++: NIGHTのニュアンスが違う別のNIGHTのチケット(e.g. 17時から)が出てきた時でも対応できるように by jflute (2025/08/27)
            LocalTime borderTime = LocalTime.of(18, 0);
            LocalTime nowTime = LocalTime.now();
            // LocalTime nowTime = LocalTime.of(17, 59); // 日中使用した場合を確認するためのコード
            if (nowTime.isBefore(borderTime)) {
                // TODO tsuji [いいね] 例外メッセージに borderTime を入れているのいいね、デバッグしやすい by jflute (2025/08/27)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
                throw new NotNightException("You can't use this passport before " + borderTime.format(formatter) + ".");
            }
        }

        // 入園回数を超えて利用しようとした場合
        if (totalEntrancesCount >= ticketType.getEntranceLimit()) {
            throw new IllegalStateException("This ticket is already exhausted: displayedPrice=" + ticketType.getPrice());
        }

        // チケットの利用とそれに伴う処理
        totalEntrancesCount++;
        this.alreadyIn = true;
    }

    private static class NotNightException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public NotNightException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========

    /**
     * チケットの販売金額を返すゲッター
     * @return チケットの金額
     */
    public int getDisplayPrice() { return ticketType.getPrice(); }

    // TODO jflute [質問] 今の実装だと、チケットのメンバ変数で直接金額を持たないため不具合が生じてしまいます？　akinari.tsuji  (2025/08/27)
    // 例えば、8/1に8,000円のチケットを購入した（チケットA）後、8/10に10,000へと値上がりした場合、チケットAの表示金額が上昇してしまいそう
    // その場合、ticketType側で新旧という軸でチケット種別を管理するべきなのか、そもそもenumを使うやり方が不適切で他の方法なら問題ないのかを知りたいです
    // TODO tsuji [回答] 良い着眼点ですね。そう、実際そういうことは業務でよくあるので... by jflute (2025/08/27)
    // マスターデータ(TicketType)を参照するではなく、「購入時金額」というニュアンスでSNAPSHOTとしてdisplayPriceを保持するってのが選択肢の一つです。
    //
    // DBFluteハンズオンのDBを例でで言うと、商品テーブルに定価というカラムがありますが、購入テーブルに購入金額というカラムもあります。
    // https://dbflute.seasar.org/ja/data/model/maihamadb-erd.png
    // どちらもほぼ同じ値になることが多いわけですが、購入時から金額変わったときでも昔の金額を表示できるように。
    // Ticketクラスの情報がPURCHASEテーブルに近いと考えると、int displayPrice とか int purchasePrice とかを持つイメージですね。
    //
    // enumを使うやり方自体は全く問題ないです。ちなみにDB付きのシステムだと、ここがマスターテーブルに置き換えられるイメージですね。
    // (マスターテーブルになっても、enum自体は作りますが)

    /**
     * チケットの残りの利用回数を返すゲッター
     * @return チケットの残り利用回数
     */
    public int getRemainingEntranceCounts() { return ticketType.getEntranceLimit() - totalEntrancesCount; }

    // TODO [質問] そのチケットが使えるかどうかの判定するメソッドが必要な場合はTicketクラスに定義するべきでしょうか？ akinari.tsuji  (2025/08/27)
    // 今回はlogで出力するためにgetRemainingEntranceCounts()をpublicに定義しています。
    // もしも、このメソッドを外部から呼び出し、まだ利用できるかを判定している場合、Ticketクラスにまだ使えるか判定するロジックを集約するべきでしょうか。
    // TODO tsuji [回答] getRemainingEntranceCounts()さえあれば利用者側は簡単に判断できるのでそれで十分とも言えますし... by jflute (2025/08/27)
    // それだけで「どの項目とどの項目を引き算」というあまり外部に依存させたくないロジックを隠蔽しているので。
    // 一方で、isUsedUp() みたいなbooleanメソッドを提供してあげたらそれはそれで便利ですが、
    // 「remainingEntranceCounts が 0 であること」というロジックはこれ以上変わりようのないものと考えることもできるので、
    // そこはあってもなくてもくらいな感じですね。さっきの「どの項目とどの項目を引き算」を隠蔽していることの方が重要。

    /**
     * チケットの利用上限回数。
     * 残りの利用回数ではなく、チケット種類に紐づく合計利用回数の上限を返します。
     * @return チケットの合計利用回数の上限
     */
    public int getEntranceLimit() { return ticketType.getEntranceLimit(); }

    /**
     * チケットの種別を返す関数。
     * TicketTypeでマスタを管理しているチケット種別を返す。
     * @return チケット種別
     */
    public TicketType getTicketType() { return ticketType; }

    /**
     * 現在、入園中かを返す関数。
     * 入園中の場合、true。
     * @return 入園中か？
     */
    public boolean isAlreadyIn() { return alreadyIn; }
}
