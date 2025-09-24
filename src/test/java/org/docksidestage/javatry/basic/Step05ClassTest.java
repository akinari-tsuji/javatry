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
package org.docksidestage.javatry.basic;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;

import org.docksidestage.bizfw.basic.buyticket.*;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth.TicketShortMoneyException;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of class. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう) <br>
 * 
 * If ambiguous requirement exists, you can determine specification that seems appropriate. <br>
 * (要件が曖昧なところがあれば、適切だと思われる仕様を決めても良いです)
 * 
 * @author jflute
 * @author akinari.tsuji
 */
public class Step05ClassTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          How to Use
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_class_howToUse_basic() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(7400);
        int sea = booth.getQuantity(); // 一つ購入したので9
        log(sea); // your answer? => 9
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_overpay() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // your answer? => 10000（修正後は7400)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_nosales() {
        TicketBooth booth = new TicketBooth();
        Integer sea = booth.getSalesProceeds(); // 初回購入まではnull
        log(sea); // your answer? => null
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_class_howToUse_wrongQuantity() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // your answer? => 9（修正後は10)
    }

    private Integer doTest_class_ticket_wrongQuantity() {
        TicketBooth booth = new TicketBooth();
        int handedMoney = 7399;
        try {
            booth.buyOneDayPassport(handedMoney); // ここで関数ないでTicketShortMoneyExecptionが発生
            fail("always exception but none"); // ここは実行されないのでテストは失敗しない
        } catch (TicketShortMoneyException continued) {  // ここで例外キャッチ
            log("Failed to buy one-day passport: money=" + handedMoney, continued);
        }
        return booth.getQuantity(); // 購入前（例外を投げる前）に先に在庫を減らしている
    }

    // ===================================================================================
    //                                                                           Let's fix
    //                                                                           =========
    /**
     * Fix the problem of ticket quantity reduction when short money. (Don't forget to fix also previous exercise answers) <br>
     * (お金不足でもチケットが減る問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_ticketQuantityReduction() {
        Integer sea = doTest_class_ticket_wrongQuantity();
        log(sea); // should be max quantity, visual check here
        // done jflute buyOneDayPassportメソッドを書き換えました akinari.tsuji  (2025/07/23)
        // done tsuji [へんじ] おお、いいですね。ちゃんと事前チェックがすべて通ってから在庫を減らしてますね^^ by jflute (2025/07/24)
        // #1on1: IntelliJで行移動: shift+option+↑↓

        // done tsuji くぼさんにショートカットをたくさん習ってください！そして.ideaディレクトリについても！笑 by shiny (2025/08/12)
        // TODO shiny 教えていただいております! akinari.tsuji  (2025/08/22)

    }

    /**
     * Fix the problem of sales proceeds increased by handed money. (Don't forget to fix also previous exercise answers) <br>
     * (受け取ったお金の分だけ売上が増えていく問題をクラスを修正して解決しましょう (以前のエクササイズのanswerの修正を忘れずに))
     */
    public void test_class_letsFix_salesProceedsIncrease() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(10000);
        Integer sea = booth.getSalesProceeds();
        log(sea); // should be same as one-day price, visual check here
    }
    // done jflute 1on1としてここまで、次回続き (2025/08/01)

    /**
     * Make method for buying two-day passport (price is 13200). (which can return change as method return value)
     * (TwoDayPassport (金額は13200) も買うメソッドを作りましょう (戻り値でお釣りをちゃんと返すように))
     */
    public void test_class_letsFix_makeMethod_twoday() {
        // uncomment after making the method
        TicketBooth booth = new TicketBooth();
        int money = 14000;
        int change = booth.buyTwoDayPassport(money).getChange();
        Integer sea = booth.getSalesProceeds() + change;
        log(sea); // should be same as money

        // and show two-day passport quantity here
        log(booth.getSalesProceeds());
    }

    /**
     * Recycle duplicate logics between one-day and two-day by e.g. private method in class. (And confirm result of both before and after) <br>
     * (OneDayとTwoDayで冗長なロジックがあったら、クラス内のprivateメソッドなどで再利用しましょう (修正前と修正後の実行結果を確認))
     */
    public void test_class_letsFix_refactor_recycle() {
        TicketBooth booth = new TicketBooth();
        booth.buyOneDayPassport(10000);
        log(booth.getQuantity(), booth.getSalesProceeds()); // should be same as before-fix
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Now you cannot get ticket if you buy one-day passport, so return Ticket class and do in-park. <br>
     * (OneDayPassportを買ってもチケットをもらえませんでした。戻り値でTicketクラスを戻すようにしてインしましょう)
     */
    public void test_class_moreFix_return_ticket() {
        // uncomment out after modifying the method
        TicketBooth booth = new TicketBooth();
        TicketBuyResult buyResult = booth.buyOneDayPassport(10000); // 以降の問題に合わせて方を修正
        Ticket oneDayPassport = buyResult.getTicket();
        log(oneDayPassport.getDisplayPrice()); // should be same as one-day price
        log(oneDayPassport.getRemainingEntranceCounts()); // should be false
        oneDayPassport.doInPark();
        log(oneDayPassport.getRemainingEntranceCounts()); // should be true
    }

    /**
     * Now also you cannot get ticket if two-day passport, so return class that has ticket and change. <br>
     * (TwoDayPassportもチケットをもらえませんでした。チケットとお釣りを戻すクラスを作って戻すようにしましょう)
     */
    public void test_class_moreFix_return_whole() {
        // uncomment after modifying the method
        TicketBooth booth = new TicketBooth();
        int handedMoney = 20000;
        TicketBuyResult buyResult = booth.buyTwoDayPassport(handedMoney);
        Ticket twoDayPassport = buyResult.getTicket();
        int change = buyResult.getChange();
        log(twoDayPassport.getDisplayPrice() + change); // should be same as money
    }

    /**
     * Now you can use only one in spite of two-day passport, so fix Ticket to be able to handle plural days. <br>
     * (TwoDayPassportなのに一回しか利用できません。複数日数に対応できるようにTicketを修正しましょう)
     */
    public void test_class_moreFix_usePluralDays() {
        // your confirmation code here
        TicketBooth booth = new TicketBooth();
        int handedMoney = 20000;
        TicketBuyResult buyResult = booth.buyTwoDayPassport(handedMoney);
        Ticket twoDayPassport = buyResult.getTicket();
        log(twoDayPassport.getRemainingEntranceCounts());
        twoDayPassport.doInPark();
        log(twoDayPassport.getRemainingEntranceCounts());
        twoDayPassport.doInPark();
        log(twoDayPassport.getRemainingEntranceCounts());
        // twoDayPassport.doInPark();
        // done akinari.tsuji Step06でalreadyInを利用するらしくエラーが出てしまった (2025/08/04)
        // 使い方がわからなかったので一旦step06側を一部コメントアウト、step06にてTicketを適宜修正する
    }

    /**
     * Accurately determine whether type of bought ticket is two-day passport or not by if-statemet. (fix Ticket classes if needed) <br>
     * (買ったチケットの種別がTwoDayPassportなのかどうかをif文で正確に判定してみましょう。(必要ならTicketクラスたちを修正))
     */
    public void test_class_moreFix_whetherTicketType() {
        // uncomment when you implement this exercise
        TicketBooth booth = new TicketBooth();
        TicketBuyResult oneDayPassportBuyResult = booth.buyOneDayPassport(10000);
        Ticket oneDayPassport = oneDayPassportBuyResult.getTicket();
        showTicketIfNeeds(oneDayPassport); // other
        TicketBuyResult twoDayPassportBuyResult = booth.buyTwoDayPassport(14000); // お金が足りないので修正
        Ticket twoDayPassport = twoDayPassportBuyResult.getTicket();
        showTicketIfNeeds(twoDayPassport); // two-day passport
    }

    // uncomment when you implement this exercise
    private void showTicketIfNeeds(Ticket ticket) {
        // done tsuji nightのtwoDayが紛れないように正確に識別できるようにしましょう by jflute (2025/08/15)
        // done jflute enumを使ってTicketTypeを定義して識別を行うようにしました akinari.tsuji  (2025/08/22)
        // done [質問] 仕様を変更するとき（今回でいえばTicketTypeを導入する）、コードを書く手前の作業として何をされていますか？ akinari.tsuji  (2025/08/22)
        // 前回、コードをまとめてあった＋コンパイルエラーが出るおかげで、直接コードを編集していても何とか漏れなく直せたのですが、もっと良いやり方を知りたいです
        // done tsuji [回答] 良い質問ですね！パッと思いつくもので二つ... by jflute (2025/08/27)
        //
        // 1: UnitTest書いて、変更前と変更後の挙動の違いが想定通りかどうかを確認できるように。
        // リファクタリングであれば挙動が変わらないことを確認、仕様変更であれば変更した分だけが変わることを確認。
        // ただ、修正のインパクトの程度に寄るので、多少端折るもありますが、ちょっと影響大きいなと思ったら書きます。
        //
        // 2: "コードをまとめてあった" につながりますが、メイン(仕様変更)の修正とは関係なくコードを綺麗にリファクタリングします。
        // もちろん、元々ある程度は綺麗に整えておくわけですが、それでも「まだまだ整えられる」ってキリがないもの。
        // そのまだまだの部分を進めるイメージです。(メインの修正したら消えちゃうものは除外して)
        // メインの修正をしていく中で「もうちょい整えた方がいいな」って一杯見えてくるものですが、
        // あまりメインの修正と同時に整える修正を混ぜると差分的に見づらいし、デバッグするときに判断のノイズになるので分けたい。
        // そして、整っている状態でメインの修正する方がやりやすいから。順番逆だと効率が悪いので。
        // 物理の世界に例えると、まず道を通りやすく整備してからメインの作業をするみたいな感覚ですね。
        // これも程度の問題はあります。えいっ一緒に混ぜちゃってもいいだろうと判断するときと、丁寧に分けるときと。
        //
        // 「コードをまとめてあった＋コンパイルエラーが出るおかげで」を体験できたはとても良いことですね(^^。
        //
        // #1on1: TicketBoothが販売の責務、Ticketがチケット状態の管理、クラスが増えた時に、どう整理しているか？ by tsujiさん
        // → 言語化が大事: 「TicketBooth=販売の責務」みたいな責任の言語化を習慣化している人が多い!? by jflute
        // → クラスのテーマの言語化が重要 by jflute
        // → JavaDocの一言テーマにこだわるのもそこ by jflute
        // 技術だから理系っぽい世界のはずなのに、国語力が求められる by tsuji
        // → いやまさしくその通り。自然言語能力がどんどん求められるようになってきて... by jflute
        // それがデザイン脳の話につながる。
        // → ただ、多くの人がそこまで深く考えてない印象なので、だから変なクラスに変なメソッドがあったりする!? by jflute
        // → 逆に言うと、こういうところを追求していけばスキルの差別化になる
        // #1on1: 一度、しっかり深堀りしておくことで、実務でゴールが想像しやすくなるし、論理的な妥協もしやすくなる。
        //
        // #1on1: 複数のクラスの関わりの関係性をどう？ファイル名やディレクトリ名で表現できたら良い？ by tsuji
        // → ファイル名やディレクトリ名で表現するのは自分の賛成で、意識していること by jflute
        // → さらに、もっと複雑になったら、やはり図、クラス図やコミュニケーション図、などを軽く書いて思考する。 by jflute
        // → 今の時代(事業会社だと)、業務で図を描いて提出するってことがあんまりないので図を描く練習する機会が少ない by jflute
        // → なので、多くの人が、図を描いて理解するとか思考するとかが慣れてない人が多い印象 by jflute
        
        // done tsuji [読み物課題] ホワイトボードを買ってこよう by jflute (2025/08/29)
        // https://jflute.hatenadiary.jp/entry/20110607/1307440686
        // TODO jflute [読みました]akinari.tsuji  (2025/09/10)
        // やはり手書きの方が障害がなく考えられていいですね...
        // 図を作る練習のためにPlantUMLでStep05のクラス図とシーケンス図を作ってみました（docs/Step05...）
        // TODO tsuji [読み物課題] SIとスタートアップの違いを知ろう by jflute (2025/08/29)
        // https://jflute.hatenadiary.jp/entry/20151007/sista
        // TODO tsuji [読み物課題] お世話になってる先輩が登壇する勉強会くらい行ってみたら？ by jflute (2025/08/29)
        // https://jflute.hatenadiary.jp/entry/20161201/gotomeeting
        // #1on1: 新卒研修の歴史のお話 (2025/08/29)
        
        if (ticket.getTicketType() == TicketType.TWO_DAYS) { // write determination for two-day passport
            log("two-day passport");
        } else {
            log("other");
        }
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Fix it to be able to buy four-day passport (price is 22400). <br>
     * (FourDayPassport (金額は22400) のチケットも買えるようにしましょう)
     */
    public void test_class_moreFix_wonder_four() {
        // your confirmation code here
        // done akinari.tsuji そろそろ整理しないと不味そうなので要件を整理 (2025/08/04)
        // 購入はTicketBoothを通じてTicketBuyResultを受けとる
        // チケットの種類は日数分、夜以降のチケット
        TicketBooth booth = new TicketBooth();
        TicketBuyResult fourDayTicketBuyResult = booth.buyFourDayPassport(30000);
        Ticket fourDayPassport = fourDayTicketBuyResult.getTicket();
        log(fourDayTicketBuyResult.getChange());
        log(fourDayPassport.getDisplayPrice());
        log(fourDayPassport.getRemainingEntranceCounts());
        for (int i = 0; i < fourDayPassport.getEntranceLimit(); i++) {
            fourDayPassport.doInPark();
            log(fourDayPassport.getRemainingEntranceCounts());
        }
    }

    /**
     * Fix it to be able to buy night-only two-day passport (price is 7400), which can be used at only night. <br>
     * (NightOnlyTwoDayPassport (金額は7400) のチケットも買えるようにしましょう。夜しか使えないようにしましょう)
     */
    public void test_class_moreFix_wonder_night() {
        // your confirmation code here
        TicketBooth booth = new TicketBooth();
        TicketBuyResult nightPassportBuyResult = booth.buyNightPassport(10000);
        log(nightPassportBuyResult.getChange());
        Ticket nightPassport = nightPassportBuyResult.getTicket();
        log(nightPassport.getDisplayPrice());
        log(nightPassport.getEntranceLimit());
        
        // TODO tsuji 修行++: UnitTestを昼に動かすと落ちるのどうにかしたいですね by jflute (2025/08/27)
        // UnitTestの実行時間に依存せずに夜入園のテストができるようにできるといいなと。(難しいの後回しOK)
        // TODO akinari.tsuji [メモ] doInParkの中で時間外の場合、例外を発生させるために落ちてしまっている (2025/09/10)
        // これをどう修正することができるのかを考える
        // 1. そもそも落ちなくする（例外を投げない）
        // 2. テスト実行時に時間を指定できるようにする（引数で現在時間を渡す？）(確か、依存性注入とかいうやつ）
        // 3. Gemini案：Clock（時間を返すクラス）をメンバ変数に持たせる。UnitTestを実行する際には、固定時間を返すClockをコンストラクタに渡すようにする
        // done akinari.tsuji [次回はここから＋読み物課題も] 2で十分な気がする...ので2で実装する (2025/09/10)
        // TODO akinari.tsuji [続き] 家に帰って考えたら2は不自然な気がしてきた (2025/09/16)
        // ticket.doInPark(currentTime); という形で呼び出すのおかしくないのかな...
        // 使う時間は絶対に使おうとしたタイミングだし、それを呼び出し側で伝えることに違和感
        // 現実を考えた時に、遊園地で入園する際に、「今、16時です。チケット使わせてください」はおかしいような...
        // というわけで、やはり案3を採用する...?
        // あれ、でも、どのみち今回のTicketでいえば、使えるかどうかをticket.doInParkな時点でおかしい気するな...
        // 実際にはブースの人が使えるか判断するし
        // TODO akinari.tsuji [追加の疑問点] 現在のPolicy interfaceの実装でどうやって依存性を注入する？ (2025/09/16)
        // IUsagePolicyのisAvailableに引数設定したら、AllDayPolicyの方でも引数必要になっちゃうけど、常にtrueを返す関数に渡してもしょうがない
        // -> Geminiに相談してみる：前回、クラス図をplantUMLで作っておいたので、相談用のプロンプト作成が楽だった！！！
        // ClockクラスをTimeBasedPolicyの定義でメンバ変数に持つように記述すればいいみたい（他のPolicyには影響がない）
        // Enumのナイトパスを設定する部分で、new TimeBasedPolicy(Clock.systemDefaultZone())を渡してあげる
        // さらに、EnumにsetUsagePolicyという、テスト時にポリシー書き換えるための関数を定義してあげる
        // テストの時にはsetUsagePolicyの引数に、new TimeBasedPolicy(Clock.fixed(...))を渡してあげる
        // これでやってみよう
        // TODO akinari.tsuji [次回ここ！] (2025/09/16)
        // TODO akinari.tsuji [理解する] Instant, ZoneId, Clockはいまいち理解せずに書いてるので次回調べる！ (2025/09/16)
        // TODO akinari.tsuji [調べる] あと、こういうのは「テストダブル」というらしい。前に一回調べたけど忘れたから↓を読み直す (2025/09/16)
        // https://goyoki.hatenablog.com/entry/20120301/1330608789
        Instant fixedInstant = Instant.parse("2025-09-17T10:30:00Z"); // 日本時間で19:30なのでOK
        ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");
        Clock fixedClock = Clock.fixed(fixedInstant, tokyoZone);
        TicketType.NIGHT_FROM_EIGHTEEN.setUsagePolicy(new TimeBasedPolicy(fixedClock, LocalTime.of(18, 0)));
        nightPassport.doInPark();
        
        // #1on1: 実現できてるはできてるけど、共有インスタンスであるenumにpublicでsetして変えられるので...
        // アプリが間違って setUsagePolicy() を呼び出してシステムを壊しちゃったら...
    }

    /**
     * Refactor if you want to fix (e.g. is it well-balanced name of method and variable?). <br>
     * (その他、気になるところがあったらリファクタリングしてみましょう (例えば、バランスの良いメソッド名や変数名になっていますか？))
     */
    public void test_class_moreFix_yourRefactoring() {
        // your confirmation code here
        // done akinari.tsuji 一つ前の問題のところで対応しました (2025/08/04)
        // 共通する購入処理をbuyTicketに集約し、具体的にどのチケットを買うかは各メソッドから引数で渡す形にしました
        // それにあたって、他の問題でコンパイルエラーが出てしまったので適宜変数の型を修正しました
    }

    // done tsuji ちょっとjavatry更新でお願いします by jflute (2025/08/15)
    // done jflute [完了しました] akinari.tsuji  (2025/09/10)
    /**
     * Write intelligent JavaDoc comments seriously on the public classes/constructors/methods of the Ticket class. <br>
     * (Ticketクラスのpublicなクラス/コンストラクター/メソッドに、気の利いたJavaDocコメントを本気で書いてみましょう)
     */
    public void test_class_moreFix_yourSuperJavaDoc() {
        // your confirmation code here
    }

    ///**
    // * Write intelligent comments on source code to the main code in buyticket package. <br>
    // * (buyticketパッケージのクラスに、気の利いたコメントを追加してみましょう)
    // */
    //public void test_class_moreFix_yourSuperComments() {
    //    // your confirmation code here
    //}

    // ===================================================================================
    //                                                                         Devil Stage
    //                                                                         ===========
    // TODO tsuji 修行#: 最後にこれやってみましょう by jflute (2025/09/24)
    /**
     * If your specification is to share inventory (quantity) between OneDay/TwoDay/...,
     * change the specification to separate inventory for each OneDay/TwoDay/.... <br>
     * (もし、OneDay/TwoDay/...で在庫(quantity)を共有する仕様になってたら、
     * OneDay/TwoDay/...ごとに在庫を分ける仕様に変えてみましょう)
     */
    public void test_class_moreFix_zonedQuantity() {
        // your confirmation code here
    }
}
