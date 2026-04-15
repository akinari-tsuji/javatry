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
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.docksidestage.javatry.basic.st8.St8DbFacade;
import org.docksidestage.javatry.basic.st8.St8Member;
import org.docksidestage.javatry.basic.st8.St8Withdrawal;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of Java8 functions. <br>
 * Operate as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りに実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author akinari.tsuji
 */
public class Step08Java8FunctionTest extends PlainTestCase {

    // ===================================================================================
    //                                                                              Lambda
    //                                                                              ======
    // -----------------------------------------------------
    //                                              Callback
    //                                              --------
    /**
     * Are all the strings by log() methods in callback processes same? (yes or no) <br>
     * (コールバック処理の中で出力しているログの文字列はすべて同じでしょうか？ (yes or no))
     */
    public void test_java8_lambda_callback_basic() {
        String title = "over";

        // メモ：なんとなくしか文法わかんないけど、きっとコールバックをクラスでやったり匿名クラスでやったり、ラムダでやったり... akinari.tsuji  (2026/03/18)
        // acceptっていうのが文法なのかな、Consumerインターフェースをimplementsしてacceptが動く的な
        log("...Executing named class callback(!?)");
        helpCallbackConsumer(new St8BasicConsumer(title));

        log("...Executing anonymous class callback");
        // #1on1: A → B
        //        A ← B
        // コールバックとは？
        helpCallbackConsumer(new Consumer<String>() {
            public void accept(String stage) {
                log(stage + ": " + title);
            }
        });

        log("...Executing lambda block style callback");
        helpCallbackConsumer(stage -> {
            log(stage + ": " + title);
        });

        log("...Executing lambda expression style callback");
        helpCallbackConsumer(stage -> log(stage + ": " + title));

        // your answer? => yes

        // cannot reassign because it is used at callback process
//        title = "wave";
        // java: 内部クラスから参照されるローカル変数は、finalまたは事実上のfinalである必要があります 61
        // java: ラムダ式から参照されるローカル変数は、finalまたは事実上のfinalである必要があります 67
        // java: ラムダ式から参照されるローカル変数は、finalまたは事実上のfinalである必要があります71
        // done jflute なぜfinalである必要があるのでしょう...？ by akinari.tsuji (2026/03/18)
        // St8BasicConsumerはエラーが出てないので、なんか納得はするのですが、うまく言葉にできないです...
        // #1on1: step2のforEach()で説明、技術的な制約というより、カオスを防ぐためのもの (2026/04/01)
        // コールバックはあくまで別クラスの別メソッド。
        // そのメソッドが、元のメソッドのローカル変数のライフサイクルに依存するとややこしい。
        // finalな変数であればただの参照だけなのでコピーもできるからややこしさが発生しにくい。
    }

    /**
     * What is order of strings by log(). (write answer as comma-separated) <br>
     * (ログに出力される文字列の順番は？ (カンマ区切りで書き出しましょう))
     */
    public void test_java8_lambda_callback_order() {
        log("harbor");
        helpCallbackConsumer(stage -> {
            log(stage);
        });
        log("lost river");
        // your answer? =>
        /**
         * harbor
         * broadway
         * dockside
         * harbor
         * lost river
         */
        // ラスト二つ目はhangerでした（見間違えてた笑
    }

    private class St8BasicConsumer implements Consumer<String> {

        private final String title;

        public St8BasicConsumer(String title) {
            this.title = title;
        }

        @Override
        public void accept(String stage) {
            log(stage + ": " + title);
        }
    }

    private void helpCallbackConsumer(Consumer<String> oneArgLambda) {
        log("broadway");
        oneArgLambda.accept("dockside");
        log("hangar");
    }

    /**
     * What string is sea variable at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_lambda_callback_valueRoad() {
        String label = "number";
        String sea = helpCallbackFunction(number -> {
            return label + ": " + number;
        });
        log(sea); // your answer? => "number: 7"
        // FunctionはRepresents a function that accepts one argument and produces a result.
        // Consumerは値を返さないけど、Functionは値を返す
    }

    private String helpCallbackFunction(Function<Integer, String> oneArgLambda) {
        return oneArgLambda.apply(7);
    }

    // -----------------------------------------------------
    //                                         Convert Style
    //                                         -------------
    /**
     * Change callback style like this:
     * <pre>
     * o sea: to lambda block style
     * o land: to lambda expression style
     * o piari: to lambda block style
     * </pre>
     * (このようにコールバックスタイルを変えてみましょう:)
     * <pre>
     * o sea: BlockのLambda式に
     * o land: ExpressionのLambda式に a -> 処理
     * o piari: BlockのLambda式に a -> { 処理 }
     * </pre>
     */
    public void test_java8_lambda_convertStyle_basic() {
        helpCallbackSupplier(
                // new Supplier<String>() { // sea
//            public String get() {
//                return "broadway";
//            }
                () -> { return "boradway"; }
            );

//        helpCallbackSupplier(() -> { // land
//            return "dockside";
//        });
        helpCallbackSupplier(() -> "dockside");

        helpCallbackSupplier(() -> { return "hangar"; }); // piari

        // #1on1: 1statementのblock styleは、中身が長かったり()が複雑だったりのときに見栄え調整で使える (2026/04/01)
    }

