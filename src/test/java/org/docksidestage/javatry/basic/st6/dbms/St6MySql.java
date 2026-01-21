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
package org.docksidestage.javatry.basic.st6.dbms;

/**
 * @author jflute
 * @author akinari.tsuji
 */
public class St6MySql extends St6Sql {

    // TODO tsuji offsetの計算がPostgreSQLと(概念的にも)全く同じなので再利用したいところ by jflute (2026/01/21)
    // TODO tsuji 例えば、offsetの計算の後に、追加処理をする必要が出てきたとして...(つまり仕様変更) by jflute (2026/01/21)
    // それを、MySQLとPostgreSQLのクラス両方で、同じように直すってのを避けたい。(もしサブクラス20個あったら...)
    // (現状は、処理の流れが再利用できてない: 1なにやって2なにやって...が冗長している)
    // #1on1: 抽象クラスを導入するというのは、ポリモーフィズムだけじゃなく、実装継承によるコードの最適化もやりたい。 (2026/01/21)
    public String buildPagingQuery(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);
        return "limit " + offset + ", " + pageSize;
    }
}
