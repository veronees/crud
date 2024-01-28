package hello.mycrud.crud.repository.jparepository;

import hello.mycrud.crud.domain.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserJpaRepository {

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

    //username으로 조회, 회원가입 시 아이디 중복체크
    public Optional<User> findByUsername(String username) {
        try {
            User user = em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            return Optional.ofNullable(user);
        }catch (NoResultException e) {
            return Optional.empty();
        }
    }



    //유저 삭제
    public void delete(Long userId) {
        User user = em.find(User.class, userId);
        em.remove(user);
    }

}
