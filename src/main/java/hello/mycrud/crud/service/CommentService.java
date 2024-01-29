package hello.mycrud.crud.service;

import hello.mycrud.crud.domain.entity.Comment;
import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.domain.requestdto.CommentRequestDto;
import hello.mycrud.crud.domain.responsedto.CommentResponseDto;
import hello.mycrud.crud.repository.CommentRepository;
import hello.mycrud.crud.repository.PostRepository;
import hello.mycrud.crud.repository.UserRepository;
import hello.mycrud.crud.repository.jparepository.CommentJpaRepository;
import hello.mycrud.crud.repository.jparepository.PostJpaRepository;
import hello.mycrud.crud.repository.jparepository.UserJpaRepository;
import hello.mycrud.crud.util.CommentEntityDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentJpaRepository commentJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final CommentEntityDtoConverter commentEntityDtoConverter;

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     *  순수 JPA를 사용한 로직
     */

    //댓글 등록
    public void createComment(Long postId, Long parentCommentId, CommentRequestDto commentRequestDto) {
        Post post = postJpaRepository.findOne(postId);
        User user = userJpaRepository.findOne(commentRequestDto.getUserId());

        //null이 아닌 경우는 대댓글 컨트롤러인 경우
        if (parentCommentId != null) {
            Comment parentComment = commentJpaRepository.findOne(parentCommentId);
            Comment comment = commentEntityDtoConverter.convertToEntity(post, user, parentComment, commentRequestDto);
            commentJpaRepository.save(comment);
        }
        //null인 경우는 대댓글이 아닌 일반 댓글 작성 컨트롤러인 경우
        else {
            Comment comment = commentEntityDtoConverter.convertToEntity(post, user, null, commentRequestDto);
            commentJpaRepository.save(comment);
        }
    }

    //댓글 수정
    public void modifiedComment(Long postId, Long commentId, CommentRequestDto commentRequestDto) {
        Comment findComment = commentJpaRepository.findByPostIdAndCommentId(postId, commentId);
        String content = commentRequestDto.getContent();
        findComment.modifiedContent(content);
    }

    //댓글 삭제
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentJpaRepository.findByPostIdAndCommentId(postId, commentId);
        commentJpaRepository.delete(comment.getId());
    }

    //특정 게시글의 전체 댓글 조회
    public List<CommentResponseDto> findAllByPostId(Long postId) {
        Post post = postJpaRepository.findOne(postId);
        List<Comment> comments = post.getComments();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = commentEntityDtoConverter.convertToDto(comment);
            commentResponseDtos.add(commentResponseDto);
        }
        return commentResponseDtos;
    }

    /**
     * Spring Data JPA를 사용한 로직
     */

    public void createCommentV2(Long postId, Long parentCommentId, CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 못찾음"));
        User user = userRepository.findById(commentRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("유저 못찾음"));

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new RuntimeException("부모 댓글 못찾음"));
            Comment comment = commentEntityDtoConverter.convertToEntity(post, user, parentComment, commentRequestDto);
            commentRepository.save(comment);
        }
        else {
            Comment comment = commentEntityDtoConverter.convertToEntity(post, user, null, commentRequestDto);
            commentRepository.save(comment);
        }
    }

    public void modifiedCommentV2(Long postId, Long commentId, CommentRequestDto commentRequestDto) {
        Comment findComment = commentRepository.findByPostIdAndId(postId, commentId);
        String content = commentRequestDto.getContent();
        findComment.modifiedContent(content);
    }

    public void deleteCommentV2(Long postId, Long commentId) {
        Comment comment = commentRepository.findByPostIdAndId(postId, commentId);

        //부모 댓글이 없고, 자식 댓글이 있으면
        if (comment.getParentComment() == null && !comment.getChildComments().isEmpty()) {
            comment.deleteContent();
            return;
        }
        commentRepository.delete(comment);
    }

    public List<CommentResponseDto> findAllByPostIdV2(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글 못찾음"));
        List<Comment> comments = post.getComments();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = commentEntityDtoConverter.convertToDto(comment);
            commentResponseDtos.add(commentResponseDto);
        }
        return commentResponseDtos;
    }


}
