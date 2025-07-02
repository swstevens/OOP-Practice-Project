// File: src/libraryoop/services/TypeReport.java
package libraryoop.services;

public class TypeReport {
    private final int total;
    private final int available;
    
    public TypeReport(int total, int available) {
        this.total = total;
        this.available = available;
    }
    
    public int getTotal() { return total; }
    public int getAvailable() { return available; }
}