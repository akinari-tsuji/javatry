package org.docksidestage.bizfw.basic.objanimal;

/**
 * BarkingProcessがAnimalのprivateメソッドを呼び出すためのクラス
 * @author akinari.tsuji
 */
public class BarkingContext {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final Animal animal;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                           =========
    BarkingContext(Animal animal) {
        this.animal = animal;
    }

    // ===================================================================================
    //                                                                            HitPoint
    //                                                                           =========
    public void downHitPoint() {
        animal.downHitPointForContext();
    }
}
