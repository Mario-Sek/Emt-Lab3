package emt223287.service.application;

import emt223287.dto.DisplayBookDto;
import emt223287.dto.WishlistDto;

import java.util.List;
import java.util.Optional;

public interface WishlistApplicationService {

    List<DisplayBookDto> listAllBooksInWishlist(Long wishlistId);

    Optional<WishlistDto> getActiveWishlist(String username);

    Optional<WishlistDto> addBookToWishlist(String username, Long bookId);

    void rentAllBooksFromWishlist(String username);
}
