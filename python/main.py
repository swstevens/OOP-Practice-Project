"""
Main test file demonstrating OOP concepts and testing student implementation.
Run this file to see which tests pass/fail.
"""

from models import Book, DigitalBook, PhysicalBook, AudioBook
from service import BookService, LendingService, NotificationService


class OOPTestSuite:
    """Test suite for OOP concepts"""
    
    def __init__(self):
        self.tests_passed = 0
        self.tests_failed = 0
        self.setup_test_data()
    
    def setup_test_data(self):
        """Create test data"""
        # Create services
        self.lending_service = LendingService()
        self.notification_service = NotificationService()
        self.book_service = BookService(self.lending_service, self.notification_service)
        
        # Create sample books
        self.digital_book = DigitalBook("Python OOP", "Jane Developer", "1111111111", "pdf")
        self.physical_book = PhysicalBook("Clean Code", "Robert Martin", "2222222222", "Section A", "Excellent")
        self.audio_book = AudioBook("Design Patterns", "Gang of Four", "3333333333", "John Narrator", 480)
        
        # Add books to service
        self.book_service.add_book(self.digital_book)
        self.book_service.add_book(self.physical_book)
        self.book_service.add_book(self.audio_book)
    
    def run_test(self, test_name: str, test_func):
        """Run a single test and track results"""
        try:
            test_func()
            print(f"✅ {test_name}")
            self.tests_passed += 1
        except Exception as e:
            print(f"❌ {test_name}: {str(e)}")
            self.tests_failed += 1
    
    def test_inheritance(self):
        """Test inheritance concepts"""
        # Test that subclasses inherit from Book
        assert isinstance(self.digital_book, Book), "DigitalBook should inherit from Book"
        assert isinstance(self.physical_book, Book), "PhysicalBook should inherit from Book"
        assert isinstance(self.audio_book, Book), "AudioBook should inherit from Book"
        
        # Test that abstract method is implemented differently
        assert self.digital_book.get_checkout_period() == 7, "Digital book should have 7-day checkout"
        assert self.physical_book.get_checkout_period() == 14, "Physical book should have 14-day checkout"
        assert self.audio_book.get_checkout_period() == 21, "Audio book should have 21-day checkout"
    
    def test_encapsulation(self):
        """Test encapsulation concepts"""
        # Test property access
        assert self.digital_book.title == "Python OOP", "Title property should work"
        assert self.digital_book.isbn == "1111111111", "ISBN property should access private field"
        
        # Test property validation
        try:
            self.digital_book.title = ""
            assert False, "Should not allow empty title"
        except ValueError:
            pass  # Expected
        
        # Test that private field is name-mangled
        assert hasattr(self.digital_book, '_Book__isbn'), "Private field should be name-mangled"
    
    def test_polymorphism_models(self):
        """Test polymorphism in models"""
        books = [self.digital_book, self.physical_book, self.audio_book]
        
        # Same method call, different behavior
        checkout_periods = [book.get_checkout_period() for book in books]
        assert checkout_periods == [7, 14, 21], f"Expected [7, 14, 21], got {checkout_periods}"
        
        # All should have display info
        for book in books:
            info = book.get_display_info()
            assert "by" in info, f"Display info should contain 'by': {info}"
    
    def test_duck_typing_lending_service(self):
        """Test duck typing in LendingService"""
        # Test with valid book
        due_date = self.lending_service.calculate_due_date(self.digital_book)
        assert "7 days" in due_date, f"Expected '7 days' in due date, got: {due_date}"
        
        # Test availability check
        can_checkout = self.lending_service.can_checkout(self.digital_book)
        assert can_checkout == True, "Available book should be checkable"
        
        # Test with unavailable book
        self.digital_book.set_availability(False)
        can_checkout = self.lending_service.can_checkout(self.digital_book)
        assert can_checkout == False, "Unavailable book should not be checkable"
        self.digital_book.set_availability(True)  # Reset
    
    def test_composition_book_service(self):
        """Test composition in BookService"""
        # Test that service contains other services
        assert hasattr(self.book_service, '_lending_service'), "BookService should contain LendingService"
        assert hasattr(self.book_service, '_notification_service'), "BookService should contain NotificationService"
    
    def test_polymorphic_collection(self):
        """Test polymorphic collections"""
        all_books = self.book_service.get_all_books()
        assert len(all_books) == 3, f"Expected 3 books, got {len(all_books)}"
        
        # Should contain different types
        types = [type(book).__name__ for book in all_books]
        expected_types = ['DigitalBook', 'PhysicalBook', 'AudioBook']
        for expected_type in expected_types:
            assert expected_type in types, f"Missing {expected_type} in collection"
    
    def test_type_filtering(self):
        """Test type-based filtering"""
        digital_books = self.book_service.get_books_by_type("digital")
        assert len(digital_books) == 1, f"Expected 1 digital book, got {len(digital_books)}"
        assert isinstance(digital_books[0], DigitalBook), "Should return DigitalBook instance"
        
        physical_books = self.book_service.get_books_by_type("physical")
        assert len(physical_books) == 1, f"Expected 1 physical book, got {len(physical_books)}"
        
        audio_books = self.book_service.get_books_by_type("audio")
        assert len(audio_books) == 1, f"Expected 1 audio book, got {len(audio_books)}"
    
    def test_book_search(self):
        """Test search functionality"""
        # Search by title
        results = self.book_service.search_books("python")
        assert len(results) == 1, f"Expected 1 result for 'python', got {len(results)}"
        
        # Search by author
        results = self.book_service.search_books("martin")
        assert len(results) == 1, f"Expected 1 result for 'martin', got {len(results)}"
        
        # Case insensitive
        results = self.book_service.search_books("CLEAN")
        assert len(results) == 1, f"Expected 1 result for 'CLEAN', got {len(results)}"
    
    def test_checkout_functionality(self):
        """Test checkout with polymorphic behavior"""
        # Checkout digital book
        result = self.book_service.checkout_book("1111111111")
        assert result["success"] == True, f"Checkout should succeed: {result}"
        assert "7 days" in result["due_date"], f"Should mention 7 days: {result['due_date']}"
        assert result["checkout_period"] == 7, f"Should have 7-day period: {result}"
        
        # Try to checkout same book again
        result = self.book_service.checkout_book("1111111111")
        assert result["success"] == False, f"Second checkout should fail: {result}"
        assert "not available" in result["error"].lower(), f"Should mention availability: {result}"
    
    def test_availability_report(self):
        """Test polymorphic reporting"""
        report = self.book_service.get_availability_report()
        
        assert report["total_books"] == 3, f"Should have 3 total books: {report}"
        assert "by_type" in report, "Report should include breakdown by type"
        assert "digital" in report["by_type"], "Should include digital books"
        assert "physical" in report["by_type"], "Should include physical books"
        assert "audio" in report["by_type"], "Should include audio books"
        
        # Check average checkout period calculation
        expected_avg = (7 + 14 + 21) / 3  # 14.0
        assert abs(report["average_checkout_period"] - expected_avg) < 0.1, \
            f"Expected avg ~{expected_avg}, got {report['average_checkout_period']}"
    
    def run_all_tests(self):
        """Run all tests and display results"""
        print("=" * 60)
        print("🐍 PYTHON OOP CONCEPTS TEST SUITE")
        print("=" * 60)
        
        print("\n📚 Testing Core OOP Concepts:")
        self.run_test("Inheritance (Abstract classes, method overriding)", self.test_inheritance)
        self.run_test("Encapsulation (Properties, private fields)", self.test_encapsulation)
        self.run_test("Polymorphism (Same interface, different behavior)", self.test_polymorphism_models)
        
        print("\n🔧 Testing Service Layer Concepts:")
        self.run_test("Duck Typing (LendingService methods)", self.test_duck_typing_lending_service)
        self.run_test("Composition (BookService contains other services)", self.test_composition_book_service)
        self.run_test("Polymorphic Collections (Mixed book types)", self.test_polymorphic_collection)
        
        print("\n🎯 Testing Business Logic:")
        self.run_test("Type-based Filtering", self.test_type_filtering)
        self.run_test("Search Functionality", self.test_book_search)
        self.run_test("Checkout with Polymorphic Behavior", self.test_checkout_functionality)
        self.run_test("Polymorphic Reporting", self.test_availability_report)
        
        print("\n" + "=" * 60)
        print(f"📊 RESULTS: {self.tests_passed} passed, {self.tests_failed} failed")
        
        if self.tests_failed == 0:
            print("🎉 Congratulations! All Python OOP concepts implemented correctly!")
        else:
            print("📝 Focus on implementing the failing methods in service.py")
            
        print("\n🎓 Python OOP Concepts Demonstrated:")
        print("✓ Convention-based access control (_private, __name_mangling)")
        print("✓ Property decorators (@property, @setter)")
        print("✓ Duck typing and protocols")
        print("✓ Abstract base classes (ABC)")
        print("✓ Multiple inheritance support")
        print("✓ Dynamic typing with runtime checks")
        
        return self.tests_failed == 0


if __name__ == "__main__":
    test_suite = OOPTestSuite()
    test_suite.run_all_tests()