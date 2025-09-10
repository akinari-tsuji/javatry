package org.docksidestage.bizfw.basic.buyticket;

/**
 * チケットの利用ポリシーに関するインターフェース
 * @author akinari.tsuji
 */
public interface IUsagePolicy {
    boolean isAvailable();
    void validate() throws IllegalStateException;
}
