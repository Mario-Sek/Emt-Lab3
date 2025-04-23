package emt223287.repository;

import emt223287.model.views.AuthorsByCountryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AuthorsByCountryViewRepository extends JpaRepository<AuthorsByCountryView, String> {

    @Transactional
    @Modifying
    @Query(value = "REFRESH MATERIALIZED VIEW CONCURRENTLY authors_by_country", nativeQuery = true)
    void refreshMaterializedView();
}
