package org.docksidestage.javatry.basic;

import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import java.time.Clock;

public class StubbedTicketBooth extends TicketBooth {
    private final Clock stubClock;
    public StubbedTicketBooth(Clock clock) {
        super();
        this.stubClock = clock;
    }

    @Override
    protected Clock getCurrentClock() {
        // 親のロジック（this.clock を返す）を無視し、
        // 保持しておいたスタブ（stubClock）を返す
        return this.stubClock;
    }
}
