package org.docksidestage.bizfw.basic.objanimal;

/**
 * 検証用のクラス
 * @author akinari.tsuji
 */
public class MaliciousClass {
    private final BarkingContext barkingContext;

    public MaliciousClass(Animal animal) {
        this.barkingContext = new BarkingContext(animal);
    }

    public void downHitPointDeliberately() {
        barkingContext.downHitPoint();
    }
}
