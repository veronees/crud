package hello.mycrud.crud.repository;

import hello.mycrud.crud.domain.entity.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findOne(Long commentId) {
        Comment comment = em.find(Comment.class, commentId);
        return comment;
    }

    public void delete(Long commentId) {
        Comment comment = em.find(Comment.class, commentId);
        em.remove(comment);
    }

    //특정 게시글의 특정 댓글 조회
    public Comment findByPostIdAndCommentId(Long postId, Long commentId) {
        Comment result = em.createQuery("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.id = :commentId", Comment.class)
                .setParameter("postId", postId)
                .setParameter("commentId", commentId)
                .getSingleResult();
        return result;
    }
}
