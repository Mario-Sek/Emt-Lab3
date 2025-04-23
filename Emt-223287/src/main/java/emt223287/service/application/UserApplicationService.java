package emt223287.service.application;


import emt223287.dto.CreateUserDto;
import emt223287.dto.DisplayUserDto;
import emt223287.dto.LoginResponseDto;
import emt223287.dto.LoginUserDto;
import emt223287.model.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserApplicationService {

    Optional<DisplayUserDto> register(CreateUserDto createUserDto);

    Optional<LoginResponseDto> login(LoginUserDto loginUserDto);

    Optional<DisplayUserDto> findByUsername(String username);
}

