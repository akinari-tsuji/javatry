package org.docksidestage.bizfw.basic.buyticket;

// done tsuji javadocよろしく by jflute (2025/09/24)

import java.time.LocalTime;

/**
 * 一日中利用可能なポリシーを表すクラス
 * @author akinari.tsuji
 */
public class AllDayPolicy implements IUsagePolicy {
    // done tsuji ちょっとjavadoc変 by jflute (2025/09/24)
    /**
     * 利用可能かどうかを返す関数
     * @return 利用可能であればtrue
     */
    public boolean isAvailable() {
        // done jflute このクラス・メソッドはいるのでしょうか？　akinari.tsuji  (2025/09/03)
        // interfaceを利用して、doInParkのif文を減らすために、当たり前すぎるメソッドを定義するのが良いのかと気になります
        // 「当たり前　＝　ただtrueを返すだけ」の意図です
        // #1on1: 意図を示すってのは良いことですが、このくらいならisAvailable()やらずに、
        // validate()を空っぽにして、コメントで一言、「ここはやることない」とか。
        // e.g.
        //public void validate() throws IllegalStateException {
        //    // AllDayなのでチェックするものが何も無い
        //}
        // 本来、ifベタベタのときに、elseが省略されてるとか、分岐が省略されてるとかが、
        // オブジェクト指向構造になったら、空っぽオーバーライドとして代わりの表現になってると言えるかも。
        //
        // #1on1: isAvailable() を public として呼んでる人はだれもいないので、
        // 現時点ではinterfaceにも無くても良いメソッドはあるかなと。
        // 将来性のことを考えても、isAvailable()をそもそもクラスとinterfaceの構造で隠蔽してるので、
        // それを呼ばないといけないケースがあまり想定されないかなと。(少なくとも必要になったら追加でも)
        return true;
    }

    // done tsuji ちょっとjavadoc変 by jflute (2025/09/24)
    /**
     * 利用可能かを判定する関数
     * @throws IllegalStateException 利用不可の場合
     */
    @Override
    public void validate(LocalTime currentTime) throws IllegalStateException {
        if(!isAvailable()) {
            throw new IllegalStateException();
        }
    }
}