    // Supplierは毎回同じ値を返す時に使う？
    // #1on1: そんなことはなく、やろうと思えば呼び出すたびに違う値を戻すことはできる。
    private void helpCallbackSupplier(Supplier<String> oneArgLambda) {
        String supplied = oneArgLambda.get();
        log(supplied);
    }

    // ===================================================================================
    //                                                                            Optional
    //                                                                            ========
    /**
     * Are the strings by two log() methods same? (yes or no) <br>
     * (二つのlog()によって出力される文字列は同じでしょうか？ (yes or no))
     */
    public void test_java8_optional_concept() {
        // こっちはただのSt8Member
        St8Member oldmember = new St8DbFacade().oldselectMember(1);
        if (oldmember != null) {
            log(oldmember.getMemberId(), oldmember.getMemberName());
        }
        // こっちはOptional: 値があるかもしれないし、無いかもしれない
        // NullPointerExceptionを防ぐためのもの
        // 最近ほんとに趣味レベルでRustをお勉強中で、Optional<T>が合った記憶（というレベルの学習頻度です笑）
        Optional<St8Member> optMember = new St8DbFacade().selectMember(1);
        if (optMember.isPresent()) { // != nullの比較ではない
            St8Member member = optMember.get();
            log(member.getMemberId(), member.getMemberName());
        }
        // your answer? => "1 broadway" 結局は同じになる

        // #1on1: "ないかもしれない" という概念をオブジェクトにした (2026/04/01)
        // (概念もオブジェクトになりうる)
        // Optionalで戻すことで、ないかもしれないことを無視される可能性が低い。
        // (問答無用get()やる人はほぼ確信犯。まあよくわからずget()する人もいるけど)
        // まじめな人であれば凡ミスがなくなる、ってニュアンス。
        
        // #1on1: Optionalが入ったのが2015年くらい (2026/04/01)
        // 20年間Optionalがなかった。安全なのに...なぜ？
        // Optionalという発明が2015年だったから？ (まあそんなことはない)
        // Optionalという技術を導入するのが大変だったから？ (まあそんなことはない)
        // (JavaのOptionalは文法というよりかはただのクラス、しかも実装シンプル)
        // 「見た目のスッキリさ + 長年のnullチェックの意識」
        // Lambda式とセットで導入されて、ifPresent(), map() があってこそ。
    }

    /**
     * Are the strings by two log() methods same? (yes or no) <br>
     * (二つのlog()によって出力される文字列は同じでしょうか？ (yes or no))
     */
    public void test_java8_optional_ifPresent() {
        Optional<St8Member> optMember = new St8DbFacade().selectMember(1);
        if (optMember.isPresent()) {
            St8Member member = optMember.get();
            log(member.getMemberId(), member.getMemberName());
        }
        // value != nullならconsumer.accept(value)
        optMember.ifPresent(member -> {
            log(member.getMemberId(), member.getMemberName());
        });
        // your answer? => "1, broadway"
    }

