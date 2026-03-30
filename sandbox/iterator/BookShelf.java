import java.util.ArrayList;
import java.util.Iterator;

class BookShelf implements Iterable<Book> {
    private ArrayList<Book> books;

    public BookShelf() {
        books = new ArrayList<Book>();
    }

    public Book getBookAt(int index) {
        return books.get(index);
    }

    public void appendBook(Book book) {
        books.add(book);
    }

    public int getLength() {
        return books.size();
    }
    
    @Override
    public Iterator<Book> iterator() {
        return new BookShelfIterator(this);
    }
}
