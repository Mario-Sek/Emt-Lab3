package emt223287.events;

import emt223287.model.domain.Author;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AuthorChangedEvent extends ApplicationEvent {
    public AuthorChangedEvent(Author author) {
        super(author);
    }

    public Author getAuthor() {
        return (Author) getSource();
    }
}
