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
    // #1on1: ここポイント(いいね)。仲介役を誰もがnewできたら意味ないけど、newを制限していることが大事 (2026/02/04)
    // 確かに、packageの設定は自由なので、悪意のある人が同じpackageのクラス作ってnewすることはできちゃうけど...
    // まあそもそも、ぼくらは悪意のある人を防ぐことはできない。(いざとなったら誰でもReflectionとかでなんでもできるし)
    // ぼくらが防ぎたいのは、善意のある人の間違ったり勘違いしたりを防ぎたい。
    // という観点でいうと、これで十分ではある。
    // 完全な隠蔽をするのは大抵の場合難しいので、隠蔽感を強くすることが大事。とも言える。
    // (外部のセキュリティの話とは違うので)
    // でも、つじさんの自分で検証して、同じpackageだったら呼べちゃうってのを検知できたのは素晴らしい。
    BarkingContext(Animal animal) {
        this.animal = animal;
    }

    // ===================================================================================
    //                                                                            HitPoint
    //                                                                           =========
    public void downHitPoint() {
        animal.downHitPointForContext();
    }

    // ===================================================================================
    //                                                                            BarkWord
    //                                                                           =========
    public String getBarkWord() {
        return animal.getBarkWord();
    }
}
