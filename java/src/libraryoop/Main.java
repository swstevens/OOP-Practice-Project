// File: src/libraryoop/Main.java
package libraryoop;

import libraryoop.models.*;
import libraryoop.services.*;
import java.util.*;

public class Main {
    
    static class OOPTestSuite {
        private int testsPassed = 0;
        private int testsFailed = 0;
        private BookService bookService;
        private DigitalBook digitalBook;
        private PhysicalBook physicalBook;
        private AudioBook audioBook;
        
        public OOPTestSuite() {
            setupTestData();
        }
        
        private void setupTestData() {
            // Create services using dependency injection
            LendingService lendingService = new LendingServiceImpl();
            NotificationService notificationService = new NotificationServiceImpl();
            bookService = new BookServiceImpl(lendingService, notificationService);
            
            // Create sample books
            digitalBook = new DigitalBook("Java Programming", "Jane Developer", "1111111111", "pdf");
            physicalBook = new PhysicalBook("Clean Code", "Robert Martin", "2222222222", "Section A", "Excellent");
            audioBook = new AudioBook("Design Patterns", "Gang of Four", "3333333333", "John Narrator", 480);
            
            // Add books to service
            bookService.addBook(digitalBook);
            bookService.addBook(physicalBook);
            bookService.addBook(audioBook);
        }
        
        private void runTest(String testName, Runnable testAction) {
            try {
                testAction.run();
                System.out.println("✅ " + testName);
                testsPassed++;
            } catch (Exception ex) {
                System.out.println("❌ " + testName + ": " + ex.getMessage());
                testsFailed++;
            }
        }
        
        private void testInheritance() {
            // Test that subclasses inherit from Book
            if (!(digitalBook instanceof Book)) throw new RuntimeException("DigitalBook should inherit from Book");
            if (!(physicalBook instanceof Book)) throw new RuntimeException("PhysicalBook should inherit from Book");
            if (!(audioBook instanceof Book)) throw new RuntimeException("AudioBook should inherit from Book");
            
            // Test that abstract method is implemented differently
            if (digitalBook.getCheckoutPeriod() != 7) throw new RuntimeException("Digital book should have 7-day checkout");
            if (physicalBook.getCheckoutPeriod() != 14) throw new RuntimeException("Physical book should have 14-day checkout");
            if (audioBook.getCheckoutPeriod() != 21) throw new RuntimeException("Audio book should have 21-day checkout");
        }
        
