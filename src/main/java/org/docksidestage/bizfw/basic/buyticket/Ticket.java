/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author jflute
 * @author akinari.tsuji
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int displayPrice; // written on ticket, park guest can watch this
    private final int entranceLimit;
    private int totalEntranceCounts;
    private boolean alreadyIn;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    // one-day ticket
    public Ticket(int displayPrice) {
        this.displayPrice = displayPrice;
        this.entranceLimit = 1;
        this.totalEntranceCounts = 0;
        this.alreadyIn = false;
    }

    // two or more day ticket
    public Ticket(int displayPrice, int entranceLimit) {
        this.displayPrice = displayPrice;
        this.entranceLimit = entranceLimit;
        this.totalEntranceCounts = 0;
        this.alreadyIn = false;
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void doInPark() {
        if (totalEntranceCounts >= entranceLimit) {
            throw new IllegalStateException("This ticket is already exhausted: displayedPrice=" + displayPrice);
        }
        totalEntranceCounts++;
        this.alreadyIn = true;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return displayPrice;
    }

    public int getRemainingEntranceCounts() { return entranceLimit - totalEntranceCounts; }

    public int getEntranceLimit() { return entranceLimit; }

    public boolean isAlreadyIn() { return alreadyIn; }
}
