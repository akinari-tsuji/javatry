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

import java.time.Clock;
import java.time.LocalTime;
import java.util.Arrays;

import org.docksidestage.bizfw.basic.buyticket.Ticket;
import org.docksidestage.bizfw.basic.buyticket.TicketBooth;
import org.docksidestage.bizfw.basic.buyticket.TicketType;
import org.docksidestage.bizfw.basic.objanimal.Animal;
import org.docksidestage.bizfw.basic.objanimal.BarkedSound;
import org.docksidestage.bizfw.basic.objanimal.Cat;
import org.docksidestage.bizfw.basic.objanimal.Dog;
import org.docksidestage.bizfw.basic.objanimal.Turtle;
import org.docksidestage.bizfw.basic.objanimal.Zombie;
import org.docksidestage.bizfw.basic.objanimal.loud.AlarmClock;
import org.docksidestage.bizfw.basic.objanimal.loud.Loudable;
import org.docksidestage.bizfw.basic.objanimal.runner.FastRunner;
import org.docksidestage.javatry.basic.st6.dbms.St6MySql;
import org.docksidestage.javatry.basic.st6.dbms.St6PostgreSql;
import org.docksidestage.javatry.basic.st6.dbms.St6Sql;
import org.docksidestage.javatry.basic.st6.os.St6OperationSystem;
import org.docksidestage.javatry.basic.st6.os.St06MacOperatingSystem;
import org.docksidestage.unit.PlainTestCase;

/**
 * The test of object-oriented. <br>
 * Operate exercise as javadoc. If it's question style, write your answer before test execution. <br>
 * (javadocの通りにエクササイズを実施。質問形式の場合はテストを実行する前に考えて答えを書いてみましょう)
 * @author jflute
 * @author akinari.tsuji
 */
public class Step06ObjectOrientedTest extends PlainTestCase {

    // ===================================================================================
    //                                                                        About Object
    //                                                                        ============
    // -----------------------------------------------------
    //                                        Against Object
    //                                        --------------
    /**
     * Fix several mistakes (except simulation) in buying one-day passport and in-park process. <br>
     * (OneDayPassportを買って InPark する処理の中で、(simulationを除いて)間違いがいくつかあるので修正しましょう)
     */
    public void test_objectOriented_aboutObject_againstObject() {
        //
        // [ticket booth info]
        //
        // simulation: actually these variables should be more wide scope
        // TODO [メモ]　チケット購入プロセスの初期値 akinari.tsuji  (2025/08/15)
        int oneDayPrice = 7400;
        int quantity = 10; // ここもTicketBoothを利用するべき？
        Integer salesProceeds = null; // ここがnullなのはいいのでしょうか？0の方が好ましい？
        log(salesProceeds);
        // TODO tsuji 間違い探し、あと2つ by jflute (2025/10/22)
        //
        // [buy one-day passport]
        //
        // simulation: actually this money should be from customer
        // TODO [メモ] 売り切れの場合の例外 akinari.tsuji  (2025/08/15)
        int handedMoney = 10000;
        if (quantity <= 0) {
            throw new IllegalStateException("Sold out");
        }
        // TODO [メモ] お金が足りず購入できない場合でも在庫を減らしてしまっている akinari.tsuji  (2025/08/15)
        // --quantity;
        if (handedMoney < oneDayPrice) {
            throw new IllegalStateException("Short money: handedMoney=" + handedMoney);
        }
        --quantity;
        salesProceeds = handedMoney; // += にしないといけない？ここでは1人しか購入してないですが。

        //
        // [ticket info]
        //
        // simulation: actually these variables should be more wide scope
        // TODO [メモ] displayPriceは購入したチケットの金額のはず akinari.tsuji  (2025/08/15)
        // int displayPrice = quantity;
        int displayPrice = oneDayPrice;
        boolean alreadyIn = false; // TicketクラスのalreadyInを参照するべき?

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        // simulation: actually this process should be called by other trigger
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayPrice=" + quantity);
        }
        alreadyIn = true;

