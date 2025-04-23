package emt223287.repository;

import emt223287.model.views.BooksPerAuthorView;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BooksPerAuthorViewRepository extends JpaRepository<BooksPerAuthorView, Long> {

    @Transactional
    @Modifying
    @Query(value = "REFRESH MATERIALIZED VIEW CONCURRENTLY public.books_by_author", nativeQuery = true)
    void refreshMaterializedView();

    Optional<BooksPerAuthorView> findByAuthorId(Long authorId);
}
