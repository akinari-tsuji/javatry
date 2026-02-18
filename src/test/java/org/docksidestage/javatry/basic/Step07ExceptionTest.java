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

import java.io.File;
import java.io.IOException;

import org.docksidestage.bizfw.basic.supercar.SupercarClient;
import org.docksidestage.javatry.basic.st7.St7BasicExceptionThrower;
import org.docksidestage.javatry.basic.st7.St7ConstructorChallengeException;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of variable. <br>
 * Operate as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りに実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author akinari.tsuji
 */
public class Step07ExceptionTest extends PlainTestCase {

    // ===================================================================================
    //                                                                               Basic
    //                                                                               =====
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_exception_basic_catchfinally() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        StringBuilder sea = new StringBuilder();
        try {
            thrower.land(); // ここでIllegalStateExceptionが投げられる
            sea.append("dockside"); // なのでこの行は実行されない
        } catch (IllegalStateException e) { // キャッチ！
            sea.append("hangar");
        } finally {
            sea.append("broadway"); // javaではfinallyで最後に確実に処理するらしい
            // Rubyではensure
        }
        log(sea); // your answer? => hangarbroadway
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_basic_message() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        String sea = null;
        try {
            thrower.land(); // IllegalStateExceptionが投げられる
            fail("no way here"); // failは期待する例外が発生しないときに、テストを失敗させるために利用されるらしい
            // 今回は期待する例外が投げられるのでスルー
        } catch (IllegalStateException e) {
            sea = e.getMessage(); // oneman at showbase
        }
        log(sea); // your answer? => oneman at showbase
    }

    /**
     * What class name and method name and row number cause the exception? (you can execute and watch logs) <br>
     * (例外が発生したクラス名とメソッド名と行番号は？(実行してログを見て良い))
     */
    public void test_exception_basic_stacktrace() {
        St7BasicExceptionThrower thrower = new St7BasicExceptionThrower();
        try {
            thrower.land();
            fail("no way here");
        } catch (IllegalStateException e) {
            log(e);
            StackTraceElement element = e.getStackTrace()[0];
            log("Class: " + element.getClassName());
            log("Method: " + element.getMethodName());
            log("Line: " + element.getLineNumber());
        }
        // your answer? => St7BasicExceptionThrower, oneman(), 40行目
    }

    // ===================================================================================
    //                                                                           Hierarchy
    //                                                                           =========
    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_exception_hierarchy_Runtime_instanceof_RuntimeException() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof RuntimeException;
        log(sea); // your answer? => true
        // IllegalStateExceptionはRuntimeExceptionをextendsしているのでtrue
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Exception() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Exception;
        log(sea); // your answer? => true
        // RuntimeExceptionがさらにExceptionをextendsしているのでtrue
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Error() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Error;
        log(sea); // your answer? => false
        // Throwable
        //      Exception
        //          RuntimeException
        //              IllegalStateException
        //      Error
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Runtime_instanceof_Throwable() {
        Object exp = new IllegalStateException("mystic");
        boolean sea = exp instanceof Throwable;
        log(sea); // your answer? => true
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_hierarchy_Throwable_instanceof_Exception() {
        Object exp = new Throwable("mystic");
        boolean sea = exp instanceof Exception;
        log(sea); // your answer? => false
        // 逆なのでfalse
    }

    // #1on1: (自然言語の)例外とエラーという言葉の違い、JavaのExceptionとErrorの違い (2026/02/18)
    // (自然言語の)例外とエラー: エラーという言葉には、ダメというニュアンスが含まれている。
    // 例外という言葉には、「ダメではないけどまあまあまあみたい」by つじさん
    // Good, ダメとは限らなくて、「普通と違う」ってことしか言ってない。
    // 普通と違うの中に、「ダメなもの」もあるかもだし、「普通じゃないだけでOKなもの」もあるかも。
    //
    // JavaのExceptionとErrorの違い。
    // IllegalArgumentException は、なぜ IllegalArgumentError じゃないの？
    // throwした瞬間(クラス)は、超厳密には「システムエラー」なのか「正常なレアケース」なのか判断つかない。
    // そのメソッドを呼び出した側の全体業務を司っているクラスがcatchして初めてどっちなのか判断できる。
    // IllegalArgument というイベントそのものが固定的にエラーと定義づけられるわけじゃない。(ある意味相対的)
    // 一方で、OutOfMemoryError と NoSuchMethodError は、throwした瞬間にダメと定義付けできる。
    // (自然言語の)例外とエラー、実装上のExceptionとErrorの対比。
    // 
    // TypeScript, Rubyの例。
    // 異常系 (from つじさん) という言葉は、確かにダメ感が入っている。
    /*
                           +-----------------+
                           |                 |
                  +------------------+       |
                  |     Throwable    | <>----+
                  +------------------+
                           △ 
                           ｜
            +------------------------------+
            |                              |
   +--------------------+        +-------------------+
   |       Error        |        |    Exception      |
   +--------------------+        +-------------------+
     NoSuchMethodError                    △ 
     OutOfMemoryError                     ｜
                           +------------------------------+
                           |                              |
                 +--------------------+        +-------------------+
                 |  RuntimeException  |        |    XxxException   |
                 +--------------------+        +-------------------+
                           △                 IOException, SQLException
                           ｜
                   NullPointerException            
                   IllegalStateException
     */
    // ===================================================================================
    //                                                                         NullPointer
    //                                                                         ===========
    /**
     * What variable (is null) causes the NullPointerException? And what row number? (you can execute and watch logs) <br>
     * (NullPointerが発生する変数(nullだった変数)と、発生する行番号は？(実行してログを見ても良い))
     */
    public void test_exception_nullpointer_basic() {
        try {
            String sea = "mystic";
            String land = sea.equals("mystic") ? null : "oneman"; // landにnullが入る
            String lowerCase = land.toLowerCase(); // nullに対してメソッドを呼び出すのでここでエラーが発生する
            log(lowerCase);
        } catch (NullPointerException e) {
            log(e);
        }
        // your answer? => land, 147行目
        // log
        // at org.docksidestage.javatry.basic.Step07ExceptionTest.test_exception_nullpointer_basic(Step07ExceptionTest.java:147)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_exception_nullpointer_headache() {
        try {
            String sea = "mystic";
            String land = !!!sea.equals("mystic") ? null : "oneman"; // landはoneman
            String piari = !!!sea.equals("mystic") ? "plaza" : null; // piariはnull
            int sum = land.length() + piari.length(); // piari.length()で発生
            log(sum);
        } catch (NullPointerException e) {
            log(e);
        }
        // your answer? =>  piari.length(), 163行目
        // 	at org.docksidestage.javatry.basic.Step07ExceptionTest.test_exception_nullpointer_headache(Step07ExceptionTest.java:163)
    }

    /**
     * Refactor to immediately understand what variable (is null) causes the NullPointerException by row number in stack trace. <br>
     * (どの変数がNullPointerを引き起こしたのか(nullだったのか)、スタックトレースの行番号だけでわかるようにリファクタリングしましょう)
     */
    public void test_exception_nullpointer_refactorCode() {
        try {
            String sea = "mystic";
            String land = !!!sea.equals("mystic") ? null : "oneman";
            String piari = !!!sea.equals("mystic") ? "plaza" : null;
            int sum = land.length();
            sum += piari.length(); // なんかこれでわかるようになるけど、微妙に感じてしまう...なぜだ？気のせい？
            // #1on1: その微妙な感情、合ってると思います (2026/02/18)
            // コードを綺麗にするための切り出しじゃなくて、Javaの例外情報の少なさをカバーする切り出し。
            // TODO jflute 次回1on1 (2026/02/18)
            log(sum);
        } catch (NullPointerException e) {
            log(e);
        }
    }

    // ===================================================================================
    //                                                                   Checked Exception
    //                                                                   =================
    /**
     * Show canonical path of new java.io.File(".") by log(), and if I/O error, show message and stack-trace instead <br>
     * (new java.io.File(".") の canonical path を取得してログに表示、I/Oエラーの時はメッセージとスタックトレースを代わりに表示)
     */
    public void test_exception_checkedException_basic() {
        File file = new File(".");
        try {
            String canonicalPath = file.getCanonicalPath();
            log(canonicalPath);
            throw new IOException();
        } catch (IOException e) {
            // TODO jflute 良いエラーメッセージってなんでしょう？ by akinari.tsuji (2026/02/17)
            // 何が起こっているか、原因は何か、どう直せばいいか、がわかれば嬉しい気がしますが、↓は過剰な気もしてしまい...
            // また、そもそもどう直せばいいかわかるなら、その時点で治せる気もする...？
            //
            // また、そもそも例外って、なんなんでしょう。
            // 想定内の例外は都度つどのtry-catchという処理フローの中で、別途管理されている（システム本来の処理とは別の経路で管理するために例外クラスを投げる？）
            // 想定外の例外はどこかで最終防御壁的なcatchがある、という感じでしょうか？
            // プロセスを止めないために、エラーを管理してあげるためのもので、想定されるエラーと想定外のエラーで止め方が異なる？
            log("canonical pathの取得時にエラーが発生しました:", e);
        }
    }

    // ===================================================================================
    //                                                                               Cause
    //                                                                               =====
    /**
     * What string is sea variable in the catch block?
     * And What is exception class name displayed at the last "Caused By:" of stack trace? <br>
     * (catchブロックの変数 sea, land の中身は？また、スタックトレースの最後の "Caused By:" で表示されている例外クラス名は？)
     */
    public void test_exception_cause_basic() {
        String sea = "mystic";
        String land = "oneman";
        try {
            throwCauseFirstLevel();
            // ↑でIllegalStateExceptionが発生、
            fail("always exception but none");
        } catch (IllegalStateException e) {
            Throwable cause = e.getCause();
            sea = cause.getMessage();
            land = cause.getClass().getSimpleName();
            log(sea); // your answer? => "Failed to call the third help method: symbol= -1"
            log(land); // your answer? => IllegalStateException
            log(e); // your answer? => java.lang.IllegalStateException: Failed to call the second help method: symbol=1

            // eにはその例外の例外クラスやエラーメッセージが入っていて、そのまま出力すると"[例外クラス]: [エラーメッセージ]"の形式で出してくれる
            // getCause()を呼び出すと、一つ手前の例外を取り出す。
            // 今回の例外発生順が、手前から
            //  a. throw new IllegalStateException("Failed to call the second help method: symbol=" + symbol, e);
            //  b. throw new IllegalArgumentException("Failed to call the third help method: symbol=" + symbol, e);
            //  c. NumberFormatException
            // となっているので、eにはaが入っていて、e.getCause()にはbが入っている

        }
    }

    private void throwCauseFirstLevel() {
        int symbol = Integer.MAX_VALUE - 0x7ffffffe; // 1
        try {
            throwCauseSecondLevel(symbol);
            // ↑でIllegalArgumentExceptionが発生、"Failed to call the second help method: symbol=" + symbol
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Failed to call the second help method: symbol=" + symbol, e);
        }
    }

    private void throwCauseSecondLevel(int symbol) {
        try {
            --symbol;
            symbol--;
            if (symbol < 0) {
                throwCauseThirdLevel(symbol); // -1
                // ↑でNumberFormatExceptionが発生、"Failed to call the third help method: symbol=" + symbol
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Failed to call the third help method: symbol=" + symbol, e);
        }
    }

    private void throwCauseThirdLevel(int symbol) {
        if (symbol < 0) {
            Integer.valueOf("piari"); // -> NumberFormatExceptionが発生する
        }
    }

    // ===================================================================================
    //                                                                         Translation
    //                                                                         ===========
    /**
     * Execute this test and read exception message and write situation and cause on comment for non-programmer. <br>
     * テストを実行して例外メッセージを読んで、プログラマーでない人にもわかるように状況と原因をコメント上に書きましょう。
     */
    public void test_exception_translation_debugChallenge() {
        try {
            new SupercarClient().buySupercar();
            fail("always exception but none");
        } catch (RuntimeException e) {
            log("*No hint here for training.", e);
            // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
            // What happens? Write situation and cause here. (何が起きた？状況と原因をここに書いてみましょう)
            // - - - - - - - - - -
            // まずは処理を追う
            // 1. SupercarDealerを作成
            //      SupercarDealerのインスタンスを作るだけなので、例外は発生しなさそう
            // 2. clientRequirementを作成 -> "steering wheel is like sea"
            //      これも要求を文字列で返すだけなので例外は発生しない
            // 3. Supercarをdealerに発注
            //      - dealerに発注した時、要求が想定外の場合は例外が発生するが、今回はこれではなさそう
            //      - SupercarManufacturerを作成し、製造を依頼（piari）
            //      ここら辺かなー
            // 4. 注文済みリストに車を追加
            //      ここはただcollectionに追加しているだけだから、問題ない気がする
            //
            // TODO akinari.tsuji これはコード追うのが面倒になってきたので、シーケンス図に起こす方が楽そう (2026/02/18)
            // あと、ちょっとずるいけど例外メッセージから発生箇所がSpecialScrewManufactuereであることはわかった
            // _/_/_/_/_/_/_/_/_/_/
        }
    }

    /**
     * Improve exception handling in supercar's classes to understand the situation
     * by only exception information as far as possible. <br>
     * できるだけ例外情報だけでその状況が理解できるように、Supercarのクラスたちの例外ハンドリングを改善しましょう。
     */
    public void test_exception_translation_improveChallenge() {
        try {
            new SupercarClient().buySupercar(); // you can fix the classes
            fail("always exception but none");
        } catch (RuntimeException e) {
            log("*No hint here for training.", e);
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Fix terrible (YABAI in Japanese) exception handling. (you can modify exception class) <br>
     * (やばい例外ハンドリングがあるので修正しましょう (例外クラスを修正してOK))
     */
    public void test_exception_writing_constructorChallenge() {
        try {
            helpSurprisedYabaiCatch();
        } catch (St7ConstructorChallengeException e) {
            log("Thrown by help method", e); // should show also "Caused-by" information
        }
    }

    private void helpSurprisedYabaiCatch() {
        try {
            helpThrowIllegalState();
        } catch (IllegalStateException e) {
            throw new St7ConstructorChallengeException("Failed to do something.");
        }
    }

    private void helpThrowIllegalState() {
        if (true) { // simulate something illegal
            String importantValue = "dummy"; // important to debug
            throw new IllegalStateException("something illegal: importantValue=" + importantValue);
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * What is the concept difference between Exception and Error? Write it on comment. <br>
     * (ExceptionとErrorのコンセプトの違いはなんでしょうか？コメント上に書きましょう)
     */
    public void test_exception_zone_differenceExceptionError() {
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // Write here. (ここに書いてみましょう)
        // - - - - - - - - - -
        //
        //
        //
        // _/_/_/_/_/_/_/_/_/_/
    }
}
