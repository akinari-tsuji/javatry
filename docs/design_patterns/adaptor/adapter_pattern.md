# Adapterパターン

「Java言語で学ぶデザインパターン」の内容をベースに、Adapterパターンについて学んだ内容をまとめる。

---

## 目次

1. [Adapterパターンとは](#1-adapterパターンとは)
2. [Adapterパターンのメリット](#2-adapterパターンのメリット)
3. [Adapterパターンの導入タイミング](#3-adapterパターンの導入タイミング)
4. [深掘り: 外部APIの呼び出しとAdapter](#4-深掘り-外部apiの呼び出しとadapter)
5. [深掘り: RubyにおけるAdapter](#5-深掘り-rubyにおけるadapter)

---

## 1. Adapterパターンとは

### 概要

Adapterパターンは、既に存在しているクラスのインターフェースを、求められている別のインターフェースに変換するパターン。
「すでに動いているコードを変更せずに、新しい形で再利用する」ことが目的。

日常の例でいえば、電源プラグの変換アダプターと同じ。日本の家電をそのまま海外のコンセントに差し込めないが、変換アダプターを間に挟めば使える。家電もコンセントも変更しない。

### 2つの実装方法

本では2つの実装方法が紹介されている。

**継承を使う方法（クラスによるAdapter）**

```java
// 既存クラス
class OldPrinter {
    public void printOld(String text) {
        System.out.println("[OLD] " + text);
    }
}

// 求められるインターフェース
interface Printer {
    void print(String text);
}

// Adapter: OldPrinterを継承し、Printerインターフェースを実装
class PrinterAdapter extends OldPrinter implements Printer {
    @Override
    public void print(String text) {
        printOld(text); // 親クラスのメソッドを呼ぶ
    }
}
```

**委譲を使う方法（インスタンスによるAdapter）**

```java
// Adapter: OldPrinterのインスタンスを内部に持つ
class PrinterAdapter implements Printer {
    private OldPrinter oldPrinter;

    public PrinterAdapter(OldPrinter oldPrinter) {
        this.oldPrinter = oldPrinter;
    }

    @Override
    public void print(String text) {
        oldPrinter.printOld(text); // 委譲先のメソッドを呼ぶ
    }
}
```

一般的には委譲の方が柔軟性が高い。Javaは単一継承のため、継承を使うと他のクラスを継承できなくなるという制約がある。

---

## 2. Adapterパターンのメリット

### 既存コードを変更せずに再利用できる

Adapterパターンの最大のメリットは、「両側のコードには触れず、間に薄い変換層を1つ置く」ことで問題を解決できる点にある。

以下、具体的なユースケースとAdapterがない場合の痛みを合わせて紹介する。

### ケースA: レガシーコードの再利用

動作実績のある既存クラスを、新システムのインターフェースに合わせたい場合。

```java
// ===== 既存クラス（変更しない） =====
class OldCsvExporter {
    public String createCsvText(List<String[]> rows) {
        StringBuilder sb = new StringBuilder();
        for (String[] row : rows) {
            sb.append(String.join(",", row)).append("\n");
        }
        return sb.toString();
    }
}

// ===== 新システムのインターフェース =====
interface DataExporter {
    void export(List<Record> records, OutputStream out);
}

// ===== Adapter =====
class CsvExporterAdapter implements DataExporter {
    private OldCsvExporter oldExporter;

    public CsvExporterAdapter(OldCsvExporter oldExporter) {
        this.oldExporter = oldExporter;
    }

    @Override
    public void export(List<Record> records, OutputStream out) {
        // Record → String[] に変換（インターフェースの差を埋める）
        List<String[]> rows = new ArrayList<>();
        for (Record r : records) {
            rows.add(new String[]{ r.getName(), r.getValue() });
        }

        // 既存のCSV生成ロジックをそのまま使う
        String csv = oldExporter.createCsvText(rows);

        // String → OutputStream に書き出す
        out.write(csv.getBytes());
    }
}
```

```java
// 使う側
DataExporter exporter = new CsvExporterAdapter(new OldCsvExporter());
exporter.export(records, outputStream);
```

**Adapterがない場合の痛み:**

- `OldCsvExporter`を直接書き換えると、今まで動いていたコードにバグを入れるリスクがある。さらにOldCsvExporterを使っている既存の呼び出し元も全部壊れる可能性がある
- 新しいCsvExporterをゼロから書くと、テスト済みのCSV生成ロジックをまた書き直す無駄が生じる

### ケースB: サードパーティライブラリの切り替え

ログライブラリを切り替えたい場合。最初からAdapterを挟んでおくことで、切り替えコストを最小化する。

```java
// ===== 自前のインターフェース（Adapter層） =====
interface AppLogger {
    void info(String message);
    void error(String message, Exception e);
}

// ===== Log4j用のAdapter =====
class Log4jAdapter implements AppLogger {
    private Logger logger;

    public Log4jAdapter(Class<?> clazz) {
        this.logger = Logger.getLogger(clazz);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void error(String message, Exception e) {
        logger.error(message, e);
    }
}

// ===== Logbackに切り替えたくなったら、Adapterを1つ追加するだけ =====
class LogbackAdapter implements AppLogger {
    private ch.qos.logback.classic.Logger logger;

    public LogbackAdapter(Class<?> clazz) {
        this.logger = (ch.qos.logback.classic.Logger)
            LoggerFactory.getLogger(clazz);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void error(String message, Exception e) {
        logger.error(message, e);
    }
}
```

```java
// アプリケーションコード（200ファイル全部がこの形）
class OrderService {
    private AppLogger log;

    public OrderService(AppLogger log) {
        this.log = log;
    }

    public void placeOrder() {
        log.info("order placed"); // ← ライブラリが変わっても、ここは絶対に変わらない
    }
}

// 切り替えはここだけ
new OrderService(new Log4jAdapter(OrderService.class));   // Log4j時代
new OrderService(new LogbackAdapter(OrderService.class)); // Logback移行後
```

**Adapterがない場合の痛み:**

200ファイル全部がLog4jを直接呼んでいる状態になる。

```java
class OrderService {
    private Logger log = Logger.getLogger(OrderService.class); // Log4j直呼び
    ...
}
// こういうクラスが200個
```

Logbackに切り替えろと言われたら、200ファイル全部を書き換えることになる。

**重要な前提:** このケースは最初の設計時点で`AppLogger`インターフェースを挟んでいた場合に成り立つ。もし最初からLog4jを直接呼んでいたら、後からAdapterを導入するには結局200ファイルの書き換えが必要になる。つまり「将来変わりうるもの」には最初からAdapterを入れておくことが重要。

### ケースC: チーム間のインターフェース不一致

他チームが提供するクラス同士のインターフェースが合わない場合。

```java
// ===== チームAのコード（変更不可） =====
class UserService {
    public UserDto findUser(long userId) {
        return new UserDto(userId, "田中太郎", "tanaka@example.com");
    }
}

class UserDto {
    private long id;
    private String name;
    private String email;
}

// ===== チームBのコード（変更不可） =====
class BillingEngine {
    public Invoice createInvoice(AccountInfo account) { ... }
}

class AccountInfo {
    private String accountId;    // long ではなく String
    private String displayName;  // name ではなく displayName
    private String contactEmail; // email ではなく contactEmail
}

// ===== Adapter =====
class UserToAccountAdapter {
    private UserService userService;

    public UserToAccountAdapter(UserService userService) {
        this.userService = userService;
    }

    public AccountInfo toAccountInfo(long userId) {
        UserDto user = userService.findUser(userId);

        return new AccountInfo(
            String.valueOf(user.getId()),  // long → String
            user.getName(),                // name → displayName
            user.getEmail()                // email → contactEmail
        );
    }
}
```

```java
// 使う側（10箇所あっても全部これだけ）
UserToAccountAdapter adapter = new UserToAccountAdapter(userService);
AccountInfo account = adapter.toAccountInfo(userId);
Invoice invoice = billingEngine.createInvoice(account);
```

**Adapterがない場合の痛み:**

変換ロジックが呼び出し箇所ごとに散らばる。呼び出す箇所が10箇所あれば10箇所に同じ変換コードが書かれる。フィールドが増えたとき10箇所全部直す羽目になる。

---

## 3. Adapterパターンの導入タイミング

Adapterパターンの使い方には2つのパターンがある。

### 後付けで使う（既存の問題を解決する）

ケースAとケースCがこれに該当する。今ある問題に対して、既存クラスのインターフェースが合わないときにAdapterで変換する。

### 先に入れておく（将来の変更に備える）

ケースBと外部APIの話がこれに該当する。将来の変更コストを下げるために、最初からAdapterで隔離しておく。

### 導入判断の基準

「全部にAdapter入れておけば安心」とはならない。Adapterを入れるとクラスが増えてコードが複雑になるコストがある。

| | Adapterを入れる | 入れない |
|---|---|---|
| 外部API・外部ライブラリ | 変わる可能性が高いので入れる価値あり | |
| 社内の安定したコード | | 過剰設計になりがち |
| チーム間の境界 | 必要になったら後付け | 最初から合わせられるなら不要 |

「将来変わりうるものとの境界に置く防壁」として使えるかどうかが判断のポイント。

---

## 4. 深掘り: 外部APIの呼び出しとAdapter

外部APIの呼び出しにAdapterパターンを適用すると、2つのメリットがある。

### 外部API変更の吸収

外部APIのレスポンス形式やエンドポイントが変わっても、Adapter層だけ修正すれば、ビジネスロジック側は無傷。

### テスタビリティの向上

テスト時にAdapterのインターフェースに対してモック/スタブを差し込めるので、実際のHTTP通信なしでテストできる。

```java
// 自分たちのインターフェース
interface PaymentGateway {
    PaymentResult charge(int amount);
}

// Adapter: Stripe APIを包む
class StripeAdapter implements PaymentGateway {
    private StripeClient client;

    public PaymentResult charge(int amount) {
        StripeCharge result = client.createCharge(amount);
        return new PaymentResult(result.getId(), result.getStatus());
    }
}

// テスト時はモック
class MockPaymentGateway implements PaymentGateway {
    public PaymentResult charge(int amount) {
        return new PaymentResult("test-id", "success");
    }
}
```

### AdapterパターンとDIの関係

テスト時にモックに差し替えられるのは、厳密にはAdapterパターン単体ではなくDI（依存性の注入）との合わせ技。

```java
// Adapterパターンだけ（DIなし）→ 差し替えられない
class OrderService {
    private PaymentGateway gateway = new StripeAdapter(); // 自分でnewしている
}

// Adapterパターン + DI → 差し替え可能
class OrderService {
    private PaymentGateway gateway;

    // 外から注入してもらう = DI
    public OrderService(PaymentGateway gateway) {
        this.gateway = gateway;
    }

    public void checkout(int amount) {
        gateway.charge(amount);
    }
}

// 本番
new OrderService(new StripeAdapter(stripeClient));
// テスト
new OrderService(new MockPaymentGateway());
```

- **Adapterの役割** = インターフェースの変換（StripeのAPIを自分たちのPaymentGatewayに合わせる）
- **DIの役割** = どのAdapterを使うかを外から差し替え可能にする

Adapterだけだと「変換」はできるが「差し替え」はできない。DIと組み合わせて初めてテスト時のモック差し替えが実現する。

---

## 5. 深掘り: RubyにおけるAdapter

### Javaとの違い

Javaでは`interface`と`implements`によってAdapterパターンを実現する。一方、Rubyにはインターフェースという概念がなく、ダックタイピング（同じメソッド名・シグネチャを持っていれば、継承関係なく使える）が基本となる。

そのため、本で紹介されている2つの実装方法のうち、Rubyでは**委譲が主流**。

### Rubyでは委譲が主流な理由

- Rubyにはインターフェース（型制約）がないので、「継承してインターフェースを満たす」という動機がそもそも薄い
- ダックタイピングにより、同じメソッド名を持っていれば継承関係なく使える
- Adapterクラスを作って内部で既存オブジェクトに委譲するだけで十分

```ruby
# 既存クラス（変更したくない）
class OldPrinter
  def print_old(text)
    puts "[OLD] #{text}"
  end
end

# Adapter（委譲）
class PrinterAdapter
  def initialize(old_printer)
    @old_printer = old_printer
  end

  def print(text) # 新しいインターフェースに合わせる
    @old_printer.print_old(text)
  end
end
```

### moduleのincludeはAdapterではない

moduleは「振る舞いの追加（mixin）」であって、「インターフェースの変換」とは役割が異なる。既存クラスにmoduleをincludeすると既存クラスを変更することになるので、Adapterの「既存を変えない」という目的に反する。

### RailsにおけるAdapterの活用例

Railsの中核部分でAdapterパターンは広く使われている。

**ActiveRecord の Database Adapter**

```
ActiveRecord（統一インターフェース）
    ↓
AbstractAdapter（共通処理）
    ↓
├── PostgreSQLAdapter
├── MySQLAdapter
├── SQLiteAdapter
```

`database.yml`の設定でAdapterが切り替わり、アプリケーションコードは「MySQLかPostgreSQLか」を意識しない。

**その他の例:**

- **ActiveStorage** - S3, GCS, ローカルディスクをAdapterで切り替え
- **ActiveJob** - Sidekiq, Resque, DelayedJobなどのバックエンドをAdapterで切り替え
- **ActionMailer** - SMTP, SendGrid, Mailgunなどの配送手段をAdapterで切り替え

Railsが「設定で差し替え可能」にしている部分は、ほぼAdapterパターン（+ DIの考え方）で実現されている。Rubyでも「ライブラリの差し替え」という文脈ではAdapterパターンは普通に活躍する。
