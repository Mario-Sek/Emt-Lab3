package emt223287.service.domain;

import emt223287.model.domain.Author;
import emt223287.projections.AuthorNameProjection;

import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();

    Author getAuthorById(Long id);

    Author addAuthor(Author author);

    Author editAuthor(Long id, Author author);

    void deleteAuthor(Long id);

    List<AuthorNameProjection> getAuthorNames();

}
