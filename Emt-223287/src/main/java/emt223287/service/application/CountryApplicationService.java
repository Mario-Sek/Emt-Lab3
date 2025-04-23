package emt223287.service.application;



import emt223287.dto.CreateCountryDto;
import emt223287.dto.DisplayCountryDto;

import java.util.List;

public interface CountryApplicationService {
    List<DisplayCountryDto> getAllCountries();

    DisplayCountryDto getCountryById(Long country);

    DisplayCountryDto addCountry(CreateCountryDto createCountryDto);

    DisplayCountryDto editCountry(Long id, CreateCountryDto createCountryDto);

    void deleteCountry(Long id);
}
