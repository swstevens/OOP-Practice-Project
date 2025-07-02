// File: src/libraryoop/models/Lendable.java
package libraryoop.models;

// Interface demonstrating contract-based design
public interface Lendable {
    boolean isAvailable();
    int getCheckoutPeriod();
    void setAvailability(boolean available);
}