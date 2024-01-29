package hello.mycrud.crud.repository;

import hello.mycrud.crud.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying(clearAutomatically = true)
    @Query("delete from Comment c where c.post.id = :postId")
    void deleteCommentByPostId(@Param("postId") Long postId);

    //특정 게시글의 특정 댓글 조회

    Comment findByPostIdAndId(Long postId, Long commentId);
}
