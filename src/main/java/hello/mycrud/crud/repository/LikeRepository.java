package hello.mycrud.crud.repository;

import hello.mycrud.crud.domain.entity.Like;
import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from Like l where l.post.id =:postId")
    void deleteLikeByPost(@Param("postId") Long postId);

    @Query("select l from Like l where l.post = :post and l.user = :user")
    Optional<Like> findByPostIdAndUserId(@Param("post") Post post, @Param("user")User user);


}
