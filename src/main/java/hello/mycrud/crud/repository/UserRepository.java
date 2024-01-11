package hello.mycrud.crud.repository;

import hello.mycrud.crud.domain.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    //user 정보 db에 저장
    public void save(User user) {
        em.persist(user);
    }

    //user 한명 조회
    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    //전체 user 조회
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    //유저 nickname으로 조회
    public List<User> findByName(String nickname) {
        return em.createQuery("select u from User u where u.nickname = :nickname", User.class)
                .setParameter("nickname", nickname)
                .getResultList();
    }

    //유저 삭제
    public void delete(Long userId) {
        User user = em.find(User.class, userId);
        em.remove(user);

    }

}
