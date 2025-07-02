// File: src/libraryoop/models/DigitalBook.java
package libraryoop.models;

// Concrete implementation demonstrating inheritance
public class DigitalBook extends Book {
    private final String fileFormat;
    private final String downloadUrl;
    
    public DigitalBook(String title, String author, String isbn, String fileFormat) {
        super(title, author, isbn);
        if (fileFormat == null || fileFormat.trim().isEmpty()) {
            throw new IllegalArgumentException("File format cannot be null or empty");
        }
        this.fileFormat = fileFormat.trim();
        this.downloadUrl = "/download/" + isbn + "." + fileFormat;
    }
    
    // Getter methods
    public String getFileFormat() { return fileFormat; }
    public String getDownloadUrl() { return downloadUrl; }
    
    // Implementation of abstract method
    @Override
    public int getCheckoutPeriod() {
        return 7; // Digital books: 1 week
    }
    
    // Method specific to digital books
    public String getDownloadLink() {
        return downloadUrl;
    }
    
    // Override virtual method
    @Override
    public String getDisplayInfo() {
        return super.getDisplayInfo() + " [Digital: " + fileFormat + "]";
    }
    
    // Override interface method with additional logic
    @Override
    public boolean matchesQuery(String query) {
        return super.matchesQuery(query) ||
               (query != null && fileFormat.toLowerCase().contains(query.toLowerCase()));
    }
}