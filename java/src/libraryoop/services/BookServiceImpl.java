// File: src/libraryoop/services/BookServiceImpl.java
package libraryoop.services;

import libraryoop.models.Book;
import libraryoop.models.DigitalBook;

import java.util.*;
import java.util.stream.Collectors;

// Main service class demonstrating dependency injection and composition
public class BookServiceImpl implements BookService {
    private final Set<Book> books;
    private final LendingService lendingService;
    private final NotificationService notificationService;
    
    public BookServiceImpl(LendingService lendingService, NotificationService notificationService) {
        /*
        TODO: Student should implement proper constructor
        - Initialize books list
        - Store injected dependencies with null checks
        - Demonstrate Java's constructor dependency injection
        
        Example implementation:
        if (lendingService == null) throw new IllegalArgumentException("LendingService cannot be null");
        if (notificationService == null) throw new IllegalArgumentException("NotificationService cannot be null");
        
        this.books = new ArrayList<>();
        this.lendingService = lendingService;
        this.notificationService = notificationService;
        */
        if (lendingService == null) throw new IllegalArgumentException("LendingService must be declared");
        if (notificationService == null) throw new IllegalArgumentException("NotificationService must be declared");

        // Minimal implementation to allow compilation
        this.books = new HashSet<>();
        this.lendingService = lendingService;
        this.notificationService = notificationService;
    }
    
    @Override
    public void addBook(Book book) {        
        // Minimal implementation to allow compilation
        if (book == null) throw new IllegalArgumentException("Book must be declared");
        
        for (Book book_search : books) {
            if (book_search.getIsbn() == book.getIsbn()) throw new IllegalArgumentException("Book ISBN already exists, no duplicates");
        }

        books.add(book);
    }
    
    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
    
    @Override
    public <T extends Book> List<T> getBooksByType(Class<T> bookClass) {
        /*
        TODO: Student should implement this method using generics
        - Filter books by type using instanceof or stream filtering
        - Cast to appropriate type safely
        - Demonstrate Java's generic bounds and type safety
        
        Example implementation:
        List<T> result = new ArrayList<>();
        for (Book book : books) {
            if (bookClass.isInstance(book)) {
                result.add(bookClass.cast(book));
            }
        }
        return result;
        
        Or using streams:
        return books.stream()
                   .filter(bookClass::isInstance)
                   .map(bookClass::cast)
                   .collect(Collectors.toList());
        */
        
        // Minimal implementation for compilation
        List<T> result = new ArrayList<>();
        for (Book book : books) {
            if (bookClass.isInstance(book)) {
                result.add(bookClass.cast(book));
            }            
        }
        return result;
    }
    
    @Override
    public Book getBookByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) return null;
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
    
    @Override
    public CheckoutResult checkoutBook(String isbn) {
        /*
        public CheckoutResult(boolean success, String errorMessage) {
            this.success = success;
            this.message = null;
            this.errorMessage = errorMessage;
            this.dueDate = null;
            this.checkoutPeriod = null;
            this.notification = null;
        }
        */
        try {
            Book book = getBookByIsbn(isbn);
            if (!book.isAvailable()) {
                throw new IllegalArgumentException("Book is not available");
            }
            book.setAvailability(false);
            return new CheckoutResult(true, "Book checked out successfully", String.format("Due in %d days",book.getCheckoutPeriod()), book.getCheckoutPeriod(), "notification message");

        } catch (Exception e) {
            return new CheckoutResult(false, "Not implemented");
        }
    }
    
    @Override
    public AvailabilityReport getAvailabilityReport() {
        /*
        TODO: Student should implement this method demonstrating polymorphic reporting
        - Count total books, available books, checked out books
        - Group by book type (DigitalBook, PhysicalBook, AudioBook)
        - Calculate average checkout period across all book types
        - Use polymorphic method calls (getCheckoutPeriod) for calculations
        - Return AvailabilityReport with all data
        
        Example implementation structure:
        int totalBooks = books.size();
        int availableBooks = 0;
        Map<String, TypeReport> byType = new HashMap<>();
        double totalCheckoutPeriod = 0;
        
        // Count by type and availability
        for (Book book : books) {
            if (book.isAvailable()) availableBooks++;
            totalCheckoutPeriod += book.getCheckoutPeriod();
            
            String typeName = book.getClass().getSimpleName();
            // Update type counts...
        }
        
        double averageCheckoutPeriod = totalBooks > 0 ? totalCheckoutPeriod / totalBooks : 0;
        
        return new AvailabilityReport(totalBooks, availableBooks, 
                                    totalBooks - availableBooks, byType, averageCheckoutPeriod);
        */
        
        // Placeholder implementation that should fail tests        
        double totalCheckoutPeriod = 0;
        int totalBooks = books.size();
        int availableBooks = 0;
        Map<String, TypeReport> byType = new HashMap<>();
        HashMap<String, Integer> countByType = new HashMap<>();
        HashMap<String, Integer> countAvailableByType = new HashMap<>();
        for (Book book : books) {
            String curType = book.getClass().getSimpleName();
            if (book.isAvailable()) {
                totalCheckoutPeriod += book.getCheckoutPeriod();
                countAvailableByType.put(curType, countAvailableByType.getOrDefault(curType, 0) + (book.isAvailable() ? 1 : 0));
                book.setAvailability(false);
                totalCheckoutPeriod += book.getCheckoutPeriod();
            }
            countByType.put(curType, countByType.getOrDefault(curType, 0) + 1);

        }
        for (String type : new String[]{"DigitalBook","PhysicalBook","AudioBook"}) {
            byType.put(type, new TypeReport(countByType.getOrDefault(type,0), countAvailableByType.getOrDefault(type,0)));
        } 
        availableBooks = countAvailableByType.values().stream().reduce(0, Integer::sum);
        return new AvailabilityReport(totalBooks, availableBooks, totalBooks-availableBooks, byType, totalCheckoutPeriod/(totalBooks-availableBooks));
    }
    
    @Override
    public List<Book> searchBooks(String query) {
        /*
        TODO: Student should implement this method
        - Use the matchesQuery method on each book (polymorphic behavior)
        - Return books that match the query
        - Handle null/empty query appropriately
        - Consider using Java 8 streams for elegant implementation
        
        Example implementation:
        if (query == null || query.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        List<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.matchesQuery(query)) {
                results.add(book);
            }
        }
        return results;
        
        Or using streams:
        return books.stream()
                   .filter(book -> book.matchesQuery(query))
                   .collect(Collectors.toList());
        */
        return new ArrayList<>();
    }
}