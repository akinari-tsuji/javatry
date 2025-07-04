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

import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author akinari.tsuji
 */
public class Step01VariableTest extends PlainTestCase {

    // ===================================================================================
    //                                                                      Local Variable
    //                                                                      ==============
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_variable_basic() { // example, so begin from the next method
        String sea = "mystic";
        log(sea); // your answer? => mystic
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_initial() {
        String sea = "mystic";
        Integer land = 8;
        String piari = null;
        String dstore = "mai";
        sea = sea + land + piari + ":" + dstore;
        log(sea); // your answer? => mystic8:mai
        // result => mystic8null:mai
        // TODO akinari.tsuji  文字列に他の型を結合すると文字列に変換してくれるので、null -> "null"になる(2025/07/04)
        // TODO akinari.tsuji  ObjectにtoStringメソッドが定義されていて、全てのクラスで継承されている。(2025/07/04)
        // TODO akinari.tsuji Rubyも同様にObejctが全てのクラスのスーパークラス。to_sメソッドが近そう。 (2025/07/04)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_basic() {
        String sea = "mystic";
        String land = "oneman";
        sea = land;
        land = land + "'s dreams";
        log(sea); // your answer? => oneman
        // result => oneman
        // TODO akinari.tsuji JavaのStringは値が変更不可 (2025/07/04)
        // TODO akinari.tsuji 内部でString poolというものを持っていて、同じものがなければ作成するっぽい (2025/07/04)
        // TODO akinari.tsuji インスタンスはString pool上の値を参照している (2025/07/04)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_int() {
        int sea = 94;
        int land = 415;
        sea = land;
        land++;
        log(sea); // your answer? => 415
        // TODO akinari.tsuji Javaにはプリミティブ型と参照型があるらしい (2025/07/04)
        // TODO akinari.tsuji intはプリミティブ型なので値自体を参照している (2025/07/04)
        // TODO jflute Integerは参照型と聞いたのですが以下の結果が(415, 416)になるのは、IntegerもStringと同じく値の変更が不可能で、b++の際に新しいオブジェクトが出来上がり参照先が変わるため？　akinari.tsuji  (2025/07/04)
        // Integer a = 94;
        // Integer b = 415;
        // a = b;
        // b++;
        // log(a, b);
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_BigDecimal() {
        BigDecimal sea = new BigDecimal(94);
        BigDecimal land = new BigDecimal(415);
        sea = land;
        sea = land.add(new BigDecimal(1));
        sea.add(new BigDecimal(1));
        log(sea); // your answer? => 416
        // TODO akinari.tsuji  BigDecimalは変更不可な型っぽい　(2025/07/04)
        // TODO akinari.tsuji BigDecimal.add(BigDecimal augend)はメンバ変数の値と引数の値を加算して返すだけで、メンバ変数の値は変化しない (2025/07/04)
    }

    // ===================================================================================
    //                                                                   Instance Variable
    //                                                                   =================
    private String instanceBroadway;
    private int instanceDockside;
    private Integer instanceHangar;
    private String instanceMagiclamp;

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_String() {
        String sea = instanceBroadway;
        log(sea); // your answer? => null
        // TODO akinari.tsuji コンストラクタの定義がなく、メンバ変数のinstanceBroadwayは値が未定義 (2025/07/04)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_int() {
        int sea = instanceDockside;
        log(sea); // your answer? => 0
        // TODO akinari.tsuji 数値型ではnullではなく0で初期化される (2025/07/04)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_Integer() {
        Integer sea = instanceHangar;
        log(sea); // your answer? => 0
        // result => null
        // TODO jflute 参照型では参照先の値を示す変数がnullを示している？ 参照型は参照先を示す変数と諸々のメソッドを含むクラス？　参照先を示すだけの変数だから値の変更ができない？　Javaの基本的な言語仕様をさらっと抑えられる良書はありますか？（Java未経験です）　by akinari.tsuji (2025/07/04)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_via_method() {
        instanceBroadway = "bbb";
        instanceMagiclamp = "magician";
        helpInstanceVariableViaMethod(instanceMagiclamp);
        String sea = instanceBroadway + "|" + instanceDockside + "|" + instanceHangar + "|" + instanceMagiclamp;
        log(sea); // your answer? => bighand|1|null|burn
        // result => bighand|1|null|magisian
        // TODO akinari.tsuji メンバ変数なのでhelpInstanceVariableViaMethodで値が変更される (2025/07/04)
        // TODO akinari.tsuji instanceMagiclampはメソッドの変数で渡されているのでこちらが優先されて更新されない (2025/07/04)
    }

    private void helpInstanceVariableViaMethod(String instanceMagiclamp) {
        instanceBroadway = "bigband";
        ++instanceDockside;
        instanceMagiclamp = "burn";
    }

    // ===================================================================================
    //                                                                     Method Argument
    //                                                                     ===============
    // -----------------------------------------------------
    //                                 Immutable Method-call
    //                                 ---------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_immutable_methodcall() {
        String sea = "harbor";
        int land = 415;
        helpMethodArgumentImmutableMethodcall(sea, land);
        log(sea); // your answer? => harbor
        // TODO akinari.tsuji helpMethodArgumentImmutableMethodcall側ではlandはメソッド内のローカル変数になり変更が影響しない (2025/07/04)
        // TODO akinari.tsuji seaについては変更不可なオブジェクトで、concatは値を返すだけでメンバ変数の値は変わらない (2025/07/04)
    }

    private void helpMethodArgumentImmutableMethodcall(String sea, int land) {
        ++land;
        String landStr = String.valueOf(land); // is "416"
        sea.concat(landStr);
    }

    // -----------------------------------------------------
    //                                   Mutable Method-call
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_mutable_methodcall() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentMethodcall(sea, land);
        log(sea); // your answer? => harbor416
        // TODO akinari.tsuji Builderというデザインパターンがあるらしい（勉強します） (2025/07/04)
    }

