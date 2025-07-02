"""
Service classes demonstrating composition, dependency injection, and polymorphism.
Student needs to implement the BookService class methods.
"""

from typing import List, Optional, Union, Any
from models import Book, DigitalBook, PhysicalBook, AudioBook, Lendable


class LendingService:
    """
    Service demonstrating duck typing and composition.
    Uses duck typing - works with any object that has the right methods.
    """
    
    @staticmethod
    def calculate_due_date(item: Any) -> str:
        """
        Duck typing example - works with any object having get_checkout_period method
        TODO: Student should implement this method
        """
        # Default implementation that should fail tests
        return "Not implemented"
    
    @staticmethod
    def can_checkout(item: Any) -> bool:
        """
        Duck typing example - works with any object having is_available method
        TODO: Student should implement this method
        """
        # Default implementation that should fail tests
        return False


class NotificationService:
    """Simple notification service for demonstration"""
    
    def send_checkout_notification(self, book_title: str, due_date: str) -> str:
        return f"Notification: '{book_title}' checked out. {due_date}"


class BookService:
    """
    Main service class demonstrating:
    - Dependency injection through constructor
    - Composition (contains other services)
    - Polymorphic behavior
    - Encapsulation of business logic
    """
    
    def __init__(self, lending_service: LendingService, notification_service: NotificationService):
        """
        Constructor demonstrating dependency injection
        TODO: Student should implement proper initialization
        """
        # Default implementation - should be replaced
        self._books: List[Book] = []
        self._lending_service = None
        self._notification_service = None
    
    def add_book(self, book: Book) -> None:
        """
        Add a book to the collection
        TODO: Student should implement this method
        """
        pass
    
    def get_all_books(self) -> List[Book]:
        """
        Get all books - demonstrates returning polymorphic collection
        TODO: Student should implement this method
        """
        return []
    
    def get_books_by_type(self, book_type: str) -> List[Book]:
        """
        Filter books by type - demonstrates type checking
        TODO: Student should implement this method
        book_type can be: "digital", "physical", "audio"
        """
        return []
    
    def get_book_by_isbn(self, isbn: str) -> Optional[Book]:
        """
        Find book by ISBN
        TODO: Student should implement this method
        """
        return None
    
    def checkout_book(self, isbn: str) -> dict:
        """
        Checkout a book - demonstrates polymorphic behavior
        TODO: Student should implement this method
        
        Should return:
        Success: {
            "success": True,
            "message": "Book checked out successfully",
            "book_title": "Title",
            "due_date": "Due in X days",
            "checkout_period": X,
            "notification": "notification message"
        }
        
        Failure: {
            "success": False,
            "error": "Error message"
        }
        """
        return {"success": False, "error": "Not implemented"}
    
    def get_availability_report(self) -> dict:
        """
        Generate report showing polymorphic behavior
        TODO: Student should implement this method
        
        Should return: {
            "total_books": X,
            "available_books": X,
            "checked_out_books": X,
            "by_type": {
                "digital": {"total": X, "available": X},
                "physical": {"total": X, "available": X},
                "audio": {"total": X, "available": X}
            },
            "average_checkout_period": X.X
        }
        """
        return {}
    
    def search_books(self, query: str) -> List[Book]:
        """
        Search books by title or author
        TODO: Student should implement this method
        Case-insensitive search in title and author fields
        """
        return []