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
    public TicketBuyResult(int change, Ticket ticket) {
        this.ticket = ticket;
        this.change = change;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    /**
     * @return 購入したチケット
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * @return お釣り
     */
    public int getChange() {
        return change;
    }
}
