package org.docksidestage.bizfw.basic.buyticket;

/**
 * チケットの利用ポリシーに関するインターフェース
 */
public interface IUsagePolicy {
    boolean isAvailable();
    void validate() throws IllegalStateException;
}
