package org.docksidestage.bizfw.basic.buyticket;

import java.time.LocalTime;

// TODO tsuji クラスjavadocお願いします (authorだけでも) by jflute (2025/08/27)
// https://dbflute.seasar.org/ja/tutorial/handson/review/codingpolicy.html#minjavadoc
public enum TicketType {
    /** 金額, 入園上限回数, 利用可能ポリシー */
    ONE_DAY(7400, 1, new AllDayPolicy()),
    TWO_DAYS(13200, 2, new AllDayPolicy()),
    FOUR_DAYS(22400, 4, new AllDayPolicy()),
    NIGHT_FROM_EIGHTEEN(7400, 1, new TimeBasedPolicy(LocalTime.of(18, 0))),
    NIGHT_FROM_SEVENTEEN(7400, 1, new TimeBasedPolicy(LocalTime.of(17, 0)));

    private final int price;
    private final int entranceLimit;
    private final IUsagePolicy usagePolicy;

    TicketType(int price, int entranceLimit, IUsagePolicy usagePolicy) {
        this.price = price;
        this.entranceLimit = entranceLimit;
        this.usagePolicy = usagePolicy;
    }

    public int getPrice() {
        return price;
    }

    public int getEntranceLimit() {
        return entranceLimit;
    }

    public IUsagePolicy getUsagePolicy() {
        return usagePolicy;
    }
}
