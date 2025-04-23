package emt223287.jobs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MaterializedViewService {

    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(cron = "0 0 * * * *") // Every hour
    public void refreshBooksByAuthorView() {
        entityManager.createNativeQuery("REFRESH MATERIALIZED VIEW CONCURRENTLY  books_by_author").executeUpdate();
    }
}


