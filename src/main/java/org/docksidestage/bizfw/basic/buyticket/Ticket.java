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
package org.docksidestage.bizfw.basic.buyticket;

import java.time.Clock;
import java.time.LocalTime;

// done jflute 次回1on1で、javadoc周りのお話 (2025/09/24)
/**
 * 遊園地のチケットを表すクラス。
 * データとしては、チケット種別（金額・回数）、今までの利用回数、現在入園中かを表す変数を持つ。
 * 操作としては、入園を行うメソッド、メンバ変数のゲッターを持つ。
 * 使用方法としては、TicketBoothクラスの購入用メソッドを通じたインスタンスの作成を想定している。
 * そのため、他クラスから直接Ticketインスタンスを作成することは非推奨。
 * @author jflute
 * @author akinari.tsuji
 */
public class Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // done tsuji コメント形式がjavadoc形式になってないです。最初スラアスタアスタというように * が二つ必要です by jflute (2025/08/27)
    // done jflute ありがとうございます！まだjavadocを理解しきれておらずすみません！ 修正しますakinari.tsuji  (2025/08/29)
    /** チケットの種類 */
    private final TicketType ticketType;

    /** 今までのこのチケットを利用した回数 */
    private int totalEntrancesCount;

    /** 現在、入園中かを表す。true: 入園中, false： 非入園中 */
    private boolean alreadyIn;

    /** 入園時間を測定する時計 */
    private Clock clock;

    // done tsuji [いいね] インスタンス変数とコンストラクターでのset順序が同じなのでわかりやすい by jflute (2025/08/15)
    // ありがとうございます！ by akinari.tsuji
