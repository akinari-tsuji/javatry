package org.docksidestage.javatry.basic.st6.dbms;

// #1on1: もし、他のサブクラスを作るとしたら、具体的にどんなクラスがある？ (2026/01/21)
// e.g. SQLite, MariaDB(forked from MySQL), Oracle(DB) (by つじさん)
// Percona(forked from MySQL), DB2(IBM), SQLServer(Microsoft) (by jflute)
// であれば...
// MySQL is a St6Sql, PostgreSQL is a St6Sql
// Oracle is a St6Sql, DB2 is a St6Sql, ...
// Oracle is a SQL, DB2 is a SQL, ...
// done tsuji なので、抽象クラスの名前、is-a がしっくりくる名前にしましょう by jflute (2026/01/21)
// (なんでこんな名前にしたんですかね？笑 by つじさん)
// #1on1: DBという言葉は色々な場所で使われるので若干曖昧になりがち... (2026/01/21)
// なので、「データベース製品」を示す言葉として、DBMS (Database Management System) ってのがよく使われる。
// (さらに厳密には、リレーショナルデータベースだけを示すなら、RDBMS になる)

// #1on1: MySQLのforkのDBMSの話、海外でのツール選択の話 (2026/01/21)
// 無難なものを使うという思考だと、ほとんど利用する機会はないはずだけど...

// done jflute Dbmsに変更しました  by akinari.tsuji (2026/02/17)
// DatabaseManagementSystemと迷いましたが、一般的な略語なのでDBMSを採用しました
// また、調べたら先頭のみ大文字での記述が良いとのことだったのでDbmsにしました

/**
 * @author akinari.tsuji
 */
public abstract class Dbms {
    public String doBuildPagingQuery(int pageSize, int pageNumber) {
        // ここを共通処理で利用できるようにしたい
        // やりたいことは
        // 1. offsetの計算
        // 2. Queryを返す（ここがサブクラスごとに、異なるようにしたいところ）
        // queryを返すメソッドの名前に悩む→doBuildPagingQuery
        int offset = pageSize * (pageNumber - 1);
        return buildPagingQuery(offset, pageSize);
    }

    // こっちをサブクラスでオーバーライドして振る舞いの差を作る
    protected abstract String buildPagingQuery(int offset, int pageSize);
}
