package emt223287.model.domain;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Entity
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String continent;

    public Country( String name, String continent) {
        this.name = name;
        this.continent = continent;
    }
}

