package emt223287.service.application.impl;

import emt223287.dto.DisplayCountryDto;
import emt223287.dto.CreateCountryDto;
import emt223287.model.domain.Country;
import emt223287.service.application.CountryApplicationService;
import emt223287.service.domain.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryApplicationServiceImpl implements CountryApplicationService {
    private final CountryService countryService;

    public CountryApplicationServiceImpl(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public List<DisplayCountryDto> getAllCountries() {
        return DisplayCountryDto.from(countryService.getAllCountries());
    }

    @Override
    public DisplayCountryDto getCountryById(Long id) {
        Country country = countryService.getCountryById(id);
        return country != null ? DisplayCountryDto.from(country) : null;
    }

    @Override
    public DisplayCountryDto addCountry(CreateCountryDto createCountryDto) {
        Country country = new Country(createCountryDto.name(), createCountryDto.continent());
        Country savedCountry = countryService.addCountry(country);
        return DisplayCountryDto.from(savedCountry);
    }

    @Override
    public DisplayCountryDto editCountry(Long id, CreateCountryDto createCountryDto) {
        Country existingCountry = countryService.getCountryById(id);
        if (existingCountry == null) {
            return null;
        }

        existingCountry.setName(createCountryDto.name());
        existingCountry.setContinent(createCountryDto.continent());

        Country updatedCountry = countryService.editCountry(id, existingCountry);
        return DisplayCountryDto.from(updatedCountry);
    }

    @Override
    public void deleteCountry(Long id) {
        countryService.deleteCountry(id);
    }
}
