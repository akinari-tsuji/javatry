package org.docksidestage.bizfw.basic.buyticket;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// done tsuji unusedのimport文 by jflute (2025/12/03)

/**
 * 利用可能時間に基づく利用可能ポリシーを表すクラス
 * @author akinari.tsuji
 */
public class TimeBasedPolicy implements IUsagePolicy {
    private final LocalTime availableFrom;

    // done tsuji @param, 全角空白が入ってしまっている by jflute (2025/12/03)
    // IntelliJで警告も出なかったので、全然気づけなかったです。修正する時も一瞬分からずでした。
    // 設定必要ですね...
    /**
     * コンストラクタ
     * @param availableFrom 何時以降利用可能か
     */
    public TimeBasedPolicy(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    // done tsuji @param がない by jflute (2025/12/03)
    /**
     * 利用可能かを判定する関数
     * @param currentTime 現在時刻
     * @return 利用可能かどうか
     */
    public boolean isAvailable(LocalTime currentTime) {
        return !currentTime.isBefore(availableFrom);
    }

    /**
     * 利用可能かを判定した上で例外を発生させる関数
     * @throws OutOfHoursUseException 時間外に利用とした場合
     */
    public void validate(LocalTime currentTime) throws OutOfHoursUseException {
        if (!isAvailable(currentTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            throw new OutOfHoursUseException("You can't use this passport before " + availableFrom.format(formatter) + ".");
        }
    }

    /**
     * 時間外利用の例外
     */
    public static class OutOfHoursUseException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public OutOfHoursUseException(String msg) {
            super(msg);
        }
    }
}
