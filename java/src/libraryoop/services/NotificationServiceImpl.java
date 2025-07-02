// File: src/libraryoop/services/NotificationServiceImpl.java
package libraryoop.services;

// Simple notification service
public class NotificationServiceImpl implements NotificationService {
    @Override
    public String sendCheckoutNotification(String bookTitle, String dueDate) {
        return "Notification: '" + bookTitle + "' checked out. " + dueDate;
    }
}