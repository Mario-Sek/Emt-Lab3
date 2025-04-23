package emt223287.web;

import emt223287.dto.CreateAuthorDto;
import emt223287.dto.DisplayAuthorDto;
import emt223287.model.views.AuthorsByCountryView;
import emt223287.projections.AuthorNameProjection;
import emt223287.repository.AuthorsByCountryViewRepository;
import emt223287.service.application.AuthorApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@Tag(name = "Author API", description = "Endpoints for managing authors")
@CrossOrigin
public class AuthorController {
    private final AuthorApplicationService authorApplicationService;
    private final AuthorsByCountryViewRepository viewRepository;

    public AuthorController(AuthorApplicationService authorApplicationService, AuthorsByCountryViewRepository viewRepository) {
        this.authorApplicationService = authorApplicationService;
        this.viewRepository = viewRepository;
    }

    @Operation(summary = "Get all authors", description = "Retrieves a list of all available authors.")
    @GetMapping
    public List<DisplayAuthorDto> listAuthors() {
        return authorApplicationService.getAllAuthors();
    }

    @Operation(summary = "Get author by ID", description = "Finds an author by their ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DisplayAuthorDto> getAuthorById(@PathVariable Long id) {
        DisplayAuthorDto author = authorApplicationService.getAuthorById(id);
        return author != null ? ResponseEntity.ok(author) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Add a new author", description = "Creates a new author.")
    @PostMapping("/add")
    public ResponseEntity<DisplayAuthorDto> addAuthor(@RequestBody CreateAuthorDto authorDto) {
        DisplayAuthorDto newAuthor = authorApplicationService.addAuthor(authorDto);
        return newAuthor != null ? ResponseEntity.ok(newAuthor) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Update an existing author", description = "Updates an author by ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayAuthorDto> editAuthor(@PathVariable Long id, @RequestBody CreateAuthorDto authorDto) {
        DisplayAuthorDto updatedAuthor = authorApplicationService.editAuthor(id, authorDto);
        return updatedAuthor != null ? ResponseEntity.ok(updatedAuthor) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Delete an author", description = "Deletes an author by ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        DisplayAuthorDto author = authorApplicationService.getAuthorById(id);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        authorApplicationService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-country")
    public List<AuthorsByCountryView> getAuthorsByCountry() {
        return viewRepository.findAll();
    }

    @GetMapping("/names")
    public List<AuthorNameProjection> getAuthorNames() {
        return authorApplicationService.getAuthorNames();
    }

}
