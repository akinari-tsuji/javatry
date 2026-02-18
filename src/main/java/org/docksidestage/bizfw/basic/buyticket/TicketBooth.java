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


//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;

// done tsuji ↑コードを消したりの影響だと思いますが、unusedのimport文があります by jflute (2025/08/27)
// done jflute 修正いたしました！ akinari.tsuji  (2025/08/29)
/* #1on1:
pp どの勉強する？
o 切羽詰まって役立つものを勉強する // 役に立つけど、楽しいとは限らない
o 自分の興味のものを勉強する // 楽しいけど、役に立つとは限らない

o 本気で勉強していれば、役に立たないことはない
o 深層記憶に残るキリのいいところまでやっておく (踊り場)
 */

// done tsuji [読み物課題]あれもこれもやらなきゃプレッシャーが集中力を阻害する by jflute (2025/11/07)
// https://jflute.hatenadiary.jp/entry/20180527/keepconce
// 何をやったとしても工夫・努力を続けていれば、抽象的な能力が身について他に役に立つのだなと感じました
// なんにせよ具体的な目の前の一つ一つの仕事をちゃんとやらないと、抽象的な力もつかないし、抽象化して捉える機会もないと思うのでやはりRuby, Railsやらねばですね

// done tsuji [読み物課題]まず何より、目の前の道具を使いこなしてください by jflute (2025/11/07)
// https://jflute.hatenadiary.jp/entry/20180223/mastercurrent
// 文句を言わずに頑張らねばと感じました。仕事で使うRuby, RailsなのにAI無しじゃまともに書けない状況を早めに脱します。

import java.time.Clock;

