package emt223287.dto;

import emt223287.model.domain.Country;

public record CreateCountryDto(String name, String continent) {
public Country toCountry() {
    return new Country(name, continent);
}

}
