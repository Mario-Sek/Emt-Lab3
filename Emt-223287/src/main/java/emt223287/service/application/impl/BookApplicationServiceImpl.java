package emt223287.service.application.impl;

import emt223287.dto.CreateBookDto;
import emt223287.dto.DisplayBookDto;
import emt223287.model.domain.Author;
import emt223287.model.domain.Book;
import emt223287.model.views.BooksPerAuthorView;
import emt223287.service.application.BookApplicationService;
import emt223287.service.domain.AuthorService;
import emt223287.service.domain.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookApplicationServiceImpl implements BookApplicationService {
    private final BookService bookService;
    private final AuthorService authorService;

    public BookApplicationServiceImpl(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Override
    public List<DisplayBookDto> getAllBooks() {
        return DisplayBookDto.from(bookService.getAllBooks());
    }

    @Override
    public DisplayBookDto getBookById(Long id) {
        Book book = bookService.getBookById(id);
        return book != null ? DisplayBookDto.from(book) : null;
    }

    @Override
    public DisplayBookDto addBook(CreateBookDto createBookDto) {
        Author author = authorService.getAuthorById(createBookDto.author());
        if (author == null) {
            throw new IllegalArgumentException("Invalid author ID");
        }

        Book book = createBookDto.toBook(author);
        Book savedBook = bookService.addBook(book);
        return DisplayBookDto.from(savedBook);
    }

    @Override
    public DisplayBookDto editBook(Long id, CreateBookDto createBookDto) {
        Book existingBook = bookService.getBookById(id);
        if (existingBook == null) {
            return null;
        }

        Author author = authorService.getAuthorById(createBookDto.author());
        if (author == null) {
            throw new IllegalArgumentException("Invalid author ID");
        }

        existingBook.setName(createBookDto.name());
        existingBook.setCategory(createBookDto.category());
        existingBook.setAuthor(author);
        existingBook.setAvailableCopies(createBookDto.availableCopies());

        Book updatedBook = bookService.editBook(id, existingBook);
        return DisplayBookDto.from(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        bookService.deleteBook(id);
    }

    @Override
    public void markBookAsTaken(Long id) {
        bookService.markBookAsTaken(id);
    }

    @Override
    public Optional<BooksPerAuthorView> getBooksCountByAuthor(Long authorId) {
        return bookService.getBooksCountByAuthor(authorId);
    }

}
