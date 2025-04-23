package emt223287.service.domain.impl;

import emt223287.model.domain.Book;
import emt223287.model.domain.User;
import emt223287.model.domain.Wishlist;
import emt223287.repository.WishlistRepository;
import emt223287.service.domain.BookService;
import emt223287.service.domain.UserService;
import emt223287.service.domain.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserService userService;
    private final BookService bookService;

    public WishlistServiceImpl(WishlistRepository wishlistRepository,
                               UserService userService,
                               BookService bookService) {
        this.wishlistRepository = wishlistRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public List<Book> listAllBooksInWishlist(Long wishlistId) {
        Wishlist wishlist = wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist not found"));
        return wishlist.getBooks();
    }

    @Override
    public Wishlist getActiveWishlist(String username) {
        User user = userService.findByUsername(username);
        return wishlistRepository.findByUser(user)
                .orElseGet(() -> wishlistRepository.save(new Wishlist(user)));
    }

    @Override
    public Wishlist addBookToWishlist(String username, Long bookId) {
        Wishlist wishlist = getActiveWishlist(username);
        Book book = bookService.getBookById(bookId);

        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("Book is out of stock");
        }

        wishlist.addBook(book);
        return wishlistRepository.save(wishlist);
    }

    @Override
    public void rentAllBooksFromWishlist(String username) {
        Wishlist wishlist = getActiveWishlist(username);

        for (Book book : wishlist.getBooks()) {
            if (book.getAvailableCopies() > 0) {
                bookService.markBookAsTaken(book.getId());
            } else {
                throw new IllegalStateException("Not enough copies for book: " + book.getName());
            }
        }

        wishlist.clearBooks();
        wishlistRepository.save(wishlist);
    }
}
