# Template Method パターン

## 概要

処理の流れ（骨格）は共通しているが、具体的な処理が異なる場合に利用するパターン。
親クラスでアルゴリズムの骨格を定義し、具体的な処理はサブクラスに委ねる。

## 基本構造

```java
abstract class AbstractClass {
    // Template Method - 処理の流れを固定
    public final void templateMethod() {
        step1();
        step2();
        step3();
    }

    protected abstract void step1();
    protected abstract void step2();
    protected abstract void step3();
}

class ConcreteClass extends AbstractClass {
    protected void step1() { /* 具体的な処理 */ }
    protected void step2() { /* 具体的な処理 */ }
    protected void step3() { /* 具体的な処理 */ }
}
```

## 具体例: 労働時間の集計

勤務形態ごとに集計したい値が異なるケース。

```java
abstract class WorkTimeAggregator {
    public final MonthlyReport aggregate(List<WorkRecord> records) {
        List<DailyReport> daily = aggregateDaily(records);
        List<WeeklyReport> weekly = aggregateWeekly(daily);
        return aggregateMonthly(weekly);
    }

    protected abstract DailyReport aggregateDaily(List<WorkRecord> records);
    protected abstract WeeklyReport aggregateWeekly(List<DailyReport> reports);
    protected abstract MonthlyReport aggregateMonthly(List<WeeklyReport> reports);
}

// フレックスタイム制: コアタイム内外の時間を分けて集計
class FlexTimeAggregator extends WorkTimeAggregator { ... }

// 固定時間制: 残業・深夜・休日の割増を計算
class FixedTimeAggregator extends WorkTimeAggregator { ... }

// 裁量労働制: みなし時間で集計
class DiscretionaryTimeAggregator extends WorkTimeAggregator { ... }
```

## 他のユースケース

- **データのインポート/エクスポート**: 読み込み→変換→書き出しの流れは同じだが、CSV/JSON/XMLでパース方法が違う
- **認証フロー**: 入力検証→認証→セッション作成の流れは同じだが、パスワード認証/OAuth/SAMLで認証ステップが違う
- **レポート生成**: ヘッダー→本文→フッターの構成は同じだが、PDF/HTML/Excelで出力方法が違う
- **テストフレームワーク**: setUp→テスト実行→tearDownの流れ（JUnitがまさにこれ）

## 抽象メソッド vs フックメソッド

Template Methodでは2種類のメソッドを使い分ける。

| | 抽象メソッド | フックメソッド |
|---|---|---|
| 実装 | サブクラスで**必須** | 任意（デフォルトが空） |
| 目的 | 処理の中身を委譲 | 拡張ポイントを提供 |
| 例 | `abstract void calculate()` | `void afterCalculate() {}` |

### フックメソッドの例

```java
abstract class OrderProcessor {
    public final void process(Order order) {
        validate(order);
        calculate(order);
        afterCalculate(order);  // フック
        save(order);
    }

    protected abstract void validate(Order order);
    protected abstract void calculate(Order order);

    // フック: デフォルトは何もしない、必要なサブクラスだけオーバーライド
    protected void afterCalculate(Order order) {}
}

class DiscountOrderProcessor extends OrderProcessor {
    @Override
    protected void afterCalculate(Order order) {
        applyDiscount(order);  // 後処理を追加
    }
}
```

フックにはbooleanを返して処理の分岐を制御するパターンもある。

```java
protected boolean shouldNotify() {
    return true;  // デフォルトは通知する、サブクラスで false にできる
}
```

## Rubyでの Template Method

Rubyには `abstract` キーワードがない。代わりに `raise NotImplementedError` で実装を強制する。

```ruby
class WorkTimeAggregator
  def aggregate(records)
    daily = aggregate_daily(records)
    weekly = aggregate_weekly(daily)
    aggregate_monthly(weekly)
  end

  private

  def aggregate_daily(_records)
    raise NotImplementedError, "#{self.class}#aggregate_daily must be implemented"
  end
end
```

コンパイル時ではなく実行時エラーになる点がJavaとの違い。

## Rails (ActiveRecord Callbacks) との関係

RailsのActiveRecord callbacksは、Template Methodのフックの思想をメタプログラミングで汎用化したもの。

### 使い方

```ruby
class User < ApplicationRecord
  before_save :normalize_email
  after_create :send_welcome_email
end
```

### 仕組み

1. **クラスロード時**: `before_save :normalize_email` が実行され、`set_callback` でコールバックを登録
2. **実行時**: `save` 内部で `run_callbacks(:save)` が呼ばれ、登録済みのコールバックを `send` で動的に呼び出す

```
クラスロード時:
  before_save :normalize_email
    → set_callback(:save, :before, :normalize_email)
      → __callbacks[:save] に { kind: :before, method: :normalize_email } を追加

実行時:
  user.save
    → run_callbacks(:save)
      → send(:normalize_email)  # 動的呼び出し
      → 本体(SQL発行)
      → send(after側のメソッド)
```

### メタプログラミングの核心

- **`define_method`**: `before_save`, `after_save` 等のクラスメソッドをループで動的に定義
- **`send`**: 文字列/シンボルでメソッドを動的に呼び出し
- **`extend`/`include`**: モジュールで機能を注入

### コールバックの実行順序

```
save呼び出し
  ├─ before_validation
  ├─ validation実行
  ├─ after_validation
  ├─ before_save
  │   ├─ before_create (or before_update)
  │   ├─ 実際のSQL実行
  │   ├─ after_create (or after_update)
  ├─ after_save
  └─ after_commit / after_rollback
```

### Template Method vs ActiveRecord Callbacks

| | Template Method | ActiveRecord Callbacks |
|---|---|---|
| カスタマイズ方法 | メソッドのオーバーライド | メソッドの登録 |
| 処理数 | 1つ/フックポイント | 複数可 |
| 条件分岐 | サブクラスで分岐 | `if:`/`unless:` オプション |
| 関係 | 継承が必須 | 継承なしでもOK |

## 関連パターン: Observer パターン（イベントパターン）

後処理のイベントをクラスにして登録・呼び出しするパターン。Template Methodとは別のGoFパターン。

```java
// イベントクラス
class OrderCalculatedEvent {
    private final Order order;
    // ...
}

// リスナー
class DiscountListener implements EventListener<OrderCalculatedEvent> {
    public void handle(OrderCalculatedEvent event) {
        applyDiscount(event.getOrder());
    }
}

// イベント発火側で登録・通知
var processor = new OrderProcessor();
processor.addListener(new DiscountListener());
processor.addListener(new LoggingListener());  // 複数登録可
```

### Template Method (フック) vs Observer (イベント)

| | Template Method (フック) | Observer (イベント) |
|---|---|---|
| 後処理の追加方法 | サブクラスでオーバーライド | リスナーを登録 |
| 関係 | 継承（密結合） | 委譲（疎結合） |
| 後処理の数 | 1つ（1クラスにつき） | 複数登録可 |
| 実行時の変更 | 不可 | 可（動的に追加/削除） |
| 向いてるケース | 処理の流れ自体を差し替え | 「何か起きたら反応する」 |

RailsのActiveRecord callbacksは、Template Methodのフック的な使い勝手と、Observerの複数登録できる柔軟性を両方持った中間的な設計。
