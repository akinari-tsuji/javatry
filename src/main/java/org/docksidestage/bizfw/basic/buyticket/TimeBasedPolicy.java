package org.docksidestage.bizfw.basic.buyticket;

import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 利用可能時間に基づく利用可能ポリシーを表すクラス
 * @author akinari.tsuji
 */
public class TimeBasedPolicy implements IUsagePolicy {
    private final LocalTime availableFrom;

    /**
     * コンストラクタ
     * @param availableFrom　何時以降利用可能か
     */
    public TimeBasedPolicy(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    /**
     * 利用可能かを判定する関数
     * @return 利用可能かどうか　
     */
    public boolean isAvailable(LocalTime currentTime) {
        return !currentTime.isBefore(availableFrom);
    }

    /**
     * 利用可能かを判定した上で例外を発生させる関数
     * @throws IllegalStateException 時間外に利用とした場合
     */
    public void validate(LocalTime currentTime) throws IllegalStateException {
        if (!isAvailable(currentTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            throw new IllegalStateException("You can't use this passport before " + availableFrom.format(formatter) + ".");
        }
    }
}
