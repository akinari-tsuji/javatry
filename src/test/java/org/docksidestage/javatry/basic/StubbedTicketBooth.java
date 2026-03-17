package org.docksidestage.javatry.basic;

import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import java.time.Clock;

// TODO akinari.tsuji ここのStubbedTicketBoothはわざわざファイル分けなくてもテスト時に定義できるのでは？ (2026/03/18)
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
