package emt223287.dto;

import emt223287.model.domain.Wishlist;
import java.util.List;

public record WishlistDto(
        Long id,
        DisplayUserDto user,
        List<DisplayBookDto> books
) {

    public static WishlistDto from(Wishlist wishlist) {
        return new WishlistDto(
                wishlist.getId(),
                DisplayUserDto.from(wishlist.getUser()),
                DisplayBookDto.from(wishlist.getBooks())
        );
    }
}
