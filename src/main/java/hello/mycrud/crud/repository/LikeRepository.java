package hello.mycrud.crud.repository;

import hello.mycrud.crud.domain.entity.Like;
import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("select l from Like l where l.post = :post and l.user = :user")
    Like findByPostIdAndUserId(@Param("post") Post post, @Param("user")User user);
}
