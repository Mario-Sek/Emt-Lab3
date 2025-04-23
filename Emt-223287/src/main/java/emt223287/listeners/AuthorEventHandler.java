package emt223287.listeners;

import emt223287.events.AuthorChangedEvent;
import emt223287.repository.AuthorsByCountryViewRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuthorEventHandler {

    private final AuthorsByCountryViewRepository repository;

    public AuthorEventHandler(AuthorsByCountryViewRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void handleAuthorChange(AuthorChangedEvent event) {
        repository.refreshMaterializedView();
    }
}

