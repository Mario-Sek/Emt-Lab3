package emt223287.dto;

import emt223287.model.domain.User;
import emt223287.model.enums.Role;

public record CreateUserDto(
        String username,
        String password,
        String repeatPassword,
        String name,
        String surname,
        Role role
) {


    public User toUser() {
        return new User(username, password, name, surname, role);
    }
}
