// File: src/libraryoop/services/CheckoutResult.java
package libraryoop.services;

// Result classes demonstrating strong typing
public class CheckoutResult {
    private final boolean success;
    private final String message;
    private final String errorMessage;
    private final String dueDate;
    private final Integer checkoutPeriod;
    private final String notification;
    
    // Constructor for success
    public CheckoutResult(boolean success, String message, String dueDate, 
                         int checkoutPeriod, String notification) {
        this.success = success;
        this.message = message;
        this.errorMessage = null;
        this.dueDate = dueDate;
        this.checkoutPeriod = checkoutPeriod;
        this.notification = notification;
    }
    
    // Constructor for failure
    public CheckoutResult(boolean success, String errorMessage) {
        this.success = success;
        this.message = null;
        this.errorMessage = errorMessage;
        this.dueDate = null;
        this.checkoutPeriod = null;
        this.notification = null;
    }
    
    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getErrorMessage() { return errorMessage; }
    public String getDueDate() { return dueDate; }
    public Integer getCheckoutPeriod() { return checkoutPeriod; }
    public String getNotification() { return notification; }
}