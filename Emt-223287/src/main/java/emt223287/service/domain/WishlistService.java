package emt223287.service.domain;

import emt223287.model.domain.Book;
import emt223287.model.domain.Wishlist;

import java.util.List;

public interface WishlistService {

    List<Book> listAllBooksInWishlist(Long wishlistId);

    Wishlist getActiveWishlist(String username);

    Wishlist addBookToWishlist(String username, Long bookId);

    void rentAllBooksFromWishlist(String username);
}
