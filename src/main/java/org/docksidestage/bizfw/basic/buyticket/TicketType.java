package org.docksidestage.bizfw.basic.buyticket;

import java.time.Clock;
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
    /** 金額, 入園上限回数, 利用可能ポリシー */
    ONE_DAY(7400, 1, new AllDayPolicy()),
    TWO_DAYS(13200, 2, new AllDayPolicy()),
    FOUR_DAYS(22400, 4, new AllDayPolicy()),
    NIGHT_FROM_EIGHTEEN(7400, 1, new TimeBasedPolicy(Clock.systemDefaultZone(), LocalTime.of(18, 0))),
    NIGHT_FROM_SEVENTEEN(7400, 1, new TimeBasedPolicy(Clock.systemDefaultZone(), LocalTime.of(17, 0)));

    private final int price;
    private final int entranceLimit;
    private IUsagePolicy usagePolicy;

    /**
     * コンストラクタ
     * @param price
     * @param entranceLimit
     * @param usagePolicy
     */
    TicketType(int price, int entranceLimit, IUsagePolicy usagePolicy) {
        this.price = price;
        this.entranceLimit = entranceLimit;
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
     * 利用ポリシーのインスタンスを返すゲッター
     * @return チケットの利用可能ポリシー
     */
    public IUsagePolicy getUsagePolicy() {
        return usagePolicy;
    }

    /**
     * テスト用に利用ポリシーを変更するための関数
     * @param usagePolicy 変更したい利用ポリシー
     */
    public void setUsagePolicy(IUsagePolicy usagePolicy) {
        this.usagePolicy = usagePolicy;
    }
}
