package emt223287.service.domain;

import emt223287.model.domain.Book;
import emt223287.model.views.BooksPerAuthorView;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book addBook(Book book);

    Book editBook(Long id, Book book);

    void deleteBook(Long id);

    void markBookAsTaken(Long id);

    Optional<BooksPerAuthorView> getBooksCountByAuthor(Long authorId);


    void refreshMaterializedView();

}