        //
        // [final process]
        //
        // TODO [メモ] 引数が対応ずれていた akinari.tsuji  (2025/08/15)
        saveBuyingHistory(quantity, salesProceeds, displayPrice, alreadyIn);
    }

    private void saveBuyingHistory(int quantity, Integer salesProceeds, int displayPrice, boolean alreadyIn) {
        if (alreadyIn) {
            // simulation: only logging here (normally e.g. DB insert)
            // TODO [メモ] 多分引数間違えている akinari.tsuji  (2025/08/15)
            // showYourTicketの引数にquantityあるのおかしい
            // done [質問] jflute こういう時に値オブジェクトがあるとコンパイル時に気づける、というのが前回教えていただいたメリットでしょうか？ akinari.tsuji  (2025/08/15
            // あと個人的な所感なのですが、intでprice, quantityが両方表現されていると、今回のように引数取り間違えそうです
            // [つぶやき] C言語のライブラリの内部でtypedefを用いてintを別名で定義しているのもわかりやすくするためなのかな
            // done tsuji [回答] Goodですね！前回の値オブジェクトの話とちょうどつながりますね by jflute (2025/08/27)
            // まあ、ここでは、細かい単位の値オブジェクトじゃなくて、何かしらカテゴライズされたオブジェクト化されていれば、
            // そういったことは防げるということで、それがこの次のエクササイズになるわけですね。(TicketBoothを使った例)
            showTicketBooth(quantity, salesProceeds);
            showYourTicket(displayPrice, alreadyIn);
        }
    }

    private void showTicketBooth(int quantity, Integer salesProceeds) {
        log("Ticket Booth: quantity={}, salesProceeds={}", quantity, salesProceeds);
    }

    private void showYourTicket(int displayPrice, boolean alreadyIn) {
        log("Ticket: displayPrice={}, alreadyIn={}", displayPrice, alreadyIn);
    }

    // -----------------------------------------------------
    //                                          Using Object
    //                                          ------------
    /**
     * Read (analyze) this code and compare with the previous test method, and think "what is object?". <br>
     * (このコードを読んで(分析して)、一つ前のテストメソッドと比べて、「オブジェクトとは何か？」を考えてみましょう)
     */
    public void test_objectOriented_aboutObject_usingObject() {
        //
        // [ticket booth info]
        //
        TicketBooth booth = new TicketBooth();

        // *booth has these properties:
        //int oneDayPrice = 7400;
        //int quantity = 10;
        //Integer salesProceeds = null;
        // TODO [メモ] TicketBoothクラスが初期化もやってくれる akinari.tsuji  (2025/08/15)

        //
        // [buy one-day passport]
        //
        // if step05 has been finished, you can use this code by jflute (2019/06/15)
        //Ticket ticket = booth.buyOneDayPassport(10000);
//       // TODO [メモ] クラスのメソッド内部で処理をするように切り分けているので、このtest_...メソッドでの条件分岐がなくなり、読みやすいです　akinari.tsuji  (2025/08/15)
        booth.buyOneDayPassport(10000); // as temporary, remove if you finished step05
        Ticket ticket = new Ticket(TicketType.ONE_DAY, Clock.systemDefaultZone()); // also here

        // *buyOneDayPassport() has this process:
        //if (quantity <= 0) {
        //    throw new TicketSoldOutException("Sold out");
        //}
        //if (handedMoney < oneDayPrice) {
        //    throw new TicketShortMoneyException("Short money: handedMoney=" + handedMoney);
        //}
        //--quantity;
        //salesProceeds = handedMoney;

        // *ticket has these properties:
        //int displayPrice = oneDayPrice;
        //boolean alreadyIn = false;

        // other processes here...
        // ...
        // ...

        //
        // [do in park now!!!]
        //
        ticket.doInPark();

        // *doInPark() has this process:
        //if (alreadyIn) {
        //    throw new IllegalStateException("Already in park by this ticket: displayPrice=" + displayPrice);
        //}
        //alreadyIn = true;

        //
        // [final process]
        //
        saveBuyingHistory(booth, ticket);
    }

    private void saveBuyingHistory(TicketBooth booth, Ticket ticket) {
        if (ticket.isAlreadyIn()) {
            // only logging here (normally e.g. DB insert)
            doShowTicketBooth(booth);
            doShowYourTicket(ticket);
        }
    }

    private void doShowTicketBooth(TicketBooth booth) {
        // 在庫の回収に合わせてコンパイルエラーが発生（quantityをmapで持たせるようにしたため、キーを引数で渡す必要あり）
        // 一旦、one_dayの値を出すように変更しておく
        log("Ticket Booth: quantity={}, salesProceeds={}", booth.getQuantity(TicketType.ONE_DAY), booth.getSalesProceeds());
    }

    private void doShowYourTicket(Ticket ticket) {
        log("Your Ticket: displayPrice={}, alreadyIn={}", ticket.getDisplayPrice(), ticket.isAlreadyIn());
    }

    // write your memo here:　
    // doe [回答] データとそれに対する操作をひとまとめにしたもの akinari.tsuji  (2025/08/15)
    // 責任が切り分けられて、コードの各部の意味がわかりやすくなる & 読みやすい
    // どのメソッドを利用するべきかもわかりやすくなる（オブジェクト指向がないと操作を行うメソッドがどれなのかわかりにくい）
    // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
    // what is object?
    //
    // _/_/_/_/_/_/_/_/_/_/
    // done tsuji [回答] 責任が切り分けられて、ってのがすごく良い表現ですね！ by jflute (2025/08/27)
    // ありがとうございます！
    // #1on1: オブジェクトの概念化を、いかにうまくやるか？ (2025/10/22)
    // 先に横の関係性から見出して、次に縦の関係性 by つじさん
    // 意味のある言葉で集まれるかどうか？ただまとめればいいというわけではない。
    // 業務のクラスを作るので、業務用語の解釈がしっかりできていること。← 現実世界
    // 現実世界 == 物理だけとは限らない (論理上の現実世界の概念もある e.g. 売上、口座、振込)
    // → 業務理解がしっかりできてるか？ってのはとてもコードの実装設計において重要になる。

    // ===================================================================================
    //                                                              Polymorphism Beginning
    //                                                              ======================
    /**
     * What string is sea and land variable at the method end? <br>
     * (メソッド終了時の変数 sea, land の中身は？)
     */
    public void test_objectOriented_polymorphism_1st_concreteOnly() {
        // Javaでは親クラスのコンストラクタは自動で呼び出されるっぽい
        // なので、hitPoint: 10で初期化される
        Dog dog = new Dog();
        // Animalクラスのbarkが呼ばれる。内部では以下の順で処理
        // 1. Animal#breathIn() : ログ出力して、hitPointを-1
        // 2. prepareAbdominalMuscle() : ログ出力してhitPointを-1
        // 3. getBarkWork(): アブストラクト関数なので定義は子クラス側、"wan"を返す
        // 4. doBark(barkWord): hitPointを-1, BarkedSoundのインスタンスを返す
        BarkedSound sound = dog.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = dog.getHitPoint();
        // Animalクラス
        log(land); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_2nd_asAbstract() {
        // AnimalはDogの親クラスなので型エラー起きない
        Animal animal = new Dog();
        // 以降もanimalの型が違うだけなので一問前と同じ結果になるはず
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_3rd_fromMethod() {
        // 同じ結果になって欲しいけど...
        // -> 実行したらなっていたのでOK
        Animal animal = createAnyAnimal();
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }

    private Animal createAnyAnimal() {
        return new Dog();
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_4th_toMethod() {
        Dog dog = new Dog();
        // やっている処理は同じなので同じになるはず
        doAnimalSeaLand_for_4th(dog);
    }

    private void doAnimalSeaLand_for_4th(Animal animal) {
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => wan
        int land = animal.getHitPoint();
        log(land); // your answer? => 7
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_5th_overrideWithSuper() {
        // [メモ] Catクラスが違う部分はdownHitPointの実装とgetBarkWordの返り値
        // あとFastRunnerを継承している（interface?）
        // 確か、interfaceは必ず継承側で実装が必要
        // Rubyであればmoduleのmixinが使える
        // [つぶやき] ここの言語の思想を勉強したい
        // 前にまつもとゆきひろさんの本を読んだが、わからなすぎて撤退した記憶
        // Lisp, Smalltalkの話が出てきて？となって、メタプログラミングの話の頃には沈没した記憶
        Animal animal = new Cat();
        // 基本的には同じだけど、hitPointの減り方が変わる
        // 計3回downHitPointが呼ばれるのは同じ
        // 1回目：10の時は1へっておしまい　→ ９
        // 2回目：９の時は２へる　→ ７
        // 3回目：７ → 5
        BarkedSound sound = animal.bark();
        String sea = sound.getBarkWord();
        log(sea); // your answer? => nya-
        int land = animal.getHitPoint();
        log(land); // your answer? => 5
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_6th_overriddenWithoutSuper() {
        Animal animal = new Zombie();
        BarkedSound sound = animal.bark(); // BarkSound("uooo")
        String sea = sound.getBarkWord(); // "uooo"
        log(sea); // your answer? => "uooo"
        int land = animal.getHitPoint();
        log(land); // your answer? => -1
        // -1から変わっていないので、downHitPointはZombie側のものが呼ばれたことがわかる
        // Zombie.breathIn()の中でsuper.breathIn()を実行しており、Animal.breathIn()の中でdownHitPoint()を呼び出している
        // この時、オーバーライドされた関数のうち親クラスの関数を実行するのはsuperで指定したものだけ
        // super.method_name()の中ではオーバーライドされた関数が実行される
    }

    /**
     * What is happy if you can assign Dog or Cat instance to Animal variable? <br>
     * (Animal型の変数に、DogやCatなどのインスタンスを代入できると何が嬉しいのでしょう？)
     */
    public void test_objectOriented_polymorphism_7th_whatishappy() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is happy?
        // Animalを継承するクラス分だけ変数や条件分岐を用意しなくて良いためコードが簡潔で済む
        // _/_/_/_/_/_/_/_/_/_/

        // もしも代入できない場合,,,
        int animalType = 1; // 吠えさせたい動物のコード（1: Dog, 2: Cat, 3: Zombie）
        switch (animalType) {
        case 1:
            Dog dog = new Dog();
            BarkedSound sound = dog.bark();
            String sea = sound.getBarkWord();
            log(sea);
            break;
        case 2:
            Cat cat = new Cat();
            BarkedSound sound2 = cat.bark();
            String sea2 = sound2.getBarkWord();
            log(sea2);
            break;
        case 3:
            Zombie zombie = new Zombie();
            BarkedSound sound3 = zombie.bark();
            String sea3 = sound3.getBarkWord();
            log(sea3);
            break;
        default:
            break;
        }

        // 代入できるおかげで
        Animal targetAnimal = new Dog(); // ここで指定すれば済む
        BarkedSound sound = targetAnimal.bark();
        String sea = sound.getBarkWord();
        log(sea);
    }

    // ===================================================================================
    //                                                              Polymorphism Interface
    //                                                              ======================
    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_dispatch() {
        Loudable loudable = new Zombie();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => "uooo"
        String land = ((Zombie) loudable).bark().getBarkWord();
        log(land); // your answer? => "uooo"
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_hierarchy() {
        Loudable loudable = new AlarmClock();
        String sea = loudable.soundLoudly();
        log(sea); // your answer? => "jiri jiri jiri---"
        boolean land = loudable instanceof Animal;
        log(land); // your answer? => false

        // 実験
        Loudable loudable2 = new Zombie();
        log(loudable2 instanceof Animal);
        // こちらはtrueになる
        log(loudable2 instanceof Loudable);

        // loudableはLoudableという抽象クラスを具象化したAlarmClockクラスのインスタンス（型はLoudableで宣言）, Animalのインスタンスではない
        // loudable2はLoudableという抽象クラスを具象化したZombieクラスのインスタンス（型はLoudableで宣言）, Animalのインスタンスとして扱われる
        // -> 継承するクラスの型（ここではLoudable）を利用しても、内部（コンストラクタ？）としてはちゃんとAlarmClock型、Zombie型として扱われている模様
    }

    /** Same as the previous method question. (前のメソッドの質問と同じ) */
    public void test_objectOriented_polymorphism_interface_partImpl() {
        Animal seaAnimal = new Cat();
        Animal landAnimal = new Zombie();
        boolean sea = seaAnimal instanceof FastRunner;
        log(sea); // your answer? => true
        boolean land = landAnimal instanceof FastRunner;
        log(land); // your answer? => false
        // CatはFastRunnerのinterfaceを継承？しているのでFastRunnerのインスタンスとなる
        log("seaAnimal Class Name: " + seaAnimal.getClass().getName());
        log("seaAnimal Super Class Name: "  + seaAnimal.getClass().getSuperclass().getName());
        log("landAnimal Class Name: " + landAnimal.getClass().getName());
        log("landAnimal Super Class Name: " + landAnimal.getClass().getSuperclass().getSuperclass().getName());
        Class<?>[] interfaces = landAnimal.getClass().getSuperclass().getInterfaces();
        String[] interfacesName = Arrays.stream(interfaces).map(Class::getName).toArray(String[]::new);
        String joinedInterfaces = String.join(", ", interfacesName);
        log(joinedInterfaces);
    }

    /**
     * Make Dog class implement FastRunner interface. (the method implementation is same as Cat class) <br>
     * (DogもFastRunnerインターフェースをimplementsしてみましょう (メソッドの実装はCatと同じで))
     */
    public void test_objectOriented_polymorphism_interface_runnerImpl() {
        // your confirmation code here
        Dog seaAnimal = new Dog();
        seaAnimal.run();
    }

    /**
     * What is difference as concept between abstract class and interface? <br>
     * (抽象クラスとインターフェースの概念的な違いはなんでしょう？)
     */
    public void test_objectOriented_polymorphism_interface_whatisdifference() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // what is difference?
        // 抽象クラスは概念として親子関係にあるもの
        // インターフェースは概念として親子関係になく、共通のメソッド（機能)を持つ
        // 抽象クラス
//        abstract class Animal {}
//        class Cat extends Animal {}
//        class Dog extends Animal {}
//
//        interface Swimmer {}
//        abstract class Robot {}
//        class Penguin extends Animal implements Swimmer {}
//        class SwimRobot{} extends Robot implements Swimmer {}
        // _/_/_/_/_/_/_/_/_/_/
    }

    // ===================================================================================
    //                                                                 Polymorphism Making
    //                                                                 ===================
    /**
     * Make concrete class of Animal, which is not FastRunner, in "objanimal" package. (implementation is as you like) <br>
     * (FastRunnerではないAnimalクラスのコンクリートクラスをobjanimalパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeConcrete() {
        // your confirmation code here
        Turtle turtle = new Turtle();
        BarkedSound barkedSound = turtle.bark();
        String barkedWord= barkedSound.getBarkWord();
        log(barkedWord);
    }

    /**
     * Make interface implemented by part of Animal concrete class in new package under "objanimal" package. (implementation is as you like) <br>
     * (Animalクラスの一部のコンクリートクラスだけがimplementsするインターフェースをobjanimal配下の新しいパッケージに作成しましょう (実装はお好きなように))
     */
    public void test_objectOriented_polymorphism_makeInterface() {
        // your confirmation code here
        // TODO jfulte Javaに詳しくないのですが、パッケージってどのような粒度で分けるものなのでしょうか？ (2025/11/06)
        // intefaceとそれを実装するクラスは同一のパッケージに入れるべきでしょうか？
        Turtle turtle = new Turtle();
        for (int i = 0; i < 20; i++) {
            turtle.sleep();
        }
    }

    // ===================================================================================
    //                                                                           Challenge
    //                                                                           =========
    /**
     * Extract St6MySql, St6PostgreSql (basic.st6.dbms)'s process to abstract class (as super class and sub-class) <br>
     * (St6MySql, St6PostgreSql (basic.st6.dbms) から抽象クラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_generalization_extractToAbstract() {
        // your confirmation code here
        St6Sql sql = new St6MySql();
        log(sql.buildPagingQuery(100, 10));
        sql = new St6PostgreSql();
        log(sql.buildPagingQuery(100, 10));
    }

    /**
     * Extract St6OperationSystem (basic.st6.os)'s process to concrete classes (as super class and sub-class) <br>
     * (St6OperationSystem (basic.st6.os) からコンクリートクラスを抽出してみましょう (スーパークラスとサブクラスの関係に))
     */
    public void test_objectOriented_writing_specialization_extractToConcrete() {
        // your confirmation code here
        St06MacOperatingSystem os = new St06MacOperatingSystem("root");
        log(os.buildUserResourcePath("hogehoge"));

        // protected: 同一パッケージ・子クラスからアクセス可能
        // 何も設定しない：クラス内・同一パッケージからアクセス可能
        // パッケージは名前空間。フォルダ構成と一致する必要あり
        // このファイルでは　package org.docksidestage.javatry.basic;
        // ディレクトリは java/org.docksidestage/javatry/basic/Step06...
    }

    // ===================================================================================
    //                                                                           Good Luck
    //                                                                           =========
    /**
     * Extract Animal's bark() process as BarkingProcess class to also avoid large abstract class. <br>
     * (抽象クラス肥大化を抑制するためにも、Animalのbark()のプロセス(処理)をBarkingProcessクラスとして切り出しましょう)
     */
    public void test_objectOriented_writing_withDelegation() {
        // your confirmation code here
    }

    /**
     * Put barking-related classes, such as BarkingProcess and BarkedSound, into sub-package. <br>
     * (BarkingProcessやBarkedSoundなど、barking関連のクラスをサブパッケージにまとめましょう)
     * <pre>
     * e.g.
     *  objanimal
     *   |-barking
     *   |  |-BarkedSound.java
     *   |  |-BarkingProcess.java
     *   |-loud
     *   |-runner
     *   |-Animal.java
     *   |-Cat.java
     *   |-Dog.java
     *   |-...
     * </pre>
     */
    public void test_objectOriented_writing_withPackageRefactoring() {
        // your confirmation code here
    }

    /**
     * Is Zombie correct as sub-class of Animal? Analyze it in thirty seconds. (thinking only) <br>
     * (ゾンビは動物クラスのサブクラスとして適切でしょうか？30秒だけ考えてみましょう (考えるだけでOK))
     */
    public void test_objectOriented_zoo() {
        // write your memo here:
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // is it corrent?
        //
        // _/_/_/_/_/_/_/_/_/_/
    }
}
