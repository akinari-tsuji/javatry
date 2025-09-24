package org.docksidestage.bizfw.basic.buyticket;

// #1on1: インターフェースの概念、step6の先取りの少し (2025/09/24)
// 物と物のインターフェースの考え方が基本になる。
/**
 * チケットの利用ポリシーに関するインターフェース
 * @author akinari.tsuji
 */
public interface IUsagePolicy {
    boolean isAvailable();
    void validate() throws IllegalStateException;
}
