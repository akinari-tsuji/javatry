package org.docksidestage.bizfw.basic.buyticket;

// #1on1: インターフェースの概念、step6の先取りの少し (2025/09/24)
// 物と物のインターフェースの考え方が基本になる。

import java.time.LocalTime;

/**
 * チケットの利用ポリシーに関するインターフェース
 * @author akinari.tsuji
 */
public interface IUsagePolicy {
    /**
     * 利用可能か判定を行う関数
     * @param currentTime 現在時刻
     * @throws RuntimeException 利用不可の場合
     */
    void validate(LocalTime currentTime) throws RuntimeException;
}
