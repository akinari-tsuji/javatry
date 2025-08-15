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

/**
 * チケットの購入を管理するためのクラス
 * @author jflute
 * @author akinari.tsuji
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;
    private static final int FOUR_DAY_PRICE = 22400;
    private static final int NIGHT_PRICE = 7400;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int quantity = MAX_QUANTITY;
    private Integer salesProceeds; // null allowed: until first purchase

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {}

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // TODO tsuji 実処理のprivateに関しては、区別する名前を付ける慣習があったりします by jflute (2025/08/15)
    // 例えば、自分だと doBuyPassport() とか do を付けて実処理感出す。
    // 他だと、internalBuyPassport() みたいなのもある。個人的にはdoをよく使う。
    // #1on1: 会話しやすい名前という観点も (2025/08/15)
    // #1on1: IntelliJのrenameのショートカット: shift+shiftからのrenameでもいいけど...
    // control + T でリファクタリングメニューを出してrenameを選ぶがオススメ
    // TODO tsuji javadoc, throws よりも return の方が上が一般的かなと。IN/OUTが固まっていたほうが by jflute (2025/08/15)
    // TODO tsuji privateのメソッドは、publicの下の方が一般的かなと by jflute (2025/08/15)
    /**
     * チケットを購入するメソッド
     * @param handedMoney 渡された金額
     * @param price 購入しようとしているチケットの金額
     * @param numberOfPurchases 購入しようとしているチケットの利用回数
     * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
     * @throws TicketShortMoneyException 買うのに金額が足りなかったら
     * @return ticketBuyResult 購入結果を返す
     */
    private TicketBuyResult buyTicket(Integer handedMoney, int price, int numberOfPurchases) {
        validateQuantity(numberOfPurchases);
        validateShortMoney(handedMoney, price);
        quantity -= numberOfPurchases;
        // TODO done jflute[質問]
        //  Ticketのインスタンスを作成するのが、TicketBuyResultの中なのか、TicketBooth内で作成するべきなのか判断がつかず、
        //  どのように切り分けるべきか、考え方をお伺いしたいです。
        //  akinari.tsuji  (2025/08/04)
        // TODO tsuji [へんじ] どちらかというところで言うと、自分ならTicketBoothで作成します by jflute (2025/08/15)
        // TicketBuyResultはその名の通り、「買った結果」という概念を表現するオブジェクトということで、
        // あまり処理自体が入るものではなく、Boothと購入者をつなげるだけの箱みたいなものというニュアンスに徹するのがいいかなと。
        // あと、将来チケットの種類をクラスで表現するようになったときnewしている箇所が「入れ物クラス」にあると対応しづらいと想像。
        // TicketBoothで作成するのが正解というわけではないですが、Resultでは避けたいという感じですね。
        // #1on1: 質問: そういったデザイン的な思考をするためには？ by つじさん
        // 普段から現実の世界のモデリングを頭の中でしてみる。
        // 質問: それが合ってるかどうか？どう確認するか？実装までやらないとして by つじさん
        // 先輩とか同僚とかと技術話で壁打ちしてみる。(ランチなど)
        // TODO done jflute[質問] akinari.tsuji  (2025/08/15)
        // buyTicketの引数の型にIntegerとintの両方含まれているのが違和感あるのですが、intに揃えても大丈夫でしょうか？
        // 元々、handedMoneyがIntegerだったのでどちらに揃えるべきか悩んでおります。
        // TODO tsuji [へんじ] いいと思いますー。あえて色々とバラけさせてるってのもあるので^^ by jflute (2025/08/15)
        TicketBuyResult ticketBuyResult = new TicketBuyResult(price, handedMoney - price, numberOfPurchases);
        setSalesProceeds(price);
        return ticketBuyResult;
    }

    // TODO tsuji ちょと面倒かもですが、publicメソッドこそjavadocでparam/returnの説明が欲しいところです by jflute (2025/08/15)
    /**
     * Buy one-day passport, method for park guest.
     */
    public TicketBuyResult buyOneDayPassport(Integer handedMoney) {
        return buyTicket(handedMoney, ONE_DAY_PRICE, 1);
    }

    /**
     * Buy two-day passport, method for park guest.
     */
    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        return buyTicket(handedMoney, TWO_DAY_PRICE, 2);
    }

    /**
     * Buy four-day passport.
     */
    public TicketBuyResult buyFourDayPassport(Integer handedMoney) {
        return buyTicket(handedMoney, FOUR_DAY_PRICE, 4);
    }

    /**
     * Buy night passport.
     * @throws NotNightException 夜以前にナイトパスを購入しようとしたら
     */
    public TicketBuyResult buyNightPassport(Integer handedMoney) {
        LocalTime borderTime = LocalTime.of(18, 0);
        // TODO tsuji [いいね] 買うタイミングが夜じゃないいけないというのも一つのありえる業務ですね^^ by jflute (2025/08/15)
        // TODO tsuji 一方で、事前購入が可能で、入園するタイミングでチェックされるような仕様にしてみましょう by jflute (2025/08/15)
        // 一応要件としては "夜しか使えないように" ということなので「昼は買えるけど使えない」というように。
        LocalTime nowTime = LocalTime.now();
        if (nowTime.isBefore(borderTime)) {
            // TODO tsuji borderTimeが変わったときに例外メッセージ修正し忘れしそうなので、同期するようにしましょう by jflute (2025/08/15)
            throw new NotNightException("You can't buy this passport before 18:00.");
        }
        return buyTicket(handedMoney, NIGHT_PRICE, 1);
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    public static class NotNightException extends RuntimeException {
        // TODO akinari.tsuji 一旦中身を理解していないが他と同様に実装 (2025/08/04)
        // #1on1: Serializeの機能の宣言で、例外がSerializableなオブジェクトなので、serialのIDが求められる。
        // Serialize自体はあまり利用されない機能なのでぶっちゃけどうでもいいんだけど、宣言しないと警告が出るIDEがある。
        private static final long serialVersionUID = 1L;

        public NotNightException(String msg) {
            super(msg);
        }
    }

    private void validateQuantity(int numberOfPurchases) {
        if (quantity - numberOfPurchases < 0) {
            throw new TicketSoldOutException("Sold out");
        }
    }

    private void validateShortMoney(Integer handedMoney, int price) {
        if (handedMoney < price) {
            throw new TicketSoldOutException("Short money: " + handedMoney);
        }
    }

    // TODO tsuji setというメソッド名ですが、getter/setterというお決まりのメソッドがあるので... by jflute (2025/08/15)
    // そうじゃないところでsetを使うと少々紛らわしいので、calculate..., accept..., (積み上げるとか) 
    // みたいな単語を使えると区別がしやすくてわかりやすいかなと思います。
    private void setSalesProceeds(int price) {
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + price;
        } else {
            salesProceeds = price;
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
