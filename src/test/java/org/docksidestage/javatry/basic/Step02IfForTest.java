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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.docksidestage.unit.PlainTestCase;

// done tsuji [事務ごと] javatryでは、javadocのauthorをお願いします by jflute (2025/07/15)
// https://dbflute.seasar.org/ja/tutorial/handson/review/codingpolicy.html#minjavadoc
/**
 * The test of if-for. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author akinari.tsuji
 */
public class Step02IfForTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        if Statement
    //                                                                        ============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_if_basic() { // example, so begin from the next method
        int sea = 904;
        if (sea >= 904) {
            sea = 2001;
        }
        log(sea); // your answer? => 2001
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_else_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else {
            sea = 7;
        }
        log(sea); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_basic() {
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (sea >= 904) {
            sea = 7;
        } else if (sea >= 903) {
            sea = 8;
        } else {
            sea = 9;
        }
        log(sea); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_if_elseif_nested() {
        boolean land = false;
        int sea = 904;
        if (sea > 904) {
            sea = 2001;
        } else if (land && sea >= 904) {
            sea = 7;
        } else if (sea >= 903 || land) {
            sea = 8;
            if (!land) {
                land = true;
            } else if (sea <= 903) {
                sea++;
            }
        } else if (sea == 8) {
            sea++;
            land = false;
        } else {
            sea = 9;
        }
        if (sea >= 9 || (sea > 7 && sea < 9)) {
            sea--;
        }
        if (land) {
            sea = 10;
        }
        log(sea); // your answer? => 10
    }
    // TODO jflute #1on1 にて、ソースコードリーディングのお話をする予定 (2025/07/15)

    // ===================================================================================
    //                                                                       for Statement
    //                                                                       =============
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_inti_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (int i = 0; i < stageList.size(); i++) {
            String stage = stageList.get(i);
            if (i == 1) {
                sea = stage;
            }
        }
        func_for_my_experience_by_jflute();
        log(sea); // your answer? => dockside
        // done jflute Javaにおいて、参照型のインスタンスがreturnされた場合って何が返ってくるのでしょうか？　akinari.tsuji  (2025/07/08)
        // 値（プリミティブ型）を返す場合は値が返ってくるかと思うのですが、参照型のインスタンスの場合、参照しているアドレスの値を返すのでしょうか？
        // その場合、参照しているアドレスの値を保持していた変数がスコープ外になった場合はどうなるのでしょう
        // Integer b = func_for_my_experience();
        // log(b, System.identityHashCode(b));
        // result
        // 2025-07-08 20:04:20,639 [main] DEBUG (PlainTestCase@log():711) - 42, 1225373914, 42, 60830820
        // 2025-07-08 20:04:20,640 [main] DEBUG (PlainTestCase@log():711) - 42, 60830820
        // 参照しているアドレスを返しているっぽい
        //
        // done tsuji [へんじ] 戻り値の場合も、参照型(オブジェクト型)であれば、単にアドレスが戻ってきて... by jflute (2025/07/15)
        // 呼び出し側でそのアドレスを受け取ってるだけですね。そのアドレスを保持した変数を経由してインスタンスを操作するわけで。
        // そして、メソッドなどが終了して、その変数がスコープ外になって破棄されたら、参照されていたインスタンスが誰からも参照されなくなります。
        // 参照されなくなったインスタンスは二度と操作することはできないので、Javaがガベージコレクションの仕組みでいつしか破棄します。
        // 
        // ---
        // 
        // また、aとbのidentityHashCodeの値が違うのはbのインスタンスを作成するときに、aの値をメモリ上の他のアドレスに配置してbはそれを参照するようにしているからでしょうか？
        // こうすることで変数aの寿命がbに悪影響を及ぼすことがない、という認識であっていますか？
        //
        // done tsuji [へんじの続き] ↑の場合、42のIntegerインスタンス1個目が生成されてaで保持しておきます by jflute (2025/07/15)
        // done tsuji [へんじ] 戻り値の場合も、参照型(オブジェクト型)であれば、単にアドレスが戻ってきて... by jflute (2025/07/15)
        // ↓のプログラムはちょっとわかりづらいところがあるので少し書き換えさせてください。

        // TODO tsuji いいねいいねの疑問ですね！GCは深いぞー by shiny (2025/08/12)
    }

    // done tsuji [へんじの続き] ↑の場合、42のIntegerインスタンス1個目が生成されてaで保持しておきます by jflute (2025/07/15)
    // そして、aと同じ値を持つIntegerインスタンス2個目が生成されてbに保持しておきます。
    // 同じ値を持つわけですが、インスタンスとしては2回newしていますから別物です。なのでhashも違う値になります。
    // (中身が同じだろうが別だろうが、互いにnewしていればインスタンス自体は別物)
    public Integer func_for_my_experience_by_jflute() { // jfluteの書き換えコード
        // e.g. 42, 932607259, 42, 1740000325
        Integer a = new Integer(42);
        Integer b = new Integer(a);
        log(a, System.identityHashCode(a), b, System.identityHashCode(b));

        return b;
    }

    // ↑↑↑

    // done tsuji [へんじの続きの続き] 元のコードだと、int a = 42; のような a がプリミティブ型になって、b のIntegerに渡されています。 (2025/07/15)
    // プリミティブ型は値渡しですから、b の Integer からすると、引数で渡された int の値が a から来たかどうか？
    // は気にせず、ただ 42 という値を受け取ったというだけになります。42という値をコピーされて渡ってると言っても良いです。
    // そして、aの方ですが、identityHashCode() の引数のところで Object 型に暗黙の変換が行われています。
    // public static native int identityHashCode(Object x);
    // プリミティブ型はオブジェクトではないので、本来はObject引数に指定することはできないのですが...
    // Javaのオートボクシングという機能によって、暗黙的にintがIntegerに変換されます。
    // 実はここで内部的に new Integer(a) が発生した上でidentityHashCodeが呼び出されます。
    // ゆえに、こっちも2回newしていますから別物となります。
    public Integer func_for_my_experience() { // 元のつじさんのコード
        int a = 42;
        Integer b = new Integer(a);
        log(a, System.identityHashCode(a), b, System.identityHashCode(b));

        return b;
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_basic() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            sea = stage;
        }
        log(sea); // your answer? => magiclamp
        // akinari.tsuji C++にもこんな表記がありました。 (2025/07/08)
        // for(auto n: G)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_foreach_continueBreak() {
        List<String> stageList = prepareStageList();
        String sea = null;
        for (String stage : stageList) {
            if (stage.startsWith("br")) {
                continue;
            }
            sea = stage;
            if (stage.contains("ga")) {
                break;
            }
        }
        log(sea); // your answer? => hangar
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_for_listforeach_basic() {
        List<String> stageList = prepareStageList();
        StringBuilder sb = new StringBuilder();
        stageList.forEach(stage -> {
            if (sb.length() > 0) {
                return;
            }
            if (stage.contains("i")) {
                sb.append(stage);
            }
        });
        String sea = sb.toString();
        log(sea); // your answer? => dockside
        // akinari.tsuji Javascriptでも同じようなコールバック関数を引数にとるforEachがありました (2025/07/08)
        // Javaではstage -> {...} という書き方で関数になっているのでしょうか？
        // 調べたらラムダ式なんですね
        // done tsuji [へんじ] 文法的な厳密性で言うと、Javaは関数はないので... by jflute (2025/07/15)
        // ラムダ式で、1メソッドしか持ってないクラスを表現して、関数っぽいく振る舞うようにしている感じです。
        // #1on1: ラムダ式も内部的にクラス話、step8のちょい紹介

        // TODO tsuji rubyにもCallbackみたいなものはありますか...?(by rubyを何もしらない人間) by shiny (2025/08/12)
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Make list containing "a" from list of prepareStageList() and show it as log by loop. (without Stream API) <br>
     * (prepareStageList()のリストから "a" が含まれているものだけのリストを作成して、それをループで回してログに表示しましょう。(Stream APIなしで))
     */
    public void test_iffor_making() {
        // write if-for here
        List<String> stageList = prepareStageList();
        stageList.forEach(stage -> {
            if (stage.contains("a")) {
                log(stage);
            }
        });
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Change foreach statement to List's forEach() (keep result after fix) <br>
     * (foreach文をforEach()メソッドへの置き換えてみましょう (修正前と修正後で実行結果が同じになるように))
     */
    public void test_iffor_refactor_foreach_to_forEach() {
        List<String> stageList = prepareStageList();
        String sea = null;
        @SuppressWarnings("unused") // 警告ノイズにならないように便宜上 (業務なら消すけど)
        final AtomicBoolean shouldBreak = new AtomicBoolean(false);
        final AtomicReference<String> seaRef = new AtomicReference<>("");
        final StringBuilder test = new StringBuilder(); //検証
        stageList.forEach(stage -> {
            if (seaRef.get().contains("ga")) {
                return;
            }
            if (stage.startsWith("br")) {
                return;
            }
            seaRef.set(stage);
            test.append(stage); // 検証
            // test = new StringBuilder(); // 検証：これは不可
            // if (stage.contains("ga")) {
            //    shouldBreak.set(true);
            // }
        });
        sea = seaRef.get();
        log(sea); // should be same as before-fix
        log(test.toString()); // 検証
        // TODO [memo] akinari.tsuji forEachの中では変数への代入はfinal（変更不可）である必要があるらしく代入ができない (2025/07/08)
        // また、for文におけるbreak文をどう表現するべきか（一旦、今は条件式を書き換えて対応）わからず
        // 続きは明日以降やる
        // TODO [memo] akinari.tsuji 結局わからずGeminiに頼った。Atomicというのをよく理解しておらず。 (2025/07/17)
        // Atomicはマルチスレッドから利用するための変数。原子性（set, getが他のスレッドに邪魔されない）を持つからAtomic（これは予想）
        // 今回、Atomicを使うことで問題を解消できたのは参照先の値を変更することができるという性質を持っていたため
        // finalをAtomicに対して設定することで、参照先の変更ができなくなる（一方で、参照先の値自体の変更は可能）
        // なので、final StringBuilderが参照する先に保持される値はappendで変更ができる(検証部分）
        // done tsuji [ふぉろー] AtomicBoolean とか AtomicReference とかは、本来の目的はマルチスレッド対応ですが... by jflute (2025/07/18)
        // ここでは都合の良い mutable な boolean, mutable な String, というところだけに着目してオススメされたのだと思います。
        // まあ、このエクササイズとしてはそれも一つの回避です。でも、StringBuilderでも十分同じことができます。
        // done tsuji [ふぉろー] final は変数のimmutableですね。変数のimmutable性とインスタンスのimmutable性... by jflute (2025/07/18)
        // 二つあって、変数の方は参照先を差し替えられるかどうか？インスタンスの方は中身を変化させられるかどうか？
        // done tsuji 修行++: 今のコードでGoodですがパズル問題として頭の体操的な追加修行を by jflute (2025/07/18)
        // AtomicBoolean の shouldBreak の変数は使わずに同じことが実現できます。
        // つまり、AtomicReference<String> seaRef だけで全く同じ挙動を実現できるでしょう。
        // done jflute seaRefに"ga"が含まれることを条件にreturnするように変更しました akinari.tsuji  (2025/07/18)
        // done tsuji [いいね]↑ Good, そういうことです！(^^ by jflute (2025/07/22)
    }

    /**
     * Make your original exercise as question style about if-for statement. <br>
     * (if文for文についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * 最後の出力は？
     * また、finale Userなのにnameを変更できる理由は？
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    class User {
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // done tsuji [いいね] immutable の理解の正確性を問う良いエクササイズですね(^^ by jflute (2025/07/18)
    public void test_iffor_yourExercise() {
        List<String> stageList = prepareStageList();
        final User user = new User();
        stageList.forEach(stage -> {
            if (stage.contains("o")) {
                return;
            }
            user.setName(stage);
        });
        log(user.getName());
    }

    // ===================================================================================
    //                                                                        Small Helper
    //                                                                        ============
    private List<String> prepareStageList() {
        List<String> stageList = new ArrayList<>();
        stageList.add("broadway");
        stageList.add("dockside");
        stageList.add("hangar");
        stageList.add("magiclamp");
        return stageList;
    }
}