//    private final int displayPrice; // written on ticket, park guest can watch this
//    private final int entranceLimit;
    // done tsuji Countという概念自体は一つしか無いので、複数形にしない方がいいかなと by jflute (2025/08/15)
    // もし、EntrancesだったらCountという言葉を省略した入園回数という概念にはなると思う。
    // done [修正] jflute 1on1でご指摘いただいたようにtotalEntrancesCountに修正しました akinari.tsuji  (2025/08/22)
    // 複数回入園することを表現しながら、値としては一つであることを表現
    // done tsuji [読み物課題] プログラマーに求められるデザイン脳 by jflute (2025/08/15)
    // done jflute [読み物課題メモ] akinari.tsuji  (2025/08/22)
    // 1. 命名デザイン : 周りの名前・全体の中での位置付けからつける
    // 2. 構図デザイン：人が見て・手を加えるための構造デザイン
    // 3. コメントデザイン：読む人が嬉しいコメントを残す
    // 4. DBデザイン：誤魔化しが効かない
    // done jflute [質問] 後から変更がしやすいDBというのは開発されなかったのでしょうか？　akinari.tsuji  (2025/08/22)
    // 変更しにくいというのはDB全体の性質なのか、RDBに特に顕著な性質なのかが気になりました
    // 変更しやすい種類のDBを利用すればテーブル設計に多少問題があってもいいのになぁと思った次第です
    // 5. アーキテクチャデザイン
    // https://jflute.hatenadiary.jp/entry/20170623/desigraming
    // done tsuji [回答] 半分はRDBの特性で半分はDBなら避けられないみたいなところでしょうか... by jflute (2025/08/27)
    // RDBは確かに、堅いことをウリにしているというのもあり比較的他のDBに比べて変更しにくい面はあるかと思います。
    // (ただそこはトレードオフではあるので、変更しやすい仕組みだと堅いことのウリを失うので、ケースバイケースで)
    // 一方で、RDBだろうが他のDBだろうが、アプリのコードがそのDBのデータ形式に依存するので、
    // それが変わればアプリも変わらないといけないです。一般手に参照されている側というのは変更がしづらいもので。
    //
    // そこで、堅いことをウリにしているRDBのメリットを得つつ、アプリ側をDB変更に追従しやすくしたい、
    // というコンセプトがDBFluteにつながるという感じですね(^^。
    // done jflute ありがとうございます！akinari.tsuji  (2025/08/29)
    // RDBは固いことがウリなのですね、RDBの内部構造気になります
    // 依存される側は変更しにくいものなのですね...
    // done [追加質問] 「アプリ側をDB変更に追従しやすくしたい」というのは以下のような認識であっていますでしょうか？ jflute akinari.tsuji  (2025/08/29)
    // 1. DBを変更した場合に、DBFluteを利用すればDBの情報を元に自動でDB操作を行うためのコードが生成される
    // 2. アプリ側ではそれを元にコードを作成・変更すれば良い
    // 3. そのため、DBを変更したときに、アプリ側での修正が少なく済む
    // #1on1: "アプリ側での修正が少なく済む"
    //   → "アプリ側での修正が必要箇所がコンパイルエラーで自動的に検出される"
    // DB変更: e.g. MEMBER_NAME => LAST_NAME, FIRST_NAME
    // A: 実行するまでわからない(かもしれない)
    // String sql = "select ... from MEMBER where MEMBER_NAME like 'S%'";
    //
    // B: 変更されたら自動生成し直す => setMemberName...が消えて赤くなる
    // memberBhv.selectEntity(cb -> {
    //     cb.query().setMemberName_LikeSearch("S", op -> op.likePrefix());
    // });
    //
    // 基礎的な一つの手段↑↑↑
    
    // #1on1: ActiveRecordのModel中心と、スキーマ中心の違geいの話
    // #1on1: マイクロサービスをするときにフレームワーク/言語によって何か変わるか？の話
    // #1on1: DDDのアーキテクチャの恩恵、MVCも何が良いのか？何が悪いのか？を知るの難しい話
    // #1on1: javatryでシンプルな V だけアーキテクチャと、C だけアーキテクチャのエクササイズあっても良いかも話
    
    // done tsuji [思考課題] MVCの V だけのシステムだったら何がつらい？ (想像でOK) by jflute (2025/09/10)
    // done shiny ↑しゃいにーさんも同じように考えてみてください by jflute (2025/09/10)

    // done jflute [回答] akinari.tsuji  (2025/09/29)
    // JSPがViewオンリーらしいので、geminiに文法を教えてもらいながら
    // 以下の要素もviewに記述される
    // Model: DBアクセス、ビジネスロジック　<% ... %>
    // Controller: ない
    // これらをファイル分けずにviewに全て書くので以下の問題起こりそう
    // 1. 再利用性低い：　ファイルに分かれてないので、毎回コピペしてきそう
    // 2. 保守性低い：　1の結果、修正箇所がわかりにくくなる。尚且つ、一つのファイルの中に責任が複数入っているので、修正箇所探すのが大変
    // 分けた方がいいことはなんとなく伝わるものの、触ってみた方がいい気がしてきた...理屈だけわかって実感が無い
    // #1on1: Good, JSPの例でVのみベタ書きシミュレーションをしてみた。(2025/10/22)
    // Mの詳細、Vの詳細を、色々な方法論で探ってる段階なのかもしれない。
    // CQRSのちょこっと紹介

    // done tsuji git conflictのマージ作業で重複しちゃったんじゃないかと。変数が二重になってます。 by jflute (2025/08/27)
    // done ありがとうございます！消したつもりだったのですが...漏れてました akinari.tsuji  (2025/08/29)
    // private int totalEntrancesCount;
    // private boolean alreadyIn;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    /**
     * Ticketクラスのコンストラクタ。
     * TicketBoothクラスからの呼び出しを想定。
     * @param type 作成するチケットの種類。
     */
    public Ticket(TicketType type, Clock clock) {
        ticketType = type;
        totalEntrancesCount = 0;
        alreadyIn = false;
        this.clock = clock;
    }

    // done tsuji [いいね] 複数のコンストラクターに対して、コメントで役割を書いているのGood by jflute (2025/08/15)
    // #1on1: staticのFactoryメソッドのお話もちょこっと
    // done tsuji 一方で、Booth側で実際にOneDayでもこっちがnewされていない問題 by jflute (2025/08/15)
    // done jflute [修正しました] 使っていないコンストラクタを削除しました　akinari.tsuji  (2025/08/22)
    // one-day ticket
//    public Ticket(int displayPrice) {
//        this.displayPrice = displayPrice;
//        this.entranceLimit = 1;
//        this.totalEntrancesCount = 0;
//        this.alreadyIn = false;
//    }

    // two or more day ticket
