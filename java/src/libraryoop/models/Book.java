// File: src/libraryoop/models/Book.java
package libraryoop.models;

import java.util.Random;

// Abstract base class demonstrating inheritance and encapsulation
public abstract class Book implements Lendable, Searchable {
    // Private fields demonstrating encapsulation
    private final int id;
    private final String isbn; // Final - immutable after construction
    private String title;
    private String author;
    private boolean isAvailable = true;
    
    // Protected constructor for inheritance only
    protected Book(String title, String author, String isbn) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        
        this.id = new Random().nextInt(9000) + 1000; // Simple ID generation
        this.title = title.trim();
        this.author = author.trim();
        this.isbn = isbn.trim();
    }
    
    // Public getters demonstrating controlled access
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    
    // Protected setter for inheritance
    protected void setTitle(String title) {
        if (title != null && !title.trim().isEmpty()) {
            this.title = title.trim();
        }
    }
    
    protected void setAuthor(String author) {
        if (author != null && !author.trim().isEmpty()) {
            this.author = author.trim();
        }
    }
    
    // Interface implementation
    @Override
    public boolean isAvailable() {
        return isAvailable;
    }
    
    @Override
    public void setAvailability(boolean available) {
        this.isAvailable = available;
    }
    
    // Abstract method - must be implemented by subclasses
    @Override
    public abstract int getCheckoutPeriod();
    
    // Virtual method - can be overridden by subclasses
    public String getDisplayInfo() {
        return "'" + title + "' by " + author;
    }
    
    // Default interface implementation
    @Override
    public boolean matchesQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            return false;
        }
        
        String lowerQuery = query.toLowerCase();
        return title.toLowerCase().contains(lowerQuery) ||
               author.toLowerCase().contains(lowerQuery);
    }
    
    // Override equals and hashCode for proper object comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }
    
    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", isbn='" + isbn + '\'' +
               ", isAvailable=" + isAvailable +
               '}';
    }
}