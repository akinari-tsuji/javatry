package org.docksidestage.bizfw.basic.buyticket;

import java.util.HashMap;
import java.util.Map;

/**
 * チケットの在庫を管理するクラス
 */
public class TicketInventory {
    // TODO tsuji finalを付けてみましょう by jflute (2025/10/22)
    private Map<TicketType, Integer> quantities = new HashMap<>();
//    public final int ONE_DAY_PASS_INITIAL_QUANTITY = 10;
//    final int TWO_DAYS_PASS_INITIAL_QUANTITY = 10;
//    final int FOUR_DAYS_PASS_INITIAL_QUANTITY = 10;
//    final int NIGHT_FROM_EIGHTEEN_PASS_INITIAL_QUANTITY = 10;
//    final int NIGHT_FROM_SEVENTEEN_PASS_INITIAL_QUANTITY = 10;

    public TicketInventory() {
        // done jflute [質問] 初期化する値をこのようにクラス中に定数で持たせるのって悪くないのでしょうか？  akinari.tsuji(2025/09/29)
        // 気になっている点としては、チケットの種別分だけベタがきをしなくてはいけない部分です
        // チケット種が100種類になれば、それだけで100行使ってしまうのもおかしな気がしています。
        // またGemini指摘では、「在庫の初期値はビジネスロジックに当たるから、インスタンス化する際に外部から与えるべき」と言われました
        // 確かにと思う一方で、そうするとTicketBooth側で種類と数の対応を記述する必要があります
        // 結局はどこかに対応を持たなければいけないとは思うのですが、もっとすっきりと対応関係を示す専用の場所におきたいです
        // #1on1: 個々自体がデータ定義と考えたら、内部の定数なしで、, ...ONE_DAY, 10) でも良いかなと (2025/10/08)
        // 定数をpublicで誰かが使う想定とかであれば定数が必要になるけど、publicじゃなければ要らないかなと。
        // (ちなみに、外に出しても、結局外で、ONE_DAY = 10 の列挙はどこかでやらないといけないので変わらず)
        // 一方で、初期値が、チケット種別ごとの定義なのであれば、チケット種別のenumに10を持たせても良いのでは？
        // e.g.
        //  TicketType[] values = TicketType.values();
        //  for (TicketType ticketType : values) {
        //      quantities.put(ticketType,  ticketType.initialQuantity());
        /// }
        // done tsuji ↑という風にしてみましょう by jflute (2025/10/08)
//        quantities.put(TicketType.ONE_DAY,  ONE_DAY_PASS_INITIAL_QUANTITY);
//        quantities.put(TicketType.TWO_DAYS, TWO_DAYS_PASS_INITIAL_QUANTITY);
//        quantities.put(TicketType.FOUR_DAYS, FOUR_DAYS_PASS_INITIAL_QUANTITY);
//        quantities.put(TicketType.NIGHT_FROM_EIGHTEEN, NIGHT_FROM_EIGHTEEN_PASS_INITIAL_QUANTITY);
//        quantities.put(TicketType.NIGHT_FROM_SEVENTEEN, NIGHT_FROM_SEVENTEEN_PASS_INITIAL_QUANTITY);
        TicketType[] values = TicketType.values();
        for (TicketType ticketType : values) {
            quantities.put(ticketType, ticketType.getInitialQuantity());
        }
    }

    public Integer getQuantity(TicketType ticketType) {
        return quantities.get(ticketType);
    }

    public void decreaseQuantity(TicketType ticketType) {
        quantities.put(ticketType, quantities.get(ticketType) - 1);
    }
}
