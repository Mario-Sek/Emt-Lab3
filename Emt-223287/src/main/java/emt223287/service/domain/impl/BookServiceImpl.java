package emt223287.service.domain.impl;

import emt223287.model.domain.Author;
import emt223287.model.domain.Book;
import emt223287.model.views.BooksPerAuthorView;
import emt223287.repository.BookRepository;

import emt223287.repository.BooksPerAuthorViewRepository;
import emt223287.service.domain.AuthorService;
import emt223287.service.domain.BookService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final BooksPerAuthorViewRepository booksPerAuthorViewRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public BookServiceImpl(
            BookRepository bookRepository,
            AuthorService authorService,
            BooksPerAuthorViewRepository booksPerAuthorViewRepository,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.booksPerAuthorViewRepository = booksPerAuthorViewRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book addBook(Book book) {
        Book saved = saveBook(book, new Book());
        refreshMaterializedView();
        return saved;
    }

    @Override
    public Book editBook(Long id, Book book) {
        Book existing = bookRepository.findById(id).orElse(null);
        if (existing == null) return null;

        Book updated = saveBook(book, existing);
        refreshMaterializedView();
        return updated;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
        refreshMaterializedView();
    }

    @Override
    public void markBookAsTaken(Long id) {
        Book b = bookRepository.findById(id).orElse(null);
        if (b == null) return;
        b.setAvailableCopies(Math.max(b.getAvailableCopies() - 1, 0));
        bookRepository.save(b);
    }

    @Override
    public Optional<BooksPerAuthorView> getBooksCountByAuthor(Long authorId) {
        return booksPerAuthorViewRepository.findByAuthorId(authorId);
    }


    @Override
    public void refreshMaterializedView() {
        booksPerAuthorViewRepository.refreshMaterializedView();
    }

    private Book saveBook(Book book, Book b) {
        Author a = authorService.getAuthorById(book.getAuthor().getId());
        if (a == null) return null;

        b.setName(book.getName().isEmpty() ? "Unnamed Book" : book.getName());
        b.setCategory(book.getCategory());
        b.setAuthor(a);
        b.setAvailableCopies(Math.max(book.getAvailableCopies(), 0));
        return bookRepository.save(b);
    }
}
