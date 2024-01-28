package hello.mycrud.crud.repository.jparepository;

import hello.mycrud.crud.domain.entity.Like;
import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeJpaRepository {

    private final EntityManager em;

    //
    public void save(Like like) {
        em.persist(like);
    }

    public void delete(Like like) {
        em.remove(like);
    }

    public Like findByPostAndUser(Post post, User user) {
        Like result = em.createQuery("SELECT l FROM Like l WHERE l.post = :post AND l.user = :user", Like.class)
                .setParameter("post", post)
                .setParameter("user", user)
                .getSingleResult();

        return result;
    }

}
