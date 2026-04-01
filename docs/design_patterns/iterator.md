# Iterator パターン

- for文の変数iの動きを抽象化したもの
- 全体をスキャンして処理を繰り返すもの

> C++にもイテレータあったような記憶

# Sample Program

```java
# java.lang.Iterable
public interface Iterable<E> {
    public abstract Iterator<E> iterator();
}

public interface Iterator<E> {
    public abstract boolean hasNext();
    public abstract E next();
}

# Book.java

public class Book {
    private String name;

    public Book(String name) {
        this.name = name
    }

    public String getName() {
        return name;
    }
}

# BookShelf.java

public class BookShelf implements Iterable<Book> {
    private Book[] books;
    private int last = 0;

    public BookShelf(int maxsize) {
        this.books = new Book[maxsize];
    }

    public Book getBookAt(int index) {
        return books[index];
    }

    public void appendBook(Book book) {
        this.books[last] = book;
        last++;
    }

    public int getLength() {
        return last;
    }

    @Override
    public Iterator<Book> iterator() {
        return new BookShelfIterator(this);
    }
}

# BookShelfIterator.java

public class BookShelfIterator implements Iterator<Book> {
    private BookShelf bookShelf;
    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        if (index < bookShelf.getLength()) {
            return true;
        }
        return false;
    }

    @Override
    public Book next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        # BookShelfIteratorはBookShelfがgetBookAtを持つことを知っている
        # -> BookShelf, BookShelfIteratorは対の存在
        Book book = bookShelf.getBookAt(indext);
        index++;
        return book;
    }
}

# Main.java

public class Main {
    public static void main(String[] args) {
        # arrange

        Iterator<Book> iter = bookShelf.iterator();
        while (iter.hasNext()) {
            Book book = iter.next();
            System.out.println(book.getName());
        }

        System.out.println();
    }
}

```

## Iterable<E> interface

- Iterable<Book>

# 何が嬉しいか

- BookShelfの実装に依存しない
    - 配列ではなくArrayListを使うようになっても、hasNext, nextさえ実装されていれば良い
- 1つの部品を修正しても、他の部品の修正が少なくて済む

> 複数箇所でBookShelfを利用している場合、iteratorではなく配列を用いていると利用箇所全てを修正する必要がある。

> Q. つまり、実装に依存している箇所を抽象化して変更範囲を一箇所に集約してあげることで変更の負担を減らしている？（そのため、減るだけで変更の負担自体は減らない。当たり前だけど）

## 抽象クラス・インターフェース

- 具体的なクラスを避けることで結合が弱まり、部品として再利用しやすくなる
