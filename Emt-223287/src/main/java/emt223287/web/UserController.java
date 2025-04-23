package emt223287.web;

import emt223287.dto.CreateUserDto;
import emt223287.dto.DisplayUserDto;
import emt223287.dto.LoginResponseDto;
import emt223287.dto.LoginUserDto;
import emt223287.security.JwtHelper;
import emt223287.service.application.UserApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User API", description = "Endpoints for user authentication and registration")
public class UserController {

    private final UserApplicationService userApplicationService;
    private final JwtHelper jwtHelper;

    public UserController(UserApplicationService userApplicationService, JwtHelper jwtHelper) {
        this.userApplicationService = userApplicationService;
        this.jwtHelper = jwtHelper;
    }

    @Operation(summary = "Register a new user", description = "Creates a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or passwords do not match")
    })
    @PostMapping("/register")
    public ResponseEntity<DisplayUserDto> register(@RequestBody CreateUserDto createUserDto) {
        try {
            return userApplicationService.register(createUserDto)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new IllegalArgumentException("User could not be created"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .header("Error-Message", e.getMessage())
                    .build();
        }
    }

    @Operation(summary = "User login", description = "Authenticates a user and generates a JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "404", description = "Invalid username or password")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            return userApplicationService.login(loginUserDto)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> new IllegalArgumentException("Login failed: Invalid username or password."));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

}
