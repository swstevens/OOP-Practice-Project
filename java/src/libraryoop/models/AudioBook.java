// File: src/libraryoop/models/AudioBook.java
package libraryoop.models;

// Third implementation demonstrating polymorphism
public class AudioBook extends Book {
    private final String narrator;
    private final int durationMinutes;
    
    public AudioBook(String title, String author, String isbn, String narrator, int durationMinutes) {
        super(title, author, isbn);
        if (narrator == null || narrator.trim().isEmpty()) {
            throw new IllegalArgumentException("Narrator cannot be null or empty");
        }
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        this.narrator = narrator.trim();
        this.durationMinutes = durationMinutes;
    }
    
    // Getter methods
    public String getNarrator() { return narrator; }
    public int getDurationMinutes() { return durationMinutes; }
    
    // Another different implementation
    @Override
    public int getCheckoutPeriod() {
        return 21; // Audio books: 3 weeks
    }
    
    public double getDurationHours() {
        return durationMinutes / 60.0;
    }
    
    @Override
    public String getDisplayInfo() {
        return super.getDisplayInfo() + " [Audio: " + narrator + ", " + 
               String.format("%.1f", getDurationHours()) + "h]";
    }
    
    @Override
    public boolean matchesQuery(String query) {
        return super.matchesQuery(query) ||
               (query != null && narrator.toLowerCase().contains(query.toLowerCase()));
    }
}