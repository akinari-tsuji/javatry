package org.docksidestage.bizfw.basic.buyticket;

// TODO tsuji クラスjavadocお願いします (authorだけでも) by jflute (2025/08/27)
// https://dbflute.seasar.org/ja/tutorial/handson/review/codingpolicy.html#minjavadoc
public enum TicketType {
    ONE_DAY(7400, 1),
    TWO_DAYS(13200, 2),
    FOUR_DAYS(22400, 4),
    NIGHT(7400, 1);

    private final int price;
    private final int entranceLimit;

    TicketType(int price, int entranceLimit) {
        this.price = price;
        this.entranceLimit = entranceLimit;
    }

    public int getPrice() {
        return price;
    }

    public int getEntranceLimit() {
        return entranceLimit;
    }
}
