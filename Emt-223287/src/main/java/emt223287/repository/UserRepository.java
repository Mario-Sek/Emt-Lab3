package emt223287.repository;

import emt223287.model.domain.User;
import emt223287.model.enums.Role;
import emt223287.projections.UserProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);


    @Override
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {}
    )
    List<User> findAll();


    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {"wishlist"}
    )
    @Query("select u from User u")
    List<User> findAllWithWishlist();

    UserProjection findByRole(Role role);

    @Query("select u.username as username, u.name as name, u.surname as surname from User u")
    List<UserProjection> takeUsernameAndNameAndSurnameByProjection();
}
