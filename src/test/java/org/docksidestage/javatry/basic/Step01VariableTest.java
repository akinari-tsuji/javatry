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

// done tsuji [事務ごと] いいね的なtodoとかも、読んだら done を付けてもらえると嬉しいです by jflute (2025/07/15)
// // レビューのやり取り
// https://dbflute.seasar.org/ja/tutorial/handson/review/jflutereview.html#review
//
// あと、tsujiさんからの返事的なコメントや、質問的なコメントのときは、todoの後ろを jflute にしてもらえると、
// ぼくがtodo一覧見たときに、あっ、ぼく宛になんかコメント来てるってわかるのでそうして頂けると嬉しいです。
// 追記: ↑でもいま step2 見たら、そうしてくださってますね（＾＾。
// done jflute [reply] すみません、手元途中までやってcommit, pushしてなかったです。気をつけます! akinari.tsuji  (2025/07/15)

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
        // akinari.tsuji  文字列に他の型を結合すると文字列に変換してくれるので、null -> "null"になる(2025/07/04)
        // akinari.tsuji  ObjectにtoStringメソッドが定義されていて、全てのクラスで継承されている。(2025/07/04)
        // akinari.tsuji Rubyも同様にObejctが全てのクラスのスーパークラス。to_sメソッドが近そう。 (2025/07/04)
        // done tsuji [いいね] ObjectのtoString()の話も出突っ込んでるの素晴らしいです。 by jflute (2025/07/04)
        // "+" 記号とか、文字列型(String)に自然と変換されるような書き方をしたときは、内部的にtoString()が呼ばれます。
        // Rubyもオブジェクト指向、というか、Rubyの方ががっつりオブジェクト指向ですからね(^^。(多重継承もあるし)
        // to_sメソッド()がまさしく同じようなものだと思います。
        //
        // 余談: 昔のインターネット上の画面とか、よく「おなまえ: null」とか表示されること多かったんですよ。
        // それを見るたび、「あーあ、中の人やっちゃってるなー」って感じです(^^。
        // でも、メール文面とかは画面に比べてフレームワークが弱いので、今でも時々 null ありますね。
        // ちなみに、C#はnullを文字列にしても「空文字」になるのでそういうことは起きにくいのですが...
        // ログ出すときとかにまっさらで何も表示されないので、それはそれで開発としてはわかりにくかったりします。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_basic() {
        String sea = "mystic";
        String land = "oneman";
        sea = land;
        land = land + "'s dreams";
        log(sea); // your answer? => oneman
        // result => oneman
        // akinari.tsuji JavaのStringは値が変更不可 (2025/07/04)
        // akinari.tsuji 内部でString poolというものを持っていて、同じものがなければ作成するっぽい (2025/07/04)
        // akinari.tsuji インスタンスはString pool上の値を参照している (2025/07/04)
        // done tsuji [いいね] すごい、そこまで追求してるとは!? by jflute (2025/07/04)
        // "sea" というリテラル表現を、あちらこちらで書いたとしても、newされるStringは一回だけになるんですよね。
        // とはいえ、無駄に撒き散らす必要はないですが(^^
        // でも、リテラル表現のとき、内部的にintern()メソッドが呼ばれるってのは今回初めて知りました(^^。
        // https://qiita.com/alswnd/items/f7d559cdc4cd67564d68
        // akinari.tsuji 紹介していただいた記事めちゃくちゃ面白かったです (2025/07/07)
        // こういう、言語の裏側みたいなのってどこで情報仕入れるのでしょうか。ブログの方は資格試験で知ったと言っていましたが...
        // もし資格試験でこういう裏側を知りやすいなら、Rubyも試験あったので受けてみたいです
        //
        // #1on1: javatry研修は、間が空きます。これもトレーニングの一つ。
        // 間が空いてもインプットできる工夫、忘れない工夫、思い出せるようにする工夫。
        // 人生のプライベートでの勉強もそうだし、仕事ので久しぶりにさわる(昔の自分の)コード。
        // 将来スキルが高くなってプロジェクトの掛け持ちとかするときに。
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_int() {
        int sea = 94;
        int land = 415;
        sea = land;
        land++;
        log(sea); // your answer? => 415
        // akinari.tsuji Javaにはプリミティブ型と参照型があるらしい (2025/07/04)
        // akinari.tsuji intはプリミティブ型なので値自体を参照している (2025/07/04)
        // done jflute Integerは参照型と聞いたのですが以下の結果が(415, 416)になるのは、IntegerもStringと同じく値の変更が不可能で、b++の際に新しいオブジェクトが出来上がり参照先が変わるためでしょうか？　akinari.tsuji  (2025/07/04)
        // Integer a = 94;
        // Integer b = 415;
        // a = b;
        // b++;
        // log(a, b);
        // done tsuji [へんじ] まさしくそのとおりです。まずIntegerは不変(Immutable)なので、インスタンスの中身が変わることはないです by jflute (2025/07/04)
        // b++ は、実際には b = b + 1; と同じなのでイメージとしては...
        // Integer:415 に int:1 を足して => 一瞬 int:416 ができて...
        // それが、Integer:b に代入されるときに、int:416がラップされてInteger:416になるって感じですね。
        // done akinari.tsuji 先ほどのブログで知ったメソッドを使って確認してみます (2025/07/07)
        // Integer a = 94;
        // Integer b = 42;
        // a = b;
        // log(a, System.identityHashCode(a));
        // log(b, System.identityHashCode(b));
        // b++;
        // log(a, System.identityHashCode(a));
        // log(b, System.identityHashCode(b));
        // result ->
        // 2025-07-07 22:52:59,264 [main] DEBUG (PlainTestCase@log():711) - 42, 1712669532
        // 2025-07-07 22:52:59,264 [main] DEBUG (PlainTestCase@log():711) - 42, 1712669532
        // 2025-07-07 22:52:59,264 [main] DEBUG (PlainTestCase@log():711) - 42, 1712669532
        // 2025-07-07 22:52:59,264 [main] DEBUG (PlainTestCase@log():711) - 43, 1225373914
        // ちゃんとa=bの直後はa, bが同じ値（identityHashCodeはメモリのアドレスに基づく値、hashCodeは中身に基づく値）
        // done tsuji [いいね] ハッシュコード確認するのツワモノですね^^ by jflute (2025/07/15)
        // #1on1: System.identityHashCode(), 純粋にインスタンスのハッシュを出してくれる便利なもの
        // log(sea.hashCode());
        // log(System.identityHashCode(sea));

        // TODO tsuji 素晴らしすぎ！いい影響もらってます、ありがとう！ by shiny (2025/08/12)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_reassigned_BigDecimal() {
        BigDecimal sea = new BigDecimal(94);
        BigDecimal land = new BigDecimal(415);
        sea = land;
        sea = land.add(new BigDecimal(1));
        sea.add(new BigDecimal(1));
        log(sea); // your answer? => 416
        // akinari.tsuji  BigDecimalは変更不可な型っぽい　(2025/07/04)
        // akinari.tsuji BigDecimal.add(BigDecimal augend)はメンバ変数の値と引数の値を加算して返すだけで、メンバ変数の値は変化しない (2025/07/04)
        // done tsuji [補足] BigDecimal のクラスJavaDocを見ると、第一声でimmutableと書いてあったりします by jflute (2025/07/04)
        // add()のJavaDocも第一声が「Returns ...」というように、Immutableオブジェクトならではな表現だったりします。
        // Immutableは安全を演出するものではありますが、このケースでは戻り値受け取り忘れには注意という感じですね。
        // done akinari.tsuji  (2025/07/07)
        // どうやってJavaDocを見るんだろう、と思ったのですがBigDecimalからコードジャンプした先のコメントであっていますでしょうか？
        // Returns a {@code BigDecimal} whose ...
        // とありました。
        // 標準クラスの実装コード直接みられるの面白いです（読めないですけど笑）。
        // done tsuji [へんじ] IDE上でBigDecimalにカーソルを当てるとツールチップとして表示されるはずです by jflute (2025/07/15)
        // > 実装コード直接みられる
        // オープンソースの良いところそこですね(^^
        // #1on1: IntelliJでメソッド補完時にcontrol+JでJavaDoc表示される話

        // done jflute 1on1にて、世の中にImmutableに対するアプローチのニュアンスについて補足する予定 (2025/07/04)
        // (これはくぼ用のtodoなのでそのまま残しておいてください)
        // #1on1: 実際にadd()のソースコードリーディングしてみた
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
        // akinari.tsuji コンストラクタの定義がなく、メンバ変数のinstanceBroadwayは値が未定義 (2025/07/04)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_int() {
        int sea = instanceDockside;
        log(sea); // your answer? => 0
        // akinari.tsuji 数値型ではnullではなく0で初期化される (2025/07/04)
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_default_Integer() {
        Integer sea = instanceHangar;
        log(sea); // your answer? => 0
        // result => null
        // done jflute 参照型では参照先の値を示す変数がnullを示しているのでしょうか？ 参照型は参照先を示す変数と諸々のメソッドを含むクラス？　参照先を示すだけの変数だから値の変更ができない？　Javaの基本的な言語仕様をさらっと抑えられる良書はありますか？（Java未経験です）　by akinari.tsuji (2025/07/04)
        //
        // > 参照型では参照先の値を示す変数がnullを示しているのでしょうか？
        // done tsuji とりあえず文法的なところで言うと、参照型のインスタンス変数の初期値はnullです by jflute (2025/07/04)
        // 初期値というか、nullは厳密には値ではないので、初期値が何も入ってない「参照先が存在していない状態」(null)ということですね。
        //
        // もし、Integer instanceHangar = "sea"; であれば、instanceHangar という箱(変数)の中には、
        // "sea" のインスタンスが入っている...というよりかは、"sea" のインスタンスが(メモリ上のどこかに)置かれている場所のアドレスが入っています。
        // 参照型の変数の場合、イメージとして箱の中にアドレスが書いてある紙があるだけで、実体(インスタンス)はそのアドレスの場所に置いてあります。
        // この状態で、sea = instanceHangar; とやると、アドレスが書いてある紙をコピーしてseaに入れたみたいな感じで、
        // sea も instanceHangar も、同じインスタンスを指し示す状態になります。(参照型の変数で代入というのは、単なるアドレスのコピー)
        //
        // 今回のケースでは、まず instanceHangar という箱(変数)の中が空っぽの状態、つまり「nullの状態」になっています。
        // 箱の中にアドレスが書いてある紙すらない状態です。何も指し示していないという状態です。
        // そこで、sea という変数に instanceHangar の中身を代入 (参照先のアドレスのコピー) をしても、
        // そもそも何も指し示してないので、seaも変わらず何も指し示さないという状況です。
        //
        //
        // > 参照型は参照先を示す変数と諸々のメソッドを含むクラス？
        // 参照型という言葉自体は、変数に代入したときにアドレスによる参照形式で扱われるものを指します。
        // (実体が変数の中に入るわけではなく、紙のアドレスが入るだけ)
        //
        // そして、Javaでは「諸々のメソッドを含むクラス」がすべて参照型として扱われます。
        // 逆に、int/longなどは参照型ではなく、プリミティブ型と呼ばれ、変数に直接値が入ってるイメージです。
        // (本当にnativeなレベルで「入ってる」というニュアンスになるのかはわからないのでイメージということで)
        //
        // で、上記のセオリーがちょっと省略されて、「参照型」イコール「クラス」と表現されることが多いと。
        // 一応、配列 e.g. int[] なんかは、クラスではないですが参照型ではあります。(クラスじゃない参照型の一例)
        //
        // > 参照先を示すだけの変数だから値の変更ができない？
        // Immutableの話と、参照型の変数の話は別で...
        // Mutableのクラスであれば、参照型の変数を経由して、インスタンスが保持する値を変更することはできます。
        //  AtomicInteger atoInt = new AtomicInteger(3);
        //  atoInt.set(7);
        //  log(atoInt); => 7
        // この場合、atoInt変数は参照型の変数ですが、アドレスで指し示している先のインスタンスのメソッドを呼び出して、
        // そのインスタンスの中身を変えるような操作をしています。
        // Integerの場合は、この .set(7) のようなインスタンスの中身を変えるメソッドが用意されていないだけです。
        // Immutableなクラスか？Mutableなクラスか？の違いは外から変更できるメソッドを提供してるかしてないかだけの違いなので、
        // 変数とはあまり関係ないです。
        // (って、解答で伝わるかな？？？わかりづらかったらごめんなさい。よくわからなかったら1on1でフォローします)
        // done jflute by akinari.tsuji  (2025/07/07)
        // 解説ありがとうございます！結構しっくりきました！
        // 参照型：実際の値が置かれたアドレスを値として持ってる
        // Mutable：アドレスに保持された値を変更可能かどうか
        // というイメージであってますでしょうか？
        // done tsuji [へんじ] もうちょい厳密な表現をすると... by jflute (2025/07/15)
        //
        //  参照型変数: 実際の値(インスタンス)が置かれた場所を特定するアドレスを値として持ってる。
        //             (イメージ、住所が書かれた紙が変数という箱の中に入ってるだけ)
        //  Mutableインスタンス：その実際の値(インスタンス)の中の値が変更可能かどうか
        //                     (インスタンスも中で値を持っている)
        //
        // > Javaの基本的な言語仕様をさらっと抑えられる良書はありますか？
        // 個人的には「Java言語プログラミングレッスン 第3版(上) Java言語を始めよう」をオススメしています。
        // 上下巻で2冊構成なのですが、以前講義形式の新卒研修があったときはこちらを教材に使っていました。
        // まあ、この先javatryやっていけば自然と学んでいけるところもあるとは思いますが...
        // しっかり言語仕様を学んでからやりたいって思う姿勢は素晴らしいです(^^。
        // done akinari.tsuji  (2025/07/07)
        // ありがとうございます！ちょうどオフィスにあった気がするので今度めくってみます。
        // javatryの中で身につけられるとのことなので、一旦業務に関わる勉強優先します（紹介していただいたのに申し訳ありません）
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_variable_instance_variable_via_method() {
        instanceBroadway = "bbb";
        instanceMagiclamp = "magician";
        helpInstanceVariableViaMethod(instanceMagiclamp);
        String sea = instanceBroadway + "|" + instanceDockside + "|" + instanceHangar + "|" + instanceMagiclamp;
        log(sea); // your answer? => bighand|1|null|burn
        // result => bighand|1|null|magisian
        // akinari.tsuji メンバ変数なのでhelpInstanceVariableViaMethodで値が変更される (2025/07/04)
        // akinari.tsuji instanceMagiclampはメソッドの変数で渡されているのでこちらが優先されて更新されない (2025/07/04)
        // done tsuji [いいね] 同じ名前の変数であっても別の変数と。引数指定もあくまで参照先アドレスのコピーです。 by jflute (2025/07/04)
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
        // akinari.tsuji helpMethodArgumentImmutableMethodcall側ではlandはメソッド内のローカル変数になり変更が影響しない (2025/07/04)
        // akinari.tsuji seaについては変更不可なオブジェクトで、concatは値を返すだけでメンバ変数の値は変わらない (2025/07/04)
        // done tsuji [いいね] ↑パーフェクト。Immutableクラスの引数は、(呼び出し側から見て)絶対に変わらないことが保証されるわけですね by jflute (2025/07/04)
        // それが、可読性につながるということですね。Mutableだと「メソッド内で書き換えられてるかもしれない」ってなっちゃうので。

        // #1on1: immutableのメリットとは？話。可読性の話、人の管理の許容量の話 (状態遷移のこと考えなくて良い)
        // 個人的には、(Javaだと) 8:2 な感覚。(人と組織によって変わる)
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
        // done akinari.tsuji Builderというデザインパターンがあるらしい（勉強します） (2025/07/04)
        // done tsuji [ふぉろー] おお、いいですね。まあ、StringBuilderのBuilderとはちょっとニュアンス違いますが^^ by jflute (2025/07/04)
        // done akinari.tsuji  (2025/07/07)
        // 違ったんですね、Geminiに騙されました笑

        // TODO tsuji もう読んじゃったかもだけど、GOFのデザインパターンの本めちゃくちゃ面白いよ！ by shiny (2025/08/12)
        // 学ぶとめっちゃ使いたくなる時期がくるのだが笑、個人的には現場で実際に使うというよりかは、ソースコードリーディングをしている時に「あ、これこのパターンだな。じゃあこうなってそう」というのに役立つと思ってます！
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
        // akinari.tsuji 関数での引数は値渡しでhelpメソッド側で代入しても呼び出し元の変数には変更が生じない (2025/07/04)
        // done tsuji [いいね] Good。test側のsea変数と、help側のsea引数、「たまたま同じ名前の全く別の変数」ということで by jflute (2025/07/04)
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
        // done tsuji [いいね] わー、ややこしい笑。(ぼくも実際にやってみて) 良かった、合ってた^^ by jflute (2025/07/04)
        // done akinari.tsuji  (2025/07/07)
        // ありがとうございます！
        // 「xxxをわかっているか」というテーマで作問したかったのですが、今までの問題以外の「xxx」が思いつかず複雑にする方向で考えました

        // TODO tsuji [スバラ！] 解けたよ！Intellijがちゃんとグレーアウトしてくれてて助かった笑 by shiny (2025/08/12)
    }

    private void helperMethod(StringBuilder sea, int land, String dstore) {
        sea.append(land);
        sea = new StringBuilder(sea.toString()).append(dstore);
    }
}
