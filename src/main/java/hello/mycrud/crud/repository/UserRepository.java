package hello.mycrud.crud.repository;

import hello.mycrud.crud.domain.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u from User u")
    @EntityGraph(attributePaths = {"posts"})
    List<User> findUserAll();
}
