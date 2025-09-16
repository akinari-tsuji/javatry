package org.docksidestage.bizfw.basic.buyticket;

import java.time.Clock;
import java.time.LocalTime;

// done tsuji クラスjavadocお願いします (authorだけでも) by jflute (2025/08/27)
// https://dbflute.seasar.org/ja/tutorial/handson/review/codingpolicy.html#minjavadoc
// TODO jflute [javadoc記入しました！] akinari.tsuji  (2025/09/10)

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
