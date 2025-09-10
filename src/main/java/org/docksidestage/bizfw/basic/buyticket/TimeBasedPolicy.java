package org.docksidestage.bizfw.basic.buyticket;

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
     * @param availableFrom
     */
    public TimeBasedPolicy(LocalTime availableFrom) {
        this.availableFrom = availableFrom;
    }

    /**
     * 利用可能かを判定する関数
     * @return 利用可能かどうか　
     */
    @Override
    public boolean isAvailable() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.isAfter(availableFrom);
    }

    /**
     * 利用可能かを判定した上で例外を発生させる関数
     * @throws IllegalStateException
     */
    @Override
    public void validate() throws IllegalStateException {
        if (!isAvailable()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
            throw new IllegalStateException("You can't use this passport before " + availableFrom.format(formatter) + ".");
        }
    }
}
