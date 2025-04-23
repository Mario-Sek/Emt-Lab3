package emt223287.service.domain.impl;

import emt223287.events.AuthorChangedEvent;
import emt223287.model.domain.Author;
import emt223287.model.domain.Country;
import emt223287.projections.AuthorNameProjection;
import emt223287.repository.AuthorRepository;
import emt223287.service.domain.AuthorService;
import emt223287.service.domain.CountryService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final CountryService countryService;
    private final ApplicationEventPublisher publisher;

    public AuthorServiceImpl(AuthorRepository authorRepository, CountryService countryService, ApplicationEventPublisher publisher) {
        this.authorRepository = authorRepository;
        this.countryService = countryService;
        this.publisher = publisher;
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author addAuthor(Author author) {
        Author a = new Author();
        Author saved = saveAuthor(author, a);
        if (saved != null) {
            publisher.publishEvent(new AuthorChangedEvent(saved));
        }
        return saved;
    }

    @Override
    public Author editAuthor(Long id, Author author) {
        Author a = authorRepository.findById(id).orElse(null);
        if (a == null) return null;

        Author updated = saveAuthor(author, a);
        if (updated != null) {
            publisher.publishEvent(new AuthorChangedEvent(updated));
        }
        return updated;
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.findById(id).ifPresent(author -> {
            authorRepository.deleteById(id);
            publisher.publishEvent(new AuthorChangedEvent(author));
        });
    }

    private Author saveAuthor(Author author, Author a) {
        Country c = countryService.getCountryById(author.getCountry().getId());
        if (c == null) return null;

        a.setName(author.getName());
        a.setSurname(author.getSurname());
        a.setCountry(c);

        return authorRepository.save(a);
    }

    @Override
    public List<AuthorNameProjection> getAuthorNames() {
        return authorRepository.findAllAuthorNames();
    }

}
