package org.docksidestage.bizfw.basic.buyticket;

import java.time.LocalTime;

// done tsuji クラスjavadocお願いします (authorだけでも) by jflute (2025/08/27)
// https://dbflute.seasar.org/ja/tutorial/handson/review/codingpolicy.html#minjavadoc
// done jflute [javadoc記入しました！] akinari.tsuji  (2025/09/10)

// #1on1: enumって、インスタンスを限定したクラス (2025/09/24)
// あくまで TicketType っていうクラスだけども...普通のクラスだと何個でも new できちゃう。
// enum は、列挙した意味のあるインスタンスしか new できないようになっている。
// 
// Java5 (2005年くらい) から導入された文法で、10年間enumなかった。
// Java5より前のenumもどきクラス:
// public class TicketType {
//     public static final TicketType ONE_DAY = new TicketType(7400, ...);
//     public static final TicketType TWO_DAY = new TicketType(13200, ...);
//     ...
//     private TicketType() {
//     }
//     ...
// }
// 使用感はほぼenumと同じになる。 e.g. TicketType.ONE_DAY
// enumは、enum的普通クラスの定型パターンを文法化したもの。
//
// 列挙の業務的な意味を、インスタンスに込めたもの。

// #1on1: IUsagePolicy でチェックの再利用をできるようにしてるのGood (2025/09/24)
/**
 * 遊園地のチケットの種別を表すEnum
 * 金額、入園の上限回数、利用可能ポリシーをもつ
 * @author akinari.tsuji
 */
public enum TicketType {
    /** 金額, 入園上限回数, 初期, 在庫数, 利用可能ポリシー */
    ONE_DAY(7400, 1, 10, new AllDayPolicy()),
    TWO_DAYS(13200, 2, 10, new AllDayPolicy()),
    FOUR_DAYS(22400, 4, 10, new AllDayPolicy()),
    NIGHT_FROM_EIGHTEEN(7400, 1, 10, new TimeBasedPolicy(LocalTime.of(18, 0))),
    NIGHT_FROM_SEVENTEEN(7400, 1, 10, new TimeBasedPolicy(LocalTime.of(17, 0)));

    private final int price;
    private final int entranceLimit;
    private final int initialQuantity;
    // done tsuji setUsagePolicyがなくなったので、final付けられるかなと by jflute (2025/10/22)
    private final IUsagePolicy usagePolicy;

    // done tsuji javadocの@paramの説明ぜひ書きましょう by jflute (2025/10/22)
    // TODO done tsuji @paramが足りてない by jflute (2025/12/03)
    /**
     * コンストラクタ
     * @param price 金額
     * @param entranceLimit 入園上限
     * @param initialQuantity 初期在庫数
     * @param usagePolicy 入園ポリシー
     */
    TicketType(int price, int entranceLimit, int initialQuantity, IUsagePolicy usagePolicy) {
        this.price = price;
        this.entranceLimit = entranceLimit;
        this.initialQuantity = initialQuantity;
        this.usagePolicy = usagePolicy;
    }

    /**
     * 金額を返すゲッター
     * @return チケットの金額
     */
    public int getPrice() {
        return price;
    }

    /**
     * 入園上限回数を返すゲッター
     * @return チケットの入園上限回数
     */
    public int getEntranceLimit() {
        return entranceLimit;
    }

    /**
     * 初期在庫数を返すゲッター
     * @return 初期在庫数
     */
    public int getInitialQuantity() { return initialQuantity; }

    /**
     * 利用ポリシーのインスタンスを返すゲッター
     * @return チケットの利用可能ポリシー
     */
    public IUsagePolicy getUsagePolicy() {
        return usagePolicy;
    }

    // #1on1: mainコードでは呼ばれたくないメソッドがどうしてもpublicになっちゃう問題 (2025/10/08)
    // 現在日時の提供を、policyから排除して、policyは純粋に時間帯の判定を行うロジックに限定させて、
    // 現在日時はTicketから、さらにその前は、TicketBoothから受け取るようにして...
    // testメソッドでは、拡張したTicketBoothで現在日時を細工してあげると、
    // 100%ではないけれども、もうちょい限定的な細工になって、publicのsetよりは安心感あるかも。
    //
    // [質問] テストのしやすさは、mainをしっかり考えていけば自然としやすくなるのか？ by つじさん (2025/10/08)
    // 半分はそうだけど、半分はそうじゃない。mainがぐちゃぐちゃだったら当然テストもしにくい。
    // ただ、今回のような結構ピンポイントな部分の話に関しては、ピンポイントにテストに対応していかないと実現できない。
    // なので、test都合でmainも多少影響は受ける。
    // ということで、最初からtestのことを考えて実装設計したほうがベターではある。
    // でも、testするときにリファクタリングすりゃいいといのもあるから、手が早ければそれでも。
    // done tsuji 修行++: ↑のやり方でpublicのsetが無いようにしてみましょう by jflute (2025/10/08)

    // /**
    // * テスト用に利用ポリシーを変更するための関数
    // * @param usagePolicy 変更したい利用ポリシー
    // */
//    public void setUsagePolicy(IUsagePolicy usagePolicy) {
//        this.usagePolicy = usagePolicy;
//    }
    // #1on1: 一方で、public-setのままでも、@deprecatedでtest用であることを目立たせたり、
    // あとは、lock解除の手続きを増やしたり、メソッド名をトリッキーにして気持ち悪くさせる(目立たなくさせる)、
    // DBFluteConfig の例。
    
    //
    // [質問] テストアサート追加のときに、コードの動きに合わせたアサートしてるような気がして by つじさん (2025/10/08)
    // 手動テストですでに動作の担保(期待値の定義も含む)はできていて、そこから同じ箇所の自動テストを追加するってときの話。
    // この場合は、期待値の定義が別の作業で終わっているので、だから既存コードに合わせたアサートでも問題はない。
    //
    // 本番で正常に動いてて(何ヶ月も正常に)、でも自動テストがないから追加するってケースも似た感じ。
    // もちろん、期待値の定義を０からのつもりでやり直してアサートを作っていくのも理想だけど...
    // 半々くらいの感覚でも良いような。すでにある定義を再確認するためのバックアップの意識を持っているくらいの感覚で。
    // (↑ちなみにこの場合、デグレ防止の自動テストを追加するという感覚)
    //
    // 自動テストの役割:
    //  A. まだ動作保証できてないコードの動作保証
    //  B. 将来の修正のときのデグレ防止
    //
    // なので、"B" だけの目的(状態)になった自動テストの作成は、自然と既存コードの振る舞いを維持するテストになる。
    // (これが、期待値の定義がすでに終わっている状態で、その期待値に合わせたアサートを書くだけという風になる)
    // (もちろん、これはあまりUnitTestに思想としては理想の状態ではなく、最初からUnitTestあるべしってのはあるけど現実ね...)
    //
}
