package hello.mycrud.crud.repository.jparepository;

import hello.mycrud.crud.domain.entity.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostJpaRepository {

    private final EntityManager em;

    //post 게시글 db에 저장
    public void save(Post post) {
        em.persist(post);
    }

    //게시글 post 한개 조회
    public Post findOne(Long id) {
        return em.find(Post.class, id);
    }

    //전체 post 조회
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    //title 제목으로 조회
    public List<Post> findByTitle(String title) {
        return em.createQuery("select p from Post p where p.title = :title", Post.class)
                .setParameter("title", title)
                .getResultList();
    }

    //게시글 삭제
    public void deletePost(Long id) {
        Post findPost = em.find(Post.class, id);
        em.remove(findPost);
    }

    public List<Post> findByTitleContainingOrContentContaining(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return Collections.emptyList(); // 빈 리스트 반환 또는 다른 적절한 처리 수행
        }

        List<Post> keyword2 = em.createQuery("SELECT p FROM Post p WHERE p.title LIKE CONCAT('%', :keyword, '%') OR p.content LIKE CONCAT('%', :keyword, '%')", Post.class)
                .setParameter("keyword", keyword)
                .getResultList();

        return keyword2;
    }

    //한 페이지당 10개씩 게시글 보여줄거임
    public List<Post> findAllByPage(int page) {
        return em.createQuery("select p from Post p order by p.id desc", Post.class)
                .setFirstResult((page - 1) * 10) //1번 페이지면 0번째부터,, 2번 페이지면 10번째부터......
                .setMaxResults(10)
                .getResultList();
    }


}
