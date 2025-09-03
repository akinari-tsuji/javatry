package org.docksidestage.bizfw.basic.buyticket;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeBasedPolicy implements IUsagePolicy {
    private final LocalTime availableFrom;

    public TimeBasedPolicy(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    @Override
    public boolean isAvailable() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.isAfter(availableFrom);
    }

    @Override
    public void validate() throws IllegalStateException {
        if (!isAvailable()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
            throw new IllegalStateException("You can't use this passport before " + availableFrom.format(formatter) + ".");
        }
    }
}
