package emt223287.service.application;


import emt223287.dto.CreateBookDto;
import emt223287.dto.DisplayBookDto;
import emt223287.model.views.BooksPerAuthorView;

import java.util.List;
import java.util.Optional;

public interface BookApplicationService {
    List<DisplayBookDto> getAllBooks();

    DisplayBookDto getBookById(Long id);

    DisplayBookDto addBook(CreateBookDto createBookDto);

    DisplayBookDto editBook(Long id, CreateBookDto createBookDto);

    void deleteBook(Long id);

    void markBookAsTaken(Long id);

    Optional<BooksPerAuthorView> getBooksCountByAuthor(Long authorId);


}
