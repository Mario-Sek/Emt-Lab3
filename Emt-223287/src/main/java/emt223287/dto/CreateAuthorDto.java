package emt223287.dto;

import emt223287.model.domain.Author;
import emt223287.model.domain.Country;

public record CreateAuthorDto(String name, String surname, Long country) {

public Author toAuthor(Country country) {
    return new Author(name, surname, country);
}

}
