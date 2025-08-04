package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author akinari.tsuji
 */
public class TicketBuyResult {
    // ===================================================================================
    //                                                                           Attribute
    //                                                                          ==========
    private final Ticket ticket;
    private final int change;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                            ========
    public TicketBuyResult(int price, int change, int quantity) {
        this.ticket = new Ticket(price, quantity);
        this.change = change;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public Ticket getTicket() {
        return ticket;
    }

    public int getChange() {
        return change;
    }
}
