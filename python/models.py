"""
Book models demonstrating Python OOP concepts:
- Inheritance with abstract base classes
- Encapsulation using naming conventions
- Property decorators for getters/setters
- Multiple inheritance capabilities
"""

from abc import ABC, abstractmethod
from typing import Protocol


class Lendable(Protocol):
    """Protocol (interface) for lendable items - demonstrates duck typing"""
    def is_available(self) -> bool: ...
    def get_checkout_period(self) -> int: ...


class Book(ABC):
    """Abstract base class demonstrating inheritance and encapsulation"""
    
    def __init__(self, title: str, author: str, isbn: str):
        self._title = title              # Protected by convention
        self._author = author            # Protected by convention
        self.__isbn = isbn               # Private with name mangling
        self._is_available = True        # Protected
    
    @property
    def title(self) -> str:
        """Public property for title"""
        return self._title
    
    @title.setter
    def title(self, value: str):
        """Property setter with validation"""
        if not value or not value.strip():
            raise ValueError("Title cannot be empty")
        self._title = value.strip()
    
    @property
    def author(self) -> str:
        return self._author
    
    @property
    def isbn(self) -> str:
        """Access to private field through property"""
        return self.__isbn
    
    def is_available(self) -> bool:
        return self._is_available
    
    def set_availability(self, available: bool):
        self._is_available = available
    
    @abstractmethod
    def get_checkout_period(self) -> int:
        """Abstract method - must be implemented by subclasses"""
        pass
    
    def get_display_info(self) -> str:
        """Concrete method available to all subclasses"""
        return f"'{self.title}' by {self.author}"

class DigitalBook(Book):
    """Digital book implementation demonstrating inheritance"""
    
    def __init__(self, title: str, author: str, isbn: str, file_format: str):
        super().__init__(title, author, isbn)
        self.file_format = file_format
        self.download_url = f"/download/{isbn}.{file_format}"
    
    def get_checkout_period(self) -> int:
        """Implementation of abstract method"""
        return 7  # Digital books: 1 week
    
    def get_download_link(self) -> str:
        """Method specific to digital books"""
        return self.download_url


class PhysicalBook(Book):
    """Physical book implementation demonstrating inheritance"""
    
    def __init__(self, title: str, author: str, isbn: str, location: str, condition: str = "Good"):
        super().__init__(title, author, isbn)
        self.location = location
        self.condition = condition
    
    def get_checkout_period(self) -> int:
        """Different implementation of abstract method"""
        return 14  # Physical books: 2 weeks


class AudioBook(Book):
    """Audio book demonstrating multiple inheritance concepts"""
    
    def __init__(self, title: str, author: str, isbn: str, narrator: str, duration_minutes: int):
        super().__init__(title, author, isbn)
        self.narrator = narrator
        self.duration_minutes = duration_minutes
    
    def get_checkout_period(self) -> int:
        return 21  # Audio books: 3 weeks
    
    def get_duration_hours(self) -> float:
        return self.duration_minutes / 60.0