//     public Ticket(TicketType type) {
        // done tsuji this.を使ってたり使ってなかったりが不統一なのでどうにかしましょう by jflute (2025/08/27)
        // (this.が必要な場面だけで使うってのも一つの選択肢ですが、現状はそれでもなさそうなので)
        // done jflute こちら修正いたしました！ akinari.tsuji  (2025/08/29)
//        ticketType = type;
//        this.totalEntrancesCount = 0;
//        this.alreadyIn = false;
//    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    // TODO done tsuji ナイトパスの利用, 具体的過ぎると変更に追従できないので、もう少しぼかして by jflute (2025/10/22)
    // TODO done tsuji せっかくなので、夜チケットの例外もthrowsで書いておきましょう by jflute (2025/10/22)
    /**
     * チケットを使用するメソッド。
     * 入園回数を増加させ、入園中のステータスに切り替える。
     * ナイトパスの利用は18時以降。
     * @throws IllegalStateException チケットの入園回数を超えて利用しようとした場合
     */
    public void doInPark() {
        // ナイトパスを夜以外に使おうとした場合
//        if (ticketType == TicketType.NIGHT) {
//            // done tsuji 修行++: NIGHTのニュアンスが違う別のNIGHTのチケット(e.g. 17時から)が出てきた時でも対応できるように by jflute (2025/08/27)
              // done jflute [やりました！] 利用可否を判断するためのクラス（IUsagePolicy）を作成してENUMで種類ごとに適切なポリシーを持つ形で実装しました akinari.tsuji  (2025/09/10)
              // ややInterfaceを使ってみたい＆条件分岐を減らしてみたいという欲求メインで考えた実装なのですがやり方として妥当なのかどうか、教えていただけますと嬉しいです！
              // 自分としては、①enumに項目（利用可能時間）を持たせる、②利用可否を判断するクラスを作成する、の２パターンを考えました
              // そして、利用可否の判断が複雑になる（3/1は使える、9:00~13:00と18:00~に入園可能）可能性がある？のかなと考え後者を採用しました
              // 前者だとその分だけ項目をENUMに追加していく必要があると考えたため
              // #1on1: 個人的に、1と2のハイブリッドで柔軟性高く作られている印象です。GOod by jflute (2025/10/08)

//            // [作業メモ] akinari.tsuji  (2025/09/03)
//            // 以下のどちらで実装を行うか？
//            // 1. enumに利用可能時間を持たせる：実装が楽だが、拡張性が低い（条件が増えていった時にenumがごちゃごちゃになる）
//            // 2. チケットの利用ポリシーのinterface作り、個別の条件についてクラスを実装。enumにはポリシーを持たせる：実装大変だが、拡張性が高く柔軟
//            // 今回は勉強のために校舎で実装する。あとここの条件分岐を減らせそうなので
//            LocalTime borderTime = LocalTime.of(18, 0);
//            LocalTime nowTime = LocalTime.now();
//            // LocalTime nowTime = LocalTime.of(17, 59); // 日中使用した場合を確認するためのコード
//            if (nowTime.isBefore(borderTime)) {
//                // done tsuji [いいね] 例外メッセージに borderTime を入れているのいいね、デバッグしやすい by jflute (2025/08/27)
//                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
//                throw new NotNightException("You can't use this passport before " + borderTime.format(formatter) + ".");
//            }
//        }

        // [メモ] チケット利用可否の判断に伴う例外処理（上のthrow部分）をどこで行うべきか？ akinari.tsuji  (2025/09/03)
        // 候補は次
        // 1. Ticketクラス
        // 2. IUserPolicyクラス（とその継承クラス）
        // 3. TicketType
        // 例外を発生させる条件は、isAvailable()で、条件ごとに発生させるべき例外の内容は異なる？
        // Ticket#doInParkで例外発生させると、内容に合わせて条件分岐させちゃいそう

        // チケットの利用ポリシーに基づく、利用可否の判定
        // done [質問] jflute シーケンス図（docs/Step05_Sequence.pu）を書いてみて思ったのですがakinari.tsuji  (2025/09/10)
        // validate()メソッドは例外を投げるのではなく、booleanを返すようにした方がいいのでしょうか？
        // そして、問題があればdoInPark()の中で例外を投げる、なければ後続の処理を行うという形
        // シーケンス図上ではIUserPolicyに問い合わせて、そのまま終了になっているのが違和感あったのですが...
        // 1. そもそも例外とはどこで発生させるべきなのでしょうか？問題があった場所で投げるべきなのでしょうか？
        // 2. そもそも例外として扱うべきなのか、それともbooleanをかえす形でも良いのでしょうか？
        // #1on1: validate()が再利用されない想定なのであれば...
        // どっちでも良いくらい、doInPark()でifしてthrowしても何も問題はない。
        // validate()が再利用される想定なのであれば...
        // 例外をthrowするところが複数箇所でみんなでやることになるので、
        // validate()の中で例外投げちゃうほうが再利用の範囲は広い。
        // ただ、投げる例外を呼び出し側によって変えないといけない可能性もあるかも!?
        // その事象が起きたときのシステムの振る舞いが変わるとかであればthrowする例外も変わるので...
        // その場合は、booleanだけ戻しておいて、呼び出し側が各自好きな例外を投げるようにするもアリ。
        // (もちろん、呼び出し側がcatchして別の例外に翻訳(変換)しても良いので、別に致命的ではないが...
        // validate()のthrowする例外が、とある呼び出し側にめちゃ依存した例外をthrowしてると再利用しづらい)
        //
        // あとは、呼び出し側が例外をthrowするとは限らず、単に分岐して違う処理をすることもあるかもしれない。
        // その場合も、booleanの方が呼び出し側はやりやすいとも言える。
        // もちろんここでもcatchすればいいという話もあるが、catchで分岐ってのはあまり良い方法ではないと言われている。
        // 例外throwって少しパフォーマンス食うので、分岐することがわかりきってるならbooleanで戻すほうがベター。
        // (見た目の問題でも)
        //
        // つまり、どれだけ再利用されるかどうか？再利用されたときにどれだけ呼び出し側の多様性があるか？
        // それ次第。
        //
        // #1on1: 例外スタイルと戻り値異常スタイルの一般的な違いの話 (質問への回答) (2025/09/24)
        // (例外に関しては、続きstep7でじっくり例外とは？をやります)
        //
        // #1on1: 実装中に将来の再利用をどれだけイメージするか？ (質問への回答) (2025/09/24)
        // 人に依る...できるだけイメージはできる方が良い。実際に実行するかどうかは別にして。
        // イメージして、再利用を想定した作りをすることもあれば、イメージしたけど、あえて何も想定しないってこともある。
        // ただ、その判断は、イメージしないとできない。イメージをした人だけが想定するかしないかを判断できる。
        // 実際には結構高度で、多く人がイメージできてるとはあまり思えないかも...
        // (jfluteとしては、少なくともイメージをする習慣を付けて欲しい)
        //
        // 再利用と役割分担のイメージ...常に考えてる。
        // TODO done tsuji 修行++: 引数で時間を受け取ると、mainコードの呼び出し側が、間違った現在時刻を渡すこともできてしまう by jflute (2025/10/22)
        // 現在時刻を取得するのは、中で隠蔽したいところ。例えば、こんな感じで、ここでどこかからか現在時刻を取る。
        //  e.g. LocalTime currentTime = getCurrentTime()
        // そして、UnitTestでは、このgetの処理を差し替えられるようにしたいところ。
        // hint1: step5だけの知識ではなかなか難しいので、step6とか7,8とかやってからアプローチしても良い。
        LocalTime currentTime = LocalTime.now(clock);
        ticketType.getUsagePolicy().validate(currentTime);
        

        // [メモ] to 自分 akinari.tsuji  (2025/09/03)
        // ここの条件分岐もなんか嫌に思えてきてしまったけど、どうまとめるかわかってないので考えるところからやる
        // おそらく、チケットの利用可否と一言に言っても[利用ポリシーに基づく利用可否, 利用回数に基づく利用可否]がありそうなので
        // どのクラスの責務にするか、から考える
        // 入園回数を超えて利用しようとした場合
        // #1on1: こっちはチケットの(変化する)状態を使ったチェック処理なので...
        // 上のUsagePolicyとはまた質が違うもの。もちろん、privateや別クラスで1行にしてもいいけど...
        // あくまでチケット主体のチェック処理と解釈されるので、上と同じによう考えなくてもいいかなと。
        if (totalEntrancesCount >= ticketType.getEntranceLimit()) {
            throw new OutOfHoursUseException("Tickets can not use at " + currentTime + ".");
        }

        // チケットの利用とそれに伴う処理
        totalEntrancesCount++;
        this.alreadyIn = true;
    }

    // done tsuji unused by jflute (2025/09/24)
