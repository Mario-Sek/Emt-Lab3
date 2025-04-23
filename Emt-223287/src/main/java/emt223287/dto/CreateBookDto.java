package emt223287.dto;

import emt223287.model.domain.Author;
import emt223287.model.domain.Book;
import emt223287.model.enums.Category;

public record CreateBookDto(String name, Category category, Long author, int availableCopies) {

    public Book toBook(Author author) {
        return new Book(name, category, author, availableCopies);
    }
}
