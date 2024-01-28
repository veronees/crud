package hello.mycrud.crud.repository;

import hello.mycrud.crud.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    //특정 게시글의 특정 댓글 조회
    Comment findByPostIdAndId(Long postId, Long commentId);
}