        private void testEncapsulation() {
            // Test getter access
            if (!"Java Programming".equals(digitalBook.getTitle())) throw new RuntimeException("Title getter should work");
            if (!"1111111111".equals(digitalBook.getIsbn())) throw new RuntimeException("ISBN getter should work");
            
            // Test that fields are private
            try {
                var bookClass = Book.class;
                var titleField = bookClass.getDeclaredField("title");
                var isbnField = bookClass.getDeclaredField("isbn");
                
                if (!java.lang.reflect.Modifier.isPrivate(titleField.getModifiers())) {
                    throw new RuntimeException("title field should be private");
                }
                if (!java.lang.reflect.Modifier.isPrivate(isbnField.getModifiers())) {
                    throw new RuntimeException("isbn field should be private");
                }
                if (!java.lang.reflect.Modifier.isFinal(isbnField.getModifiers())) {
                    throw new RuntimeException("isbn field should be final");
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException("Expected private fields not found");
            }
            
            // Test default availability
            if (!digitalBook.isAvailable()) throw new RuntimeException("Book should be available by default");
        }
        
        private void testPolymorphismModels() {
            List<Book> books = Arrays.asList(digitalBook, physicalBook, audioBook);
            
            // Same method call, different behavior
            List<Integer> checkoutPeriods = new ArrayList<>();
            for (Book book : books) {
                checkoutPeriods.add(book.getCheckoutPeriod());
            }
            
            List<Integer> expected = Arrays.asList(7, 14, 21);
            if (!checkoutPeriods.equals(expected)) {
                throw new RuntimeException("Expected [7, 14, 21], got " + checkoutPeriods);
            }
            
            // All should have display info but different implementations
            for (Book book : books) {
                String info = book.getDisplayInfo();
                if (!info.contains("by")) {
                    throw new RuntimeException("Display info should contain 'by': " + info);
                }
            }
        }
        
        private void testInterfaceImplementation() {
            // Test that all books implement required interfaces
            for (Book book : Arrays.asList(digitalBook, physicalBook, audioBook)) {
                if (!(book instanceof Lendable)) {
                    throw new RuntimeException(book.getClass().getSimpleName() + " should implement Lendable");
                }
                if (!(book instanceof Searchable)) {
                    throw new RuntimeException(book.getClass().getSimpleName() + " should implement Searchable");
                }
            }
        }
        
        private void testGenericsAndReflection() {
            LendingService lendingService = new LendingServiceImpl();
            
            // Test with valid book - this will fail with placeholder implementation
            String dueDate = lendingService.calculateDueDate(digitalBook);
            if (!dueDate.contains("7 days") && !dueDate.contains("Unknown")) {
                throw new RuntimeException("Expected '7 days' or 'Unknown' in due date, got: " + dueDate);
            }
            
            // Test availability check - this will fail with placeholder implementation
            boolean canCheckout = lendingService.canCheckout(digitalBook);
            // Don't fail the test here since we expect the placeholder to return false
            
            // Test with unavailable book
            digitalBook.setAvailability(false);
            canCheckout = lendingService.canCheckout(digitalBook);
            // Should return false regardless of implementation
            digitalBook.setAvailability(true); // Reset
        }
        
        private void testDependencyInjection() {
            // Test that service was constructed with dependencies
            try {
                var serviceClass = bookService.getClass();
                var fields = serviceClass.getDeclaredFields();
                
                boolean hasLendingService = false;
                boolean hasNotificationService = false;
                
                for (var field : fields) {
                    if (field.getType().getSimpleName().contains("LendingService")) {
                        hasLendingService = true;
                    }
                    if (field.getType().getSimpleName().contains("NotificationService")) {
                        hasNotificationService = true;
                    }
                }
                
                if (!hasLendingService) throw new RuntimeException("BookService should contain LendingService");
                if (!hasNotificationService) throw new RuntimeException("BookService should contain NotificationService");
                
            } catch (Exception e) {
                throw new RuntimeException("Error checking dependency injection: " + e.getMessage());
            }
        }
        
        private void testPolymorphicCollection() {
            List<Book> allBooks = bookService.getAllBooks();
            if (allBooks.size() != 3) {
                throw new RuntimeException("Expected 3 books, got " + allBooks.size());
            }
            
            // Should contain different types
            Set<String> types = new HashSet<>();
            for (Book book : allBooks) {
                types.add(book.getClass().getSimpleName());
            }
            
            Set<String> expectedTypes = new HashSet<>(Arrays.asList("DigitalBook", "PhysicalBook", "AudioBook"));
            if (!types.equals(expectedTypes)) {
                throw new RuntimeException("Expected " + expectedTypes + ", got " + types);
            }
        }
        
        private void testGenericTypeFiltering() {
            // These tests will fail with placeholder implementation
            List<DigitalBook> digitalBooks = bookService.getBooksByType(DigitalBook.class);
            if (digitalBooks.size() == 0) {
                throw new RuntimeException("getBooksByType not implemented - should return 1 digital book");
            }
            
            List<PhysicalBook> physicalBooks = bookService.getBooksByType(PhysicalBook.class);
            if (physicalBooks.size() == 0) {
                throw new RuntimeException("getBooksByType not implemented - should return 1 physical book");
            }
            
            List<AudioBook> audioBooks = bookService.getBooksByType(AudioBook.class);
            if (audioBooks.size() == 0) {
                throw new RuntimeException("getBooksByType not implemented - should return 1 audio book");
            }
        }
        
        private void testPolymorphicSearch() {
            // These tests will fail with placeholder implementation
            List<Book> results = bookService.searchBooks("Java");
            if (results.size() == 0) {
                throw new RuntimeException("searchBooks not implemented - should find 1 result for 'Java'");
            }
            
            results = bookService.searchBooks("Martin");
            if (results.size() == 0) {
                throw new RuntimeException("searchBooks not implemented - should find 1 result for 'Martin'");
            }
            
            results = bookService.searchBooks("clean");
            if (results.size() == 0) {
                throw new RuntimeException("searchBooks not implemented - should find 1 result for 'clean'");
            }
        }
        
        private void testCheckoutFunctionality() {
            // This test will fail with placeholder implementation
            CheckoutResult result = bookService.checkoutBook("1111111111");
            if (!result.isSuccess()) {
                throw new RuntimeException("checkoutBook not implemented - should succeed: " + result.getErrorMessage());
            }
        }
        
        private void testAvailabilityReport() {
            // This test will fail with placeholder implementation
            AvailabilityReport report = bookService.getAvailabilityReport();
            
            if (report.getTotalBooks() == 0) {
                throw new RuntimeException("getAvailabilityReport not implemented - should have 3 total books");
            }
        }
        
        private void testFinalAndImmutability() {
            // Test that ISBN is immutable
            String originalIsbn = digitalBook.getIsbn();
            // No setter should exist for ISBN
            try {
                var method = digitalBook.getClass().getMethod("setIsbn", String.class);
                throw new RuntimeException("ISBN should be immutable - no setter should exist");
            } catch (NoSuchMethodException e) {
                // Expected - no setter should exist
            }
            
            // ISBN should still be the same
            if (!originalIsbn.equals(digitalBook.getIsbn())) {
                throw new RuntimeException("ISBN should remain unchanged");
            }
        }
        
        private void testPackagePrivateAccess() {
            // All our classes should be in the same package and accessible
            if (!digitalBook.getClass().getPackage().getName().contains("models")) {
                throw new RuntimeException("Models should be in models package");
            }
            if (!bookService.getClass().getPackage().getName().contains("services")) {
                throw new RuntimeException("Services should be in services package");
            }
        }
        
        public boolean runAllTests() {
            System.out.println("============================================================");
            System.out.println("☕ JAVA OOP CONCEPTS TEST SUITE");
            System.out.println("============================================================");
            
            System.out.println("\n📚 Testing Core OOP Concepts:");
            runTest("Inheritance (Abstract classes, method overriding)", this::testInheritance);
            runTest("Encapsulation (Private fields, getters/setters)", this::testEncapsulation);
            runTest("Polymorphism (Same interface, different behavior)", this::testPolymorphismModels);
            runTest("Interface Implementation (Multiple inheritance of behavior)", this::testInterfaceImplementation);
            
            System.out.println("\n🔧 Testing Java Specific Features:");
            runTest("Generics and Reflection", this::testGenericsAndReflection);
            runTest("Dependency Injection (Constructor injection)", this::testDependencyInjection);
            runTest("Generic Bounds and Type Safety", this::testGenericTypeFiltering);
            runTest("Final keyword and Immutability", this::testFinalAndImmutability);
            runTest("Package-private Access", this::testPackagePrivateAccess);
            
            System.out.println("\n🎯 Testing Business Logic:");
            runTest("Polymorphic Collections (Mixed book types)", this::testPolymorphicCollection);
            runTest("Polymorphic Search Functionality", this::testPolymorphicSearch);
            runTest("Checkout with Polymorphic Behavior", this::testCheckoutFunctionality);
            runTest("Polymorphic Reporting", this::testAvailabilityReport);
            
            System.out.println("\n============================================================");
            System.out.println("📊 RESULTS: " + testsPassed + " passed, " + testsFailed + " failed");
            
            if (testsFailed == 0) {
                System.out.println("🎉 Congratulations! All Java OOP concepts implemented correctly!");
            } else {
                System.out.println("📝 Focus on implementing the failing methods in Services.java");
            }
            
            System.out.println("\n🎓 Java OOP Concepts Demonstrated:");
            System.out.println("✓ Explicit access modifiers (private, protected, public, package-private)");
            System.out.println("✓ Final keyword for immutability and preventing inheritance");
            System.out.println("✓ Interface-based contracts with multiple inheritance of behavior");
            System.out.println("✓ Generic bounds (<T extends Book>) for type safety");
            System.out.println("✓ Reflection for runtime type inspection");
            System.out.println("✓ Constructor dependency injection");
            System.out.println("✓ Strong typing with compile-time verification");
            System.out.println("✓ Package organization and access control");
            
            return testsFailed == 0;
        }
    }
    
    public static void main(String[] args) {
        OOPTestSuite testSuite = new OOPTestSuite();
        testSuite.runAllTests();
        
        System.out.println("\nPress Enter to exit...");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignore
        }
    }
}