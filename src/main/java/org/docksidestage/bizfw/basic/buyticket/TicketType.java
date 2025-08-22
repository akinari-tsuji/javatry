package org.docksidestage.bizfw.basic.buyticket;

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
