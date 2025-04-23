package emt223287.repository;

import emt223287.model.domain.Author;
import emt223287.projections.AuthorNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a.name as name, a.surname as surname from Author a")
    List<AuthorNameProjection> findAllAuthorNames();
}