    private void helpMethodArgumentMethodcall(StringBuilder sea, int land) {
        ++land;
        sea.append(land);
    }

    // -----------------------------------------------------
    //                                   Variable Assignment
    //                                   -------------------
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_method_argument_variable_assignment() {
        StringBuilder sea = new StringBuilder("harbor");
        int land = 415;
        helpMethodArgumentVariable(sea, land);
        log(sea); // your answer? => harbor
        // TODO akinari.tsuji 関数での引数は値渡しでhelpメソッド側で代入しても呼び出し元の変数には変更が生じない (2025/07/04)
    }

    private void helpMethodArgumentVariable(StringBuilder sea, int land) {
        ++land;
        String seaStr = sea.toString(); // is "harbor"
        sea = new StringBuilder(seaStr).append(land);
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Define variables as followings:
     * <pre>
     * o local variable named sea typed String, initial value is "mystic"
     * o local variable named land typed Integer, initial value is null
     * o instance variable named piari typed int, without initial value
     * o show all variables by log() as comma-separated
     * </pre>
     * (変数を以下のように定義しましょう):
     * <pre>
     * o ローカル変数、名前はsea, 型はString, 初期値は "mystic"
     * o ローカル変数、名前はland, 型はInteger, 初期値は null
     * o インスタンス変数、名前はpiari, 型はint, 初期値なし
     * o すべての変数をlog()でカンマ区切りの文字列で表示
     * </pre>
     */
    int piari;
    public void test_variable_writing() {
        // define variables here
        String sea = "mystic";
        Integer land = null;

        log(sea, land, piari);
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Make your original exercise as question style about variable. <br>
     * (変数についてあなたのオリジナルの質問形式のエクササイズを作ってみましょう)
     * <pre>
     * _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
     * your question here (ここにあなたの質問を):
     * log(sea)の出力結果は？
     * _/_/_/_/_/_/_/_/_/_/
     * </pre>
     */
    public void test_variable_yourExercise() {
        // write your code here
        StringBuilder sea = new StringBuilder("mystic");
        int land = 42;
        String dstore = null;
        helperMethod(sea, land, dstore);
        log(sea);
    }

    private void helperMethod(StringBuilder sea, int land, String dstore) {
        sea.append(land);
        sea = new StringBuilder(sea.toString()).append(dstore);
    }
}
