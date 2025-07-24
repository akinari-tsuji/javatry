/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.javatry.basic;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of data type. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author akinari.tsuji
 */
public class Step03DataTypeTest extends PlainTestCase {

    // ===================================================================================
    //                                                                          Basic Type
    //                                                                          ==========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_datatype_basicType() {
        String sea = "mystic";
        Integer land = 416;
        LocalDate piari = LocalDate.of(2001, 9, 4);
        LocalDateTime bonvo = LocalDateTime.of(2001, 9, 4, 12, 34, 56);
        Boolean dstore = true;
        BigDecimal amba = new BigDecimal("9.4");

        piari = piari.plusDays(1); // piari: 2001/9/5
        land = piari.getYear(); // land: 2001
        bonvo = bonvo.plusMonths(1); // 2001/10/3 12:34:56
        land = bonvo.getMonthValue(); // land: 10
        land--; // land: 9
        if (dstore) { // always true
            BigDecimal addedDecimal = amba.add(new BigDecimal(land)); // addedDecimal: 9.4 + 9
            log(land, amba);
            sea = String.valueOf(addedDecimal); // sea: "18.4"
        }
        log(sea); // your answer? =>  18.4
    }

    // ===================================================================================
    //                                                                           Primitive
    //                                                                           =========
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_datatype_primitive() {
        byte sea = 127; // max
        short land = 32767; // max
        int piari = 1;
        long bonvo = 9223372036854775807L; // max
        float dstore = 1.1f;
        double amba = 2.3d;
        char miraco = 'a';
        boolean dohotel = miraco == 'a'; // true
        if (dohotel && dstore >= piari) { // true && 1.1f > 1
            bonvo = sea; // bonvo: 127
            land = (short) bonvo; // land: 127
            bonvo = piari; // bonvo: 1
            sea = (byte) land; // sea: 1
            if (amba == 2.3D) { // true
                sea = (byte) amba; // sea: 2
            }
        }
        if ((int) dstore > piari) { // 1 > 1 => false
            sea = 0;
        }
        log(sea); // your answer? => 2
    }
    
    // TODO jflute 1on1にて、プリミティブ型の現場での使い分けについて補足する予定 (2025/07/22)

    // ===================================================================================
    //                                                                              Object
    //                                                                              ======
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_datatype_object() {
        St3ImmutableStage stage = new St3ImmutableStage("hangar");
        String sea = stage.getStageName();
        log(sea); // your answer? => hangar
        // St3ImmutableStageクラスのコンストラクタに"hangar"を渡してインスタンスを作成
        // ゲッターで取得した値を使ってString seaを宣言
        // done jflute [質問] String sea = stage.getStageName() では新しいStringオブジェクトが作成されてseaに代入されているのでprivateなのに参照が可能という認識であっていますか？ akinari.tsuji  (2025/07/22)
        // 以下で検証しました
        log(System.identityHashCode(sea));
        log(stage.getStageNameHash());
        stage.putStageHash();

        log(System.identityHashCode(stage.testNumber));
        log(stage.getTestHash());
        // done tsuji [へんじ] stage.getStageName() は、単に stage 内部で保持しているインスタンス... by jflute (2025/07/22)
        // の参照を戻して sea 変数で受け取っているだけなので、新しい String インスタンスはつくられていないです。
        // ここでは "hangar" インスタンス以外の String は存在していないですね。
        //
        // private は、インスタンスというよりも「変数に対してアクセスできるかどうか？」を制御するものなので、
        // stageName変数に外部から直接アクセスはできません。stageName変数が参照しているインスタンスへの参照は、
        // publicである getStageName() を経由して外部に公開されているので、インスタンスに対してはアクセスできています。
        //
        // log(System.identityHashCode(stage.getStageNameHash())); の行ですが...
        // getStageNameHash() がそもそも System.identityHashCode(stageName) を戻しているので...
        // stageNameのハッシュコードのハッシュコードをログに出している状態のようです。
        //  log(System.identityHashCode(sea));
        //  log(stage.getStageNameHash());
        // ↑のよう直すと、同じ値になりました。
        // TODO done jflute なるほどです。メモリ上のアドレスにはアクセスできないものの、アドレス上の番地は渡されてそれを元に参照できるという感じでしょうか？ akinari.tsuji  (2025/07/23)
        // また追加で実験をするために色々やっていたら、privateメンバ変数のtestNumberに107行目のようにアクセスできてしまったのって大丈夫なのでしょうか？ privateだとこのようなアクセスができない認識だったのですが
        // TODO tsuji [へんじ] そういうことです。インスタンスのメモリの場所はぼくらは直接辿ることはできないので... by jflute (2025/07/24)
        // 引数とか戻り値とか変数への代入とかでアドレス(番地)が渡されて、それをもとにインスタンスに参照しています。
        // 結局、アドレスのやり取りしかしてないに等しい、と言っても過言ではないですね(^^。
    }

    private static class St3ImmutableStage {

        private final String stageName;
        private int testNumber;

        public St3ImmutableStage(String stageName) {
            testNumber = 42;
            this.stageName = stageName;
        }

        public String getStageName() {
            return stageName;
        }
        public int getTest() { return testNumber; }
        public int getStageNameHash() { return System.identityHashCode(stageName); }
        public int getTestHash() { return System.identityHashCode(testNumber); }

        public void putStageHash() { System.out.println(System.identityHashCode(stageName)); }
    }
}
