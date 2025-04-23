package emt223287.service.application.impl;

import emt223287.dto.CreateAuthorDto;
import emt223287.dto.DisplayAuthorDto;
import emt223287.model.domain.Author;
import emt223287.model.domain.Country;
import emt223287.projections.AuthorNameProjection;
import emt223287.service.application.AuthorApplicationService;
import emt223287.service.domain.AuthorService;
import emt223287.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorApplicationServiceImpl implements AuthorApplicationService {
    private final AuthorService authorService;
    private final CountryService countryService;

    public AuthorApplicationServiceImpl(AuthorService authorService, CountryService countryService) {
        this.authorService = authorService;
        this.countryService = countryService;
    }

    @Override
    public List<DisplayAuthorDto> getAllAuthors() {
        return DisplayAuthorDto.from(authorService.getAllAuthors());
    }

    @Override
    public DisplayAuthorDto getAuthorById(Long id) {
        Author author = authorService.getAuthorById(id);
        return author != null ? DisplayAuthorDto.from(author) : null;
    }

    @Override
    public DisplayAuthorDto addAuthor(CreateAuthorDto createAuthorDto) {
        Country country = countryService.getCountryById(createAuthorDto.country());
        if (country == null) {
            throw new IllegalArgumentException("Invalid country ID");
        }

        Author author = createAuthorDto.toAuthor(country);
        Author savedAuthor = authorService.addAuthor(author);
        return DisplayAuthorDto.from(savedAuthor);
    }

    @Override
    public DisplayAuthorDto editAuthor(Long id, CreateAuthorDto authorDto) {
        Author existingAuthor = authorService.getAuthorById(id);
        if (existingAuthor == null) {
            return null;
        }

        Country country = countryService.getCountryById(authorDto.country());
        if (country == null) {
            throw new IllegalArgumentException("Invalid country ID");
        }

        existingAuthor.setName(authorDto.name());
        existingAuthor.setSurname(authorDto.surname());
        existingAuthor.setCountry(country);

        Author updatedAuthor = authorService.editAuthor(id, existingAuthor);
        return DisplayAuthorDto.from(updatedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        authorService.deleteAuthor(id);
    }

    @Override
    public List<AuthorNameProjection> getAuthorNames() {
        return authorService.getAuthorNames();
    }

}
