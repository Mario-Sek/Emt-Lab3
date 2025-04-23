package emt223287.model.views;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "authors_by_country")
public class AuthorsByCountryView {

    @Id
    private String countryName;
    private int numAuthors;
}