/**
 * チケットの購入を管理するためのクラス (販売の責務)
 * @author jflute
 * @author akinari.tsuji
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    // private static final int MAX_QUANTITY = 10;
//    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
//    private static final int TWO_DAY_PRICE = 13200;
//    private static final int FOUR_DAY_PRICE = 22400;
//    private static final int NIGHT_PRICE = 7400;
    // TicketTypeにまとめる

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // private int quantity = MAX_QUANTITY;
    // done tsuji Constructorでnewしてその後変更しないので、final付けられる by jflute (2025/10/22)
    // done tsuji インスタンス変数の定義順序、少ないですがカテゴリを意識してみましょう by jflute (2025/11/19)
    // 固定でmaster的なオブジェクトは先頭に定義することが多いのでオススメ。(Clockが最初)
    // #1on1: 「何かを追加するときは、何も考えず一番下ではなく、クラス全体のデザインバランスを考えて追加するべし」
    private final Clock clock;

    private final TicketInventory ticketInventory;
    private Integer salesProceeds; // null allowed: until first purchase

    // #1on1: カテゴライズ系の話、片付けの話 (2025/11/19)
    // 「複数のもののつながりを見出してカテゴリを導出する」
    // LikeSearchOptionのインスタンス変数の例。
    // 実装しながら考えるのか？ yes, 一方で最後のリファクタリングの仕上げのときにも考える。
    // 日常の片付けに通じる。
    // done tsuji [読み物課題] リファクタリングは思考のツール by jflute (2025/11/19)
    // https://jflute.hatenadiary.jp/entry/20121202/1354442627
    // ちょうど目の前の作業と全体を振り返る時間のバランス・タイミングが難しいと感じていたので参考になりました
    // 毎日xx:xxからは10分振り返りとかの自分ルールをつけたいなと思って卓上時計を調べてました笑
    // 細かめなリファクタリングを通じて、コード全体を見直す時間ができるのだなーと思いました
    // コーディングに関わらず、仕事の流れのテンプレを確立できるととても便利そうです

    // #1on1: どう整えたらいいのか？を考え続けること (2025/11/19)
    // これをやめたら、ずっとかわらない。考え続けたら、徐々にスムーズになっていく。

    // #1on1: 書きながら考えるが大変 (2025/11/19)
    // 徐々に定理が積み上がってくれば、スムーズになてくる。
    // 徐々に片付けのセオリーみたいなのが自分の中で身についてくればスムーズに。
    //
    // もう一個は、ショートカットキー、打つのに頭を使わないようにする。
    // (叩き込む、ちゃんと反復練習してる)

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
        ticketInventory = new TicketInventory();
        clock = Clock.systemDefaultZone();
    }

//    /**
//     * テスト用のコンストラクタ
//     * @param clock 時間を測定するための時計
//     */
//    public TicketBooth(Clock clock) {
//        ticketInventory = new TicketInventory();
//        this.clock = clock;
//    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    // you can rewrite comments for your own language by jflute
    // e.g. Japanese
    // done tsuji 実処理のprivateに関しては、区別する名前を付ける慣習があったりします by jflute (2025/08/15)
    // 例えば、自分だと doBuyPassport() とか do を付けて実処理感出す。
    // 他だと、internalBuyPassport() みたいなのもある。個人的にはdoをよく使う。
    // #1on1: 会話しやすい名前という観点も (2025/08/15)
    // #1on1: IntelliJのrenameのショートカット: shift+shiftからのrenameでもいいけど...
    // control + T でリファクタリングメニューを出してrenameを選ぶがオススメ
    // done jflute doBuyTicketにrenameしました akinari.tsuji  (2025/08/22)
    // done tsuji javadoc, throws よりも return の方が上が一般的かなと。IN/OUTが固まっていたほうが by jflute (2025/08/15)
    // done tsuji privateのメソッドは、publicの下の方が一般的かなと by jflute (2025/08/15)
    // done tsuji ちょと面倒かもですが、publicメソッドこそjavadocでparam/returnの説明が欲しいところです by jflute (2025/08/15)
    // done jflute [修正しました] 上記の3点（javadocでのreturnの位置、privateメソッドの位置、publicメソッドのjavadoc）修正しました akinari.tsuji  (2025/08/22)
    /**
     * Buy one-day passport, method for park guest.
     * @param handedMoney 渡された金額
     * @return 1dayチケットの購入結果
     */
    public TicketBuyResult buyOneDayPassport(Integer handedMoney) {
        return doBuyTicket(handedMoney, TicketType.ONE_DAY);
    }

    /**
     * Buy two-day passport, method for park guest.
     * @param handedMoney 渡された金額
     * @return 2dayチケットの購入結果
     */
    public TicketBuyResult buyTwoDayPassport(Integer handedMoney) {
        return doBuyTicket(handedMoney, TicketType.TWO_DAYS);
    }

    /**
     * Buy four-day passport.
     * @param handedMoney 渡された金額
     * @return 4dayチケットの購入金額
     */
    public TicketBuyResult buyFourDayPassport(Integer handedMoney) {
        return doBuyTicket(handedMoney, TicketType.FOUR_DAYS);
    }

    /**
     * Buy night passport.
     * @param handedMoney 渡された金
     * @return ナイトパスの購入結果
     */
    public TicketBuyResult buyNightPassport(Integer handedMoney) {
        if (handedMoney == null) {
            throw new IllegalArgumentException("The argument 'handedMoney' should not be null.");
        }
//        LocalTime borderTime = LocalTime.of(18, 0);
        // done tsuji [いいね] 買うタイミングが夜じゃないいけないというのも一つのありえる業務ですね^^ by jflute (2025/08/15)
        // dibe tsuji 一方で、事前購入が可能で、入園するタイミングでチェックされるような仕様にしてみましょう by jflute (2025/08/15)
        // 一応要件としては "夜しか使えないように" ということなので「昼は買えるけど使えない」というように。
        // done jflute 使用時のチェックを追加するため、夜しか買えないようにするコードはコメントアウトします！ akinari.tsuji  (2025/08/22)
        // done jflute TicketクラスのdoInParkメソッドで判定をするように変更しました akinari.tsuji  (2025/08/22)
//        LocalTime nowTime = LocalTime.now();
//        if (nowTime.isBefore(borderTime)) {
//            // done tsuji borderTimeが変わったときに例外メッセージ修正し忘れしそうなので、同期するようにしましょう by jflute (2025/08/15)
//            // done jflute [修正しました] borderTimeを利用するように修正しました akinari.tsuji  (2025/08/22)
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
//            throw new NotNightException("You can't buy this passport before" + borderTime.format(formatter) + ".");
//        }
        return doBuyTicket(handedMoney, TicketType.NIGHT_FROM_EIGHTEEN);
    }

    /**
     * チケットを購入するメソッド
     * @param handedMoney 渡された金額
     * @param ticketType 購入しようとしているチケットの種類
     * @return ticketBuyResult 購入結果を返す
     * @throws TicketSoldOutException ブース内のチケットが売り切れだったら
     * @throws TicketShortMoneyException 買うのに金額が足りなかったら
     */
    private TicketBuyResult doBuyTicket(Integer handedMoney, TicketType ticketType) {
        // done tsuji 変数がunused警告 (いまいちどunused系の警告を全体に見直してみてください) by jflute (2025/10/22)
        // final int numberOfPurchases = ticketType.getEntranceLimit(); // getEntranceLimitだと意味が分かりにくいので格納
        final int price = ticketType.getPrice();
        // validateQuantity(numberOfPurchases);
        validateQuantity(ticketType);
        validateShortMoney(handedMoney, ticketType.getPrice());
//        quantity -= numberOfPurchases;
        ticketInventory.decreaseQuantity(ticketType);
        // done jflute[質問]
        //  Ticketのインスタンスを作成するのが、TicketBuyResultの中なのか、TicketBooth内で作成するべきなのか判断がつかず、
        //  どのように切り分けるべきか、考え方をお伺いしたいです。
        //  akinari.tsuji  (2025/08/04)
        // done tsuji [へんじ] どちらかというところで言うと、自分ならTicketBoothで作成します by jflute (2025/08/15)
        // TicketBuyResultはその名の通り、「買った結果」という概念を表現するオブジェクトということで、
        // あまり処理自体が入るものではなく、Boothと購入者をつなげるだけの箱みたいなものというニュアンスに徹するのがいいかなと。
        // あと、将来チケットの種類をクラスで表現するようになったときnewしている箇所が「入れ物クラス」にあると対応しづらいと想像。
        // TicketBoothで作成するのが正解というわけではないですが、Resultでは避けたいという感じですね。
        // #1on1: 質問: そういったデザイン的な思考をするためには？ by つじさん
        // 普段から現実の世界のモデリングを頭の中でしてみる。
        // 質問: それが合ってるかどうか？どう確認するか？実装までやらないとして by つじさん
        // 先輩とか同僚とかと技術話で壁打ちしてみる。(ランチなど)
        // done jflute[質問] akinari.tsuji  (2025/08/15)
        // buyTicketの引数の型にIntegerとintの両方含まれているのが違和感あるのですが、intに揃えても大丈夫でしょうか？
        // 元々、handedMoneyがIntegerだったのでどちらに揃えるべきか悩んでおります。
        // done tsuji [へんじ] いいと思いますー。あえて色々とバラけさせてるってのもあるので^^ by jflute (2025/08/15)
        // ありがとうございます！ akinari.tsuji  (2025/08/22)
        Ticket purchasedTicket = new Ticket(getCurrentClock(), ticketType); // ticketBoothでチケットを発行するように修正
        TicketBuyResult ticketBuyResult = new TicketBuyResult(handedMoney - price, purchasedTicket);
        calculateSalesProceeds(price);
        return ticketBuyResult;
    }

    // #1on1: protectedだからこそ、オーバーライドができて、拡張ポイント。
    // 一方で、privateは拡張性がないもの、完全に隠してしまう。
    protected Clock getCurrentClock() {
        return clock;
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

//    public static class NotNightException extends RuntimeException {
//        // done akinari.tsuji 一旦中身を理解していないが他と同様に実装 (2025/08/04)
//        // #1on1: Serializeの機能の宣言で、例外がSerializableなオブジェクトなので、serialのIDが求められる。
//        // Serialize自体はあまり利用されない機能なのでぶっちゃけどうでもいいんだけど、宣言しないと警告が出るIDEがある。
//        private static final long serialVersionUID = 1L;
//
//        public NotNightException(String msg) {
//            super(msg);
//        }
//    }

    private void validateQuantity(TicketType ticketType) {
//        if (quantity - numberOfPurchases < 0) {
//            throw new TicketSoldOutException("Sold out");
//        }
        if (ticketInventory.getQuantity(ticketType) <= 0) {
            throw new TicketSoldOutException(ticketType.name() + "is sold out");
        }
    }

    private void validateShortMoney(Integer handedMoney, int price) {
        if (handedMoney < price) {
            throw new TicketSoldOutException("Short money: " + handedMoney);
        }
    }

    // done tsuji setというメソッド名ですが、getter/setterというお決まりのメソッドがあるので... by jflute (2025/08/15)
    // そうじゃないところでsetを使うと少々紛らわしいので、calculate..., accept..., (積み上げるとか) 
    // みたいな単語を使えると区別がしやすくてわかりやすいかなと思います。
    // done jflute [修正しました] つい、get, setを頭につけがちなので気をつけます akinari.tsuji  (2025/08/22)
    private void calculateSalesProceeds(int price) {
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + price;
        } else {
            salesProceeds = price;
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * @param ticketType チケット種別
     * @return 指定のチケット種別の在庫数
     */
    public int getQuantity(TicketType ticketType) {
        return ticketInventory.getQuantity(ticketType);
    }

    /**
     * @return 売上合計金額
     */
    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
