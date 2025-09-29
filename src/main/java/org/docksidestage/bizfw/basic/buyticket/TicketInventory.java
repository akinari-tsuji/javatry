package org.docksidestage.bizfw.basic.buyticket;

import java.util.HashMap;
import java.util.Map;

/**
 * チケットの在庫を管理するクラス
 */
public class TicketInventory {
    private Map<TicketType, Integer> quantities = new HashMap<>();
    final int ONE_DAY_PASS_INITIAL_QUANTITY = 10;
    final int TWO_DAYS_PASS_INITIAL_QUANTITY = 10;
    final int FOUR_DAYS_PASS_INITIAL_QUANTITY = 10;
    final int NIGHT_FROM_EIGHTEEN_PASS_INITIAL_QUANTITY = 10;
    final int NIGHT_FROM_SEVENTEEN_PASS_INITIAL_QUANTITY = 10;

    public TicketInventory() {
        // TODO jflute [質問] 初期化する値をこのようにクラス中に定数で持たせるのって悪くないのでしょうか？  akinari.tsuji(2025/09/29)
        // 気になっている点としては、チケットの種別分だけベタがきをしなくてはいけない部分です
        // チケット種が100種類になれば、それだけで100行使ってしまうのもおかしな気がしています。
        // またGemini指摘では、「在庫の初期値はビジネスロジックに当たるから、インスタンス化する際に外部から与えるべき」と言われました
        // 確かにと思う一方で、そうするとTicketBooth側で種類と数の対応を記述する必要があります
        // 結局はどこかに対応を持たなければいけないとは思うのですが、もっとすっきりと対応関係を示す専用の場所におきたいです
        quantities.put(TicketType.ONE_DAY,  ONE_DAY_PASS_INITIAL_QUANTITY);
        quantities.put(TicketType.TWO_DAYS, TWO_DAYS_PASS_INITIAL_QUANTITY);
        quantities.put(TicketType.FOUR_DAYS, FOUR_DAYS_PASS_INITIAL_QUANTITY);
        quantities.put(TicketType.NIGHT_FROM_EIGHTEEN, NIGHT_FROM_EIGHTEEN_PASS_INITIAL_QUANTITY);
        quantities.put(TicketType.NIGHT_FROM_SEVENTEEN, NIGHT_FROM_SEVENTEEN_PASS_INITIAL_QUANTITY);
    }

    public Integer getQuantity(TicketType ticketType) {
        return quantities.get(ticketType);
    }

    public void decreaseQuantity(TicketType ticketType) {
        quantities.put(ticketType, quantities.get(ticketType) - 1);
    }
}