//    private static class NotNightException extends RuntimeException {
//        private static final long serialVersionUID = 1L;
//
//        public NotNightException(String msg) {
//            super(msg);
//        }
//    }

    /**
     * 時間外利用の例外
     */
    public static class OutOfHoursUseException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public OutOfHoursUseException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========

    /*
     * @return チケットの金額
     */
    public int getDisplayPrice() { return ticketType.getPrice(); }

    // done jflute [質問] 今の実装だと、チケットのメンバ変数で直接金額を持たないため不具合が生じてしまいます？　akinari.tsuji  (2025/08/27)
    // 例えば、8/1に8,000円のチケットを購入した（チケットA）後、8/10に10,000へと値上がりした場合、チケットAの表示金額が上昇してしまいそう
    // その場合、ticketType側で新旧という軸でチケット種別を管理するべきなのか、そもそもenumを使うやり方が不適切で他の方法なら問題ないのかを知りたいです
    // done tsuji [回答] 良い着眼点ですね。そう、実際そういうことは業務でよくあるので... by jflute (2025/08/27)
    // マスターデータ(TicketType)を参照するではなく、「購入時金額」というニュアンスでSNAPSHOTとしてdisplayPriceを保持するってのが選択肢の一つです。
    //
    // DBFluteハンズオンのDBを例でで言うと、商品テーブルに定価というカラムがありますが、購入テーブルに購入金額というカラムもあります。
    // https://dbflute.seasar.org/ja/data/model/maihamadb-erd.png
    // どちらもほぼ同じ値になることが多いわけですが、購入時から金額変わったときでも昔の金額を表示できるように。
    // Ticketクラスの情報がPURCHASEテーブルに近いと考えると、int displayPrice とか int purchasePrice とかを持つイメージですね。
    //
    // enumを使うやり方自体は全く問題ないです。ちなみにDB付きのシステムだと、ここがマスターテーブルに置き換えられるイメージですね。
    // (マスターテーブルになっても、enum自体は作りますが)

    /**
     * @return チケットの残り利用回数
     */
    public int getRemainingEntranceCounts() { return ticketType.getEntranceLimit() - totalEntrancesCount; }

    // done [質問] そのチケットが使えるかどうかの判定するメソッドが必要な場合はTicketクラスに定義するべきでしょうか？ akinari.tsuji  (2025/08/27)
    // 今回はlogで出力するためにgetRemainingEntranceCounts()をpublicに定義しています。
    // もしも、このメソッドを外部から呼び出し、まだ利用できるかを判定している場合、Ticketクラスにまだ使えるか判定するロジックを集約するべきでしょうか。
    // done tsuji [回答] getRemainingEntranceCounts()さえあれば利用者側は簡単に判断できるのでそれで十分とも言えますし... by jflute (2025/08/27)
    // それだけで「どの項目とどの項目を引き算」というあまり外部に依存させたくないロジックを隠蔽しているので。
    // 一方で、isUsedUp() みたいなbooleanメソッドを提供してあげたらそれはそれで便利ですが、
    // 「remainingEntranceCounts が 0 であること」というロジックはこれ以上変わりようのないものと考えることもできるので、
    // そこはあってもなくてもくらいな感じですね。さっきの「どの項目とどの項目を引き算」を隠蔽していることの方が重要。

    /**
     * @return チケットの合計利用回数の上限
     */
    public int getEntranceLimit() { return ticketType.getEntranceLimit(); }

    // TODO done tsuji javadoc, getterだと、説明は省略しちゃってもOK by jflute (2025/10/22)
    // TODO jflute getterなので返り値だけjavadocを記載するように修正しました  by akinari.tsuji (2025/11/15)
    /**
     * @return チケット種別
     */
    public TicketType getTicketType() { return ticketType; }

    /**
     * 現在、入園中かを返す関数。
     * 入園中の場合、true。
     * @return 入園中か？
     */
    public boolean isAlreadyIn() { return alreadyIn; }
}
