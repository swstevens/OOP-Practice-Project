"""
Service classes demonstrating composition, dependency injection, and polymorphism.
Student needs to implement the BookService class methods.
"""

from typing import List, Optional, Union, Any
from models import Book, DigitalBook, PhysicalBook, AudioBook, Lendable

TYPE_MAPPING = {
    DigitalBook: 'digital',
    PhysicalBook: 'physical',
    AudioBook: 'audio'
}


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
        checkout = getattr(item, 'get_checkout_period', None)
        
        if checkout and callable(checkout):
            return str(checkout()) + " days"
        else:
            # Default behavior if method doesn't exist
            return ""
    
    @staticmethod
    def can_checkout(item: Any) -> bool:
        """
        Duck typing example - works with any object having is_available method
        TODO: Student should implement this method
        """
        # Default implementation that should fail tests
        checkout = getattr(item, 'is_available', None)
        
        if checkout and callable(checkout):
            return checkout()
        else:
            # Default behavior if method doesn't exist
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
        self._books.append(book)
    
    def get_all_books(self) -> List[Book]:
        """
        Get all books - demonstrates returning polymorphic collection
        TODO: Student should implement this method
        """
        return self._books
    
    def get_books_by_type(self, book_type: str) -> List[Book]:
        """
        Filter books by type - demonstrates type checking
        TODO: Student should implement this method
        book_type can be: "digital", "physical", "audio"
        """
        output = []

        for book in self._books:
            if TYPE_MAPPING.get(type(book), "") == book_type:
                output.append(book)
        return output
    
    def get_book_by_isbn(self, isbn: str) -> Optional[Book]:
        """
        Find book by ISBN
        TODO: Student should implement this method
        """
        for book in self._books:
            if book.isbn == isbn:
                return book
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
        book = self.get_book_by_isbn(isbn)
        if not book:
            return {"success": False, "error": "ISBN not found"}
        if not getattr(book,'is_available',False):
            return {"success": False, "error": "Book Not available"}
        
        book.is_available = False
        return {
            "success": True,
            "message": "Book checked out successfully",
            "book_title": book.title,
            "due_date": f"Due in {book.get_checkout_period()} days",
            "checkout_period": book.get_checkout_period(),
            "notification": "notification message"
        }
    
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
        output = {
            "total_books": 0,
            "available_books": 0,
            "checked_out_books": 0,
            "by_type": {
                "digital": {"total": 0, "available": 0},
                "physical": {"total": 0, "available": 0},
                "audio": {"total": 0, "available": 0}
            }
        }
        sumval = 0
        for book in self._books:
            type = ''
            match book.__class__.__name__:
                case 'DigitalBook':
                    type = 'digital'
                case 'PhysicalBook':
                    type = 'physical'
                case 'AudioBook':
                    type = 'audio'
            if type:
                output['by_type'][type]['total'] += 1
                output['by_type'][type]['available'] += 1 if book.is_available else 0
            output['total_books'] += 1
            output['available_books'] += 1 if book.is_available else 0
            sumval += book.get_checkout_period()
        output['average_checkout_period'] = sumval/output['total_books']
        return output
    
    def search_books(self, query: str) -> List[Book]:
        """
        Search books by title or author
        TODO: Student should implement this method
        Case-insensitive search in title and author fields
        """
        print(query)
        output = []
        for book in self._books:
            print(book.title,query)
            if query.lower() in book.title.lower() or query.lower() in book.author.lower():
                output.append(book)
        return output