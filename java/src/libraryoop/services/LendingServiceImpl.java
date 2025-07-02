// File: src/libraryoop/services/LendingServiceImpl.java
package libraryoop.services;

import java.lang.reflect.Method;

// Implementation demonstrating generics and reflection
public class LendingServiceImpl implements LendingService {
    @Override
    public <T> String calculateDueDate(T item) {        
        try {
            Method method = item.getClass().getMethod("getCheckoutPeriod");
            Object result = method.invoke(item);
            return "Due in " + result + " days";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    
    @Override
    public <T> boolean canCheckout(T item) {
        try {
            Method method = item.getClass().getMethod("isAvailable");
            Object result = method.invoke(item);
            return (boolean) result;
        } catch (Exception e) {
            return false;
        }
    }
}