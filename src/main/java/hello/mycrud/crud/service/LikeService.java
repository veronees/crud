package hello.mycrud.crud.service;

import hello.mycrud.crud.domain.entity.Like;
import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.repository.LikeRepository;
import hello.mycrud.crud.repository.PostRepository;
import hello.mycrud.crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public void likePost(Long postId, Long userId) {
        Post post = postRepository.findOne(postId);
        User user = userRepository.findOne(userId);

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        post.updateLikeCount();
        likeRepository.save(like);
    }

    public void unlikePost(Long postId, Long userId) {
        Post post = postRepository.findOne(postId);
        User user = userRepository.findOne(userId);

        Like like = likeRepository.findByPostAndUser(post, user);
        post.downLikeCount();
        likeRepository.delete(like);

    }
}