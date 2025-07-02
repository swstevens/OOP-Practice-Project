// File: src/libraryoop/services/NotificationService.java
package libraryoop.services;

// Interface for notification service
public interface NotificationService {
    String sendCheckoutNotification(String bookTitle, String dueDate);
}