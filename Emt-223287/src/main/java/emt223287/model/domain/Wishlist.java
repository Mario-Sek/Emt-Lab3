package emt223287.model.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)  // Ensures one-to-one mapping
    private User user;

    @ManyToMany
    @JoinTable(
            name = "wishlist_books",  // Join table name
            joinColumns = @JoinColumn(name = "wishlist_id"),  // Column for this entity's ID
            inverseJoinColumns = @JoinColumn(name = "book_id")  // Column for the related entity's ID
    )
    private List<Book> books = new ArrayList<>();

    public Wishlist() {
    }

    public Wishlist(User user) {
        this.user = user;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        if (!this.books.contains(book)) {  // Prevent duplicate books
            this.books.add(book);
        }
    }

    public void clearBooks() {
        this.books.clear();
    }
}
