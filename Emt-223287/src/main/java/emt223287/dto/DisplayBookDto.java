package emt223287.dto;


import emt223287.model.domain.Book;
import emt223287.model.enums.Category;

import java.util.List;
import java.util.stream.Collectors;

public record DisplayBookDto(Long id, String name, Category category, Long author, int availableCopies) {

public static DisplayBookDto from(Book book) {
    return new DisplayBookDto(book.getId(), book.getName(), book.getCategory(), book.getAuthor().getId(), book.getAvailableCopies());
}

public static List<DisplayBookDto> from(List<Book> books){
    return books.stream().map(DisplayBookDto::from).collect(Collectors.toList());

}


}
