import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

class BookShelfIterator implements Iterator<Book> {
    private BookShelf bookShelf;
    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        index = 0;
    }

    public boolean hasNext() {
        if (index < bookShelf.getLength()) {
            return true;
        }

        return false;
    }

    public Book next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        
        Book book = bookShelf.getBookAt(index);
        index++;
        return book;
    }
}
