package emt223287.web;

import emt223287.dto.CreateBookDto;
import emt223287.dto.DisplayBookDto;
import emt223287.model.views.BooksPerAuthorView;
import emt223287.service.application.BookApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book API", description = "Endpoints for managing books")
@CrossOrigin
public class BookController {
    private final BookApplicationService bookApplicationService;

    public BookController(BookApplicationService bookApplicationService) {
        this.bookApplicationService = bookApplicationService;
    }

    @Operation(summary = "Get all books", description = "Retrieves a list of all available books.")
    @GetMapping
    public List<DisplayBookDto> getAllBooks() {
        return bookApplicationService.getAllBooks();
    }

    @Operation(summary = "Get book by ID", description = "Finds a book by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DisplayBookDto> getBookById(@PathVariable Long id) {
        DisplayBookDto book = bookApplicationService.getBookById(id);
        return book != null ? ResponseEntity.ok(book) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Add a new book", description = "Creates a new book.")
    @PostMapping("/add")
    public ResponseEntity<DisplayBookDto> addBook(@RequestBody CreateBookDto bookDto) {
        DisplayBookDto newBook = bookApplicationService.addBook(bookDto);
        return newBook != null ? ResponseEntity.ok(newBook) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Update an existing book", description = "Updates a book by ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayBookDto> editBook(@PathVariable Long id, @RequestBody CreateBookDto bookDto) {
        DisplayBookDto updatedBook = bookApplicationService.editBook(id, bookDto);
        return updatedBook != null ? ResponseEntity.ok(updatedBook) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Delete a book", description = "Deletes a book by ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        DisplayBookDto book = bookApplicationService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        bookApplicationService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Mark a book as taken", description = "Marks a book as taken by updating its availability.")
    @PutMapping("/mark/{id}")
    public ResponseEntity<Void> markBookAsTaken(@PathVariable Long id) {
        DisplayBookDto book = bookApplicationService.getBookById(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        bookApplicationService.markBookAsTaken(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-author/{authorId}")
    public ResponseEntity<BooksPerAuthorView> getBooksCountByAuthor(@PathVariable Long authorId) {
        return bookApplicationService.getBooksCountByAuthor(authorId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
