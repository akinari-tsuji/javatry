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

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of method. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author akinari.tsuji
 */
public class Step04MethodTest extends PlainTestCase {

    // ===================================================================================
    //                                                                         Method Call
    //                                                                         ===========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_method_call_basic() {
        String sea = supplySomething();
        log(sea); // your answer? => over
        // supplySomething()では"over"を返す。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_call_many() {
        String sea = functionSomething("mystic"); // mysmys
        consumeSomething(supplySomething()); // 呼び出した関数がスコープの変数に対するreplaceなのでmysmysのまま
        runnableSomething(); // 関数ないのローカル変数に対する操作なので影響なし
        log(sea); // your answer? => mysmys
    }

    private String functionSomething(String name) {
        String replaced = name.replace("tic", "mys");
        log("in function: {}", replaced);
        return replaced;
    }

    private String supplySomething() {
        String sea = "over";
        log("in supply: {}", sea);
        return sea;
    }

    private void consumeSomething(String sea) {
        log("in consume: {}", sea.replace("over", "mystic"));
    }

    private void runnableSomething() {
        String sea = "outofshadow";
        log("in runnable: {}", sea);
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_object() {
        St4MutableStage mutable = new St4MutableStage();
        int sea = 904;
        boolean land = false;
        helloMutable(sea - 4, land, mutable); // piari変数は参照先のアドレス値を渡している。helloMutable内でセッターを呼び出せば参照先の値を変更できる piari.stageName: mystic
        if (!land) {
            sea = sea + mutable.getStageName().length(); // 904 + 6 = 910
        }
        log(sea); // your answer? => 910
    }

    private int helloMutable(int sea, Boolean land, St4MutableStage piari) {
        sea++;
        land = true;
        piari.setStageName("mystic");
        return sea;
    }

    private static class St4MutableStage {

        private String stageName;

        public String getStageName() {
            return stageName;
        }

        public void setStageName(String stageName) {
            this.stageName = stageName;
        }
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private int inParkCount;
    private boolean hasAnnualPassport;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_method_instanceVariable() {
        hasAnnualPassport = true;
        int sea = inParkCount; // nullなので0になる？
        // done tsuji [ふぉろー] ↑inParkCountは int型なので、デフォルトはnullではなく0です by jflute (2025/07/22)
        // (プリミティブ型の変数はnullの状態が発生しない、というJavaのルール)
        // done jflute なるほどです。確かにメモリ確保して値が何もないというのは違和感あったのでしっくりきました！ akinari.tsuji  (2025/07/23)
        offAnnualPassport(hasAnnualPassport); // 引数に対する操作なので何もしない
        for (int i = 0; i < 100; i++) {
            goToPark(); // メンバ変数のhasAnnualPassportはtrueのままなので加算される => inParkCountは100に
        }
        ++sea; // 1
        sea = inParkCount; // 100
        log(sea); // your answer? => 100
    }

    private void offAnnualPassport(boolean hasAnnualPassport) {
        hasAnnualPassport = false;
    }

    private void goToPark() {
        if (hasAnnualPassport) {
            ++inParkCount;
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    // write instance variables here
    /**
     * Make private methods as followings, and comment out caller program in test method:
     * <pre>
     * o replaceAwithB(): has one argument as String, returns argument replaced "A" with "B" as String 
     * o replaceCwithB(): has one argument as String, returns argument replaced "C" with "B" as String 
     * o quote(): has two arguments as String, returns first argument quoted by second argument (quotation) 
     * o isAvailableLogging(): no argument, returns private instance variable "availableLogging" initialized as true (also make it separately)  
     * o showSea(): has one argument as String argument, no return, show argument by log()
     * </pre>
     * (privateメソッドを以下のように定義して、テストメソッド内の呼び出しプログラムをコメントアウトしましょう):
     * <pre>
     * o replaceAwithB(): 一つのString引数、引数の "A" を "B" に置き換えたStringを戻す 
     * o replaceCwithB(): 一つのString引数、引数の "C" を "B" に置き換えたStringを戻す 
     * o quote(): 二つのString引数、第一引数を第二引数(引用符)で囲ったものを戻す 
     * o isAvailableLogging(): 引数なし、privateのインスタンス変数 "availableLogging" (初期値:true) を戻す (それも別途作る)  
     * o showSea(): 一つのString引数、戻り値なし、引数をlog()で表示する
     * </pre>
     */
    public void test_method_making() {
        // use after making these methods
        String replaced = replaceCwithB(replaceAwithB("ABC")); // BBB
        String sea = quote(replaced, "'"); // 'BBB'
        if (isAvailableLogging()) { // true
            showSea(sea); // 'BBB'
        }
    }

    // done tsuji [いいね] メソッドの定義順が呼び出し順序と一致していてわかりやすいです by jflute (2025/07/22)
    // ありがとうございます！
    // #1on1: 切り出しのみ目的のprivateメソッドの定義位置と再利用を目的としてprivateメソッドの定義位置のお話
    // write methods here
    private String replaceAwithB(String s) { return s.replace("A", "B"); }
    private String replaceCwithB(String s) { return s.replace("C", "B"); }
    private String quote(String s, String q) { return q + s + q; }
    private boolean availableLogging = true;
    private boolean isAvailableLogging() { return availableLogging; }
    private void showSea(String s) { log(s); }
    
    // #1on1: [話題] DDDのお話
    // Value Objectとは？ (intとかIntegerとの違い)
    //  → 業務的な意味を持っているかどうか？
    //  → 業務的な意味を単に変数だけで表現しているのか？型で表現しているのか？
    // pp 意味を持たせることで...
    // o コンパイルセーフ (業務的な値の概念が違ったときにエラー)
    // o 可読性 (業務的な振る舞いを表現できる)
    // 
    // pp DDDの歴史と、くぼのDDDの歴史の体感のお話
    // o (1on1にて詳しく)
}
