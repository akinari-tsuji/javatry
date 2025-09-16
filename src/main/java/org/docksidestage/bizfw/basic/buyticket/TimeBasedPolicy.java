package org.docksidestage.bizfw.basic.buyticket;

import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * 利用可能時間に基づく利用可能ポリシーを表すクラス
 * @author akinari.tsuji
 */
public class TimeBasedPolicy implements IUsagePolicy {
    private final Clock clock;
    private final LocalTime availableFrom;

    /**
     * コンストラクタ
     * @param availableFrom　何時以降利用可能か
     */
    public TimeBasedPolicy(Clock clock, LocalTime availableFrom) {
        this.clock = clock;
        this.availableFrom = availableFrom;
    }

    /**
     * 利用可能かを判定する関数
     * @return 利用可能かどうか　
     */
    @Override
    public boolean isAvailable() {
        LocalTime currentTime = LocalTime.now(this.clock);
        return currentTime.isAfter(availableFrom);
    }

    /**
     * 利用可能かを判定した上で例外を発生させる関数
     * @throws IllegalStateException 時間外に利用とした場合
     */
    @Override
    public void validate() throws IllegalStateException {
        if (!isAvailable()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
            throw new IllegalStateException("You can't use this passport before " + availableFrom.format(formatter) + ".");
        }
    }
}
