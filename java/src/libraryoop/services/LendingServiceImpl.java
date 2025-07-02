// File: src/libraryoop/services/LendingServiceImpl.java
package libraryoop.services;

import java.lang.reflect.Method;

// Implementation demonstrating generics and reflection
public class LendingServiceImpl implements LendingService {
    @Override
    public <T> String calculateDueDate(T item) {
        /*
        TODO: Student should implement this method using generics and reflection
        - Use reflection to find getCheckoutPeriod() method on class T
        - If method exists, invoke it and return "Due in X days"
        - If method doesn't exist, return "Unknown checkout period"
        - Handle exceptions gracefully
        
        Example implementation:
        try {
            Method method = item.getClass().getMethod("getCheckoutPeriod");
            Object result = method.invoke(item);
            return "Due in " + result + " days";
        } catch (Exception e) {
            return "Unknown checkout period";
        }
        */
        
        // Placeholder implementation - students should replace this
        return "Unknown checkout period";
    }
    
    @Override
    public <T> boolean canCheckout(T item) {
        /*
        TODO: Student should implement this method using reflection
        - Use reflection to find isAvailable() method on class T
        - Invoke the method and return the boolean result
        - Return false if method doesn't exist or invocation fails
        - Handle exceptions gracefully
        
        Example implementation:
        try {
            Method method = item.getClass().getMethod("isAvailable");
            Object result = method.invoke(item);
            return (Boolean) result;
        } catch (Exception e) {
            return false;
        }
        */
        
        // Placeholder implementation - students should replace this
        return false;
    }
}