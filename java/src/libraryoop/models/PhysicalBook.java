// File: src/libraryoop/models/PhysicalBook.java
package libraryoop.models;

// Another concrete implementation
public class PhysicalBook extends Book {
    private final String location;
    private String condition;
    
    public PhysicalBook(String title, String author, String isbn, String location, String condition) {
        super(title, author, isbn);
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        this.location = location.trim();
        this.condition = (condition != null && !condition.trim().isEmpty()) ? condition.trim() : "Good";
    }
    
    // Convenience constructor with default condition
    public PhysicalBook(String title, String author, String isbn, String location) {
        this(title, author, isbn, location, "Good");
    }
    
    // Getter methods
    public String getLocation() { return location; }
    public String getCondition() { return condition; }
    
    // Setter for mutable field
    public void setCondition(String condition) {
        if (condition != null && !condition.trim().isEmpty()) {
            this.condition = condition.trim();
        }
    }
    
    // Different implementation of abstract method
    @Override
    public int getCheckoutPeriod() {
        return 14; // Physical books: 2 weeks
    }
    
    // Override virtual method
    @Override
    public String getDisplayInfo() {
        return super.getDisplayInfo() + " [Physical: " + location + ", " + condition + "]";
    }
    
    // Override interface method
    @Override
    public boolean matchesQuery(String query) {
        return super.matchesQuery(query) ||
               (query != null && location.toLowerCase().contains(query.toLowerCase()));
    }
}