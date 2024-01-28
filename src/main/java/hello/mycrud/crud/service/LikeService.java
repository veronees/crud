package hello.mycrud.crud.service;

import hello.mycrud.crud.domain.entity.Like;
import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.repository.LikeRepository;
import hello.mycrud.crud.repository.PostRepository;
import hello.mycrud.crud.repository.UserRepository;
import hello.mycrud.crud.repository.jparepository.LikeJpaRepository;
import hello.mycrud.crud.repository.jparepository.PostJpaRepository;
import hello.mycrud.crud.repository.jparepository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeJpaRepository likeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void likePost(Long postId, Long userId) {
        Post post = postJpaRepository.findOne(postId);
        User user = userJpaRepository.findOne(userId);

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        post.updateLikeCount();
        likeJpaRepository.save(like);
    }

    public void unlikePost(Long postId, Long userId) {
        Post post = postJpaRepository.findOne(postId);
        User user = userJpaRepository.findOne(userId);

        Like like = likeJpaRepository.findByPostAndUser(post, user);
        post.downLikeCount();
        likeJpaRepository.delete(like);

    }

    public void likePostV2(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 몾찾음"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 못찾음"));

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        post.updateLikeCount();
        likeRepository.save(like);
    }

    public void unlikePostV2(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글 못찾음"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저 못찾음"));

        Like like = likeRepository.findByPostIdAndUserId(post, user);
        post.downLikeCount();
        likeRepository.delete(like);
    }
}