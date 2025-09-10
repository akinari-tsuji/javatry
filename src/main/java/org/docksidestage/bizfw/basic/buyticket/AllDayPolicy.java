package org.docksidestage.bizfw.basic.buyticket;

public class AllDayPolicy implements IUsagePolicy {
    /**
     * 金額を返すゲッター
     * @return チケットの金額
     */
    @Override
    public boolean isAvailable() {
        // TODO jflute このクラス・メソッドはいるのでしょうか？　akinari.tsuji  (2025/09/03)
        // interfaceを利用して、doInParkのif文を減らすために、当たり前すぎるメソッドを定義するのが良いのかと気になります
        return true;
    }

    /**
     * 入園上限回数を返すゲッター
     * @return チケットの入園上限回数
     */
    @Override
    public void validate() throws IllegalStateException {
        if(!isAvailable()) {
            throw new IllegalStateException();
        }
    }
}