    /**
     * What string is sea, land, piari, bonvo, dstore, amba variables at the method end? <br>
     * (メソッド終了時の変数 sea, land, piari, bonvo, dstore, amba の中身は？)
     */
    public void test_java8_optional_map_flatMap() {
        St8DbFacade facade = new St8DbFacade();

        // TODO jflute 次回1on1, map/flatMap() (2026/04/01)
        // traditional style
        St8Member oldmemberFirst = facade.oldselectMember(1);
        String sea;
        // ID: 1の場合、new St8Member(memberId, "broadway", new St8Withdrawal(11, "music"));
        if (oldmemberFirst != null) {
            St8Withdrawal withdrawal = oldmemberFirst.oldgetWithdrawal();
            if (withdrawal != null) {
                sea = withdrawal.oldgetPrimaryReason(); // 第二引数の"music"
                if (sea == null) {
                    sea = "*no reason1: the PrimaryReason was null";
                }
            } else {
                sea = "*no reason2: the Withdrawal was null";
            }
        } else {
            sea = "*no reason3: the selected Member was null";
        }

        Optional<St8Member> optMemberFirst = facade.selectMember(1);

        // map style
        String land = optMemberFirst.map(mb -> mb.oldgetWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason())
                .orElse("*no reason: someone was not present"); // orElseはnullならother, not nullならvalue

        // flatMap style
        // flatMapは引数がOptionalに包まれてれば、それ以上は包まない？
        // This method is similar to map(Function),
        // but the provided mapper is onioe whose result is already an Optional, and if invoked,
        // flatMap does not wrap it with an additional Optional.
        String piari = optMemberFirst.flatMap(mb -> mb.getWithdrawal())
                .flatMap(wdl -> wdl.getPrimaryReason())
                .orElse("*no reason: someone was not present");

        // flatMap and map style
        String bonvo = optMemberFirst.flatMap(mb -> mb.getWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason())
                .orElse("*no reason: someone was not present");

        // 2: return new St8Member(memberId, "dockside", new St8Withdrawal(12, null));
        // return Optional.ofNullable(withdrawal);
        String dstore = facade.selectMember(2)
                .flatMap(mb -> mb.getWithdrawal())
                .map(wdl -> wdl.oldgetPrimaryReason())
                .orElse("*no reason: someone was not present");

        String amba = facade.selectMember(3)
                .flatMap(mb -> mb.getWithdrawal())
                .flatMap(wdl -> wdl.getPrimaryReason())
                .orElse("*no reason: someone was not present");

        int defaultWithdrawalId = -1;
        // return new St8Member(memberId, "dockside", new St8Withdrawal(12, null));
        Integer miraco = facade.selectMember(2)
                .flatMap(mb -> mb.getWithdrawal())
                .map(wdl -> wdl.getWithdrawalId()) // ID here
                .orElse(defaultWithdrawalId);

        // TODO akinari.tsuji なんとなく答えわかって当たってはいたけど、問題の意図とflatMap, mapの使い分けは理解できていないのでそこを忘れずにやる (2026/03/31)
        log(sea); // your answer? => music
        log(land); // your answer? => music
        log(piari); // your answer? => music
        log(bonvo); // your answer? => music
        log(dstore); // your answer? => *no reason: someone was not present
        log(amba); // your answer? => *no reason: someone was not present
        log(miraco); // your answer? => 12
    }

    /**
     * What string is sea variables at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_optional_orElseThrow() {
//        return new St8Member(memberId, "dockside", new St8Withdrawal(12, null));
        Optional<St8Member> optMember = new St8DbFacade().selectMember(2);
        St8Member member = optMember.orElseThrow(() -> new IllegalStateException("over"));
        String sea = "the";
        try {
            // oldgetPrimaryReason()がnullを返して例外が発生する
            String reason = member.getWithdrawal().map(wdl -> wdl.oldgetPrimaryReason()).orElseThrow(() -> {
                return new IllegalStateException("wave");
            });
            sea = reason;
        } catch (IllegalStateException e) {
            sea = e.getMessage();
        }
        log(sea); // your answer? => wave
    }

    // ===================================================================================
    //                                                                          Stream API
    //                                                                          ==========
    /**
     * What string is sea, land variables at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_java8_stream_concept() {
        List<St8Member> memberList = new St8DbFacade().selectMemberListAll();
        List<String> oldfilteredNameList = new ArrayList<>();
        for (St8Member member : memberList) {
            if (member.getWithdrawal().isPresent()) {
                oldfilteredNameList.add(member.getMemberName());
            }
        }
        String sea = oldfilteredNameList.toString();
        log(sea); // your answer? =>  broadway, dockside
        // id=3以外はwithdrawalがnot nullなので

        List<String> filteredNameList = memberList.stream() //
                .filter(mb -> mb.getWithdrawal().isPresent()) //
                .map(mb -> mb.getMemberName()) //
                .collect(Collectors.toList());
        String land = filteredNameList.toString();
        log(land); // your answer? => broadway, dockside
        // 同じことをStreamを使ってやってる？
        // Streamを使うと宣言的にかけるのが良い...？
    }

    /**
     * What string is sea, variables at the method end? <br>
     * (メソッド終了時の変数 sea の中身は？)
     */
    public void test_java8_stream_map_flatMap() {
        List<St8Member> memberList = new St8DbFacade().selectMemberListAll();
        int sea = memberList.stream()
                .filter(mb -> mb.getWithdrawal().isPresent())
                .flatMap(mb -> mb.getPurchaseList().stream())
                .filter(pur -> pur.getPurchaseId() > 100)
                .mapToInt(pur -> pur.getPurchasePrice())
                .distinct()
                .sum();
        log(sea); // your answer? => 600
        // id = 1, 2に絞られる。1のみitemを持ってる。
        // 1のitemは全部id > 100. [100, 200, 200, 300]
        // distinctで[100, 200, 300] => sum: 600
    }

    // *Stream API will return at Step12 again, it's worth the wait!
}
