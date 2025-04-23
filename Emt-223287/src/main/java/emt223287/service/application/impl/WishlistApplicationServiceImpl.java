package emt223287.service.application.impl;

import emt223287.dto.DisplayBookDto;
import emt223287.dto.WishlistDto;
import emt223287.service.application.WishlistApplicationService;
import emt223287.service.domain.WishlistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishlistApplicationServiceImpl implements WishlistApplicationService {

    private final WishlistService wishlistService;

    public WishlistApplicationServiceImpl(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @Override
    public List<DisplayBookDto> listAllBooksInWishlist(Long wishlistId) {
        return DisplayBookDto.from(wishlistService.listAllBooksInWishlist(wishlistId));
    }

    @Override
    public Optional<WishlistDto> getActiveWishlist(String username) {
        return Optional.of(WishlistDto.from(wishlistService.getActiveWishlist(username)));
    }

    @Override
    public Optional<WishlistDto> addBookToWishlist(String username, Long bookId) {
        return Optional.of(WishlistDto.from(wishlistService.addBookToWishlist(username, bookId)));
    }

    @Override
    public void rentAllBooksFromWishlist(String username) {
        wishlistService.rentAllBooksFromWishlist(username);
    }
}
