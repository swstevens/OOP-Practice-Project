// File: src/libraryoop/services/LendingService.java
package libraryoop.services;

// Interface for lending service demonstrating contract-based design
public interface LendingService {
    <T> String calculateDueDate(T item);
    <T> boolean canCheckout(T item);
}