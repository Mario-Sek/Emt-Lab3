package emt223287.web;

import emt223287.dto.WishlistDto;
import emt223287.service.application.WishlistApplicationService;
import emt223287.model.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist API", description = "Endpoints for managing wishlist")
public class WishlistController {

    private final WishlistApplicationService wishlistApplicationService;

    public WishlistController(WishlistApplicationService wishlistApplicationService) {
        this.wishlistApplicationService = wishlistApplicationService;
    }

    @Operation(summary = "Get active wishlist", description = "Retrieves the authenticated user's current wishlist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wishlist retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    @GetMapping
    public ResponseEntity<WishlistDto> getActiveWishlist(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return wishlistApplicationService.getActiveWishlist(user.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Add a book to wishlist", description = "Adds a book to the authenticated user's wishlist by book ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Book added to wishlist successfully"),
            @ApiResponse(responseCode = "400", description = "Book not found or out of stock"),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    @PostMapping("/add-book/{id}")
    public ResponseEntity<WishlistDto> addBookToWishlist(@PathVariable Long id, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            return wishlistApplicationService.addBookToWishlist(user.getUsername(), id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Rent all books from wishlist", description = "Rents all available books from the authenticated user's wishlist.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All books rented successfully"),
            @ApiResponse(responseCode = "400", description = "Wishlist is empty or books cannot be rented"),
            @ApiResponse(responseCode = "404", description = "Wishlist not found")
    })
    @PostMapping("/rent-all")
    public ResponseEntity<Void> rentAllBooksFromWishlist(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            wishlistApplicationService.rentAllBooksFromWishlist(user.getUsername());
            return ResponseEntity.ok().build();
        } catch (RuntimeException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

}
