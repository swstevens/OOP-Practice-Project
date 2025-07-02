// File: src/libraryoop/services/AvailabilityReport.java
package libraryoop.services;

import java.util.*;

public class AvailabilityReport {
    private final int totalBooks;
    private final int availableBooks;
    private final int checkedOutBooks;
    private final Map<String, TypeReport> byType;
    private final double averageCheckoutPeriod;
    
    public AvailabilityReport(int totalBooks, int availableBooks, int checkedOutBooks,
                             Map<String, TypeReport> byType, double averageCheckoutPeriod) {
        this.totalBooks = totalBooks;
        this.availableBooks = availableBooks;
        this.checkedOutBooks = checkedOutBooks;
        this.byType = new HashMap<>(byType);
        this.averageCheckoutPeriod = averageCheckoutPeriod;
    }
    
    // Getters
    public int getTotalBooks() { return totalBooks; }
    public int getAvailableBooks() { return availableBooks; }
    public int getCheckedOutBooks() { return checkedOutBooks; }
    public Map<String, TypeReport> getByType() { return new HashMap<>(byType); }
    public double getAverageCheckoutPeriod() { return averageCheckoutPeriod; }
}