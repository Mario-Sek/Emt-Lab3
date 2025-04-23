package emt223287.web;

import emt223287.dto.CreateCountryDto;
import emt223287.dto.DisplayCountryDto;
import emt223287.service.application.CountryApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
@Tag(name = "Country API", description = "Endpoints for managing countries")
@CrossOrigin
public class CountryController {
    private final CountryApplicationService countryApplicationService;

    public CountryController(CountryApplicationService countryApplicationService) {
        this.countryApplicationService = countryApplicationService;
    }

    @Operation(summary = "Get all countries", description = "Retrieves a list of all available countries.")
    @GetMapping
    public List<DisplayCountryDto> getAllCountries() {
        return countryApplicationService.getAllCountries();
    }

    @Operation(summary = "Get country by ID", description = "Finds a country by its ID.")
    @GetMapping("/{id}")
    public ResponseEntity<DisplayCountryDto> getCountryById(@PathVariable Long id) {
        DisplayCountryDto country = countryApplicationService.getCountryById(id);
        return country != null ? ResponseEntity.ok(country) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Add a new country", description = "Creates a new country.")
    @PostMapping("/add")
    public ResponseEntity<DisplayCountryDto> addCountry(@RequestBody CreateCountryDto countryDto) {
        DisplayCountryDto newCountry = countryApplicationService.addCountry(countryDto);
        return newCountry != null ? ResponseEntity.ok(newCountry) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Update an existing country", description = "Updates a country by ID.")
    @PutMapping("/edit/{id}")
    public ResponseEntity<DisplayCountryDto> editCountry(@PathVariable Long id, @RequestBody CreateCountryDto countryDto) {
        DisplayCountryDto updatedCountry = countryApplicationService.editCountry(id, countryDto);
        return updatedCountry != null ? ResponseEntity.ok(updatedCountry) : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Delete a country", description = "Deletes a country by ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id) {
        DisplayCountryDto country = countryApplicationService.getCountryById(id);
        if (country == null) {
            return ResponseEntity.notFound().build();
        }
        countryApplicationService.deleteCountry(id);
        return ResponseEntity.ok().build();
    }
}
