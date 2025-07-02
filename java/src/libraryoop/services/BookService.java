// File: src/libraryoop/services/BookService.java
package libraryoop.services;

import libraryoop.models.Book;
import java.util.List;

// Main service interface
public interface BookService {
    void addBook(Book book);
    List<Book> getAllBooks();
    <T extends Book> List<T> getBooksByType(Class<T> bookClass);
    Book getBookByIsbn(String isbn);
    CheckoutResult checkoutBook(String isbn);
    AvailabilityReport getAvailabilityReport();
    List<Book> searchBooks(String query);
}