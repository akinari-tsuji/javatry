package org.docksidestage.bizfw.basic.buyticket;

public class AllDayPolicy implements IUsagePolicy {
    @Override
    public boolean isAvailable() {
        // TODO このクラス・メソッドはいるのでしょうか？　akinari.tsuji  (2025/09/03)
        // interfaceを利用して、doInParkのif文を減らすために、当たり前すぎるメソッドを定義するのが良いのかと気になります
        return true;
    }

    @Override
    public void validate() throws IllegalStateException {
        if(!isAvailable()) {
            throw new IllegalStateException();
        }
    }
}
