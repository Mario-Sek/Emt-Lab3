package emt223287.service.application;


import emt223287.dto.CreateAuthorDto;
import emt223287.dto.DisplayAuthorDto;
import emt223287.projections.AuthorNameProjection;

import java.util.List;

public interface AuthorApplicationService {
    List<DisplayAuthorDto> getAllAuthors();

    DisplayAuthorDto getAuthorById(Long id);

    DisplayAuthorDto addAuthor(CreateAuthorDto createAuthorDto);

    DisplayAuthorDto editAuthor(Long id, CreateAuthorDto createAuthorDto);

    void deleteAuthor(Long id);

    List<AuthorNameProjection> getAuthorNames();

}
