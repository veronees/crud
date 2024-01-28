package hello.mycrud.crud.service;

import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.domain.requestdto.PostRequestDto;
import hello.mycrud.crud.domain.responsedto.PostResponseDto;
import hello.mycrud.crud.repository.PostRepository;
import hello.mycrud.crud.repository.UserRepository;
import hello.mycrud.crud.repository.jparepository.PostJpaRepository;
import hello.mycrud.crud.repository.jparepository.UserJpaRepository;
import hello.mycrud.crud.util.PostEntityDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostJpaRepository postJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostEntityDtoConverter postEntityDtoConverter;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

//    //게시글 db에 저장
//    @Transactional
//    public void createPost(PostRequestDto postRequestDto) {
//        User user = userJpaRepository.findOne(postRequestDto.getUserId());
//        Post post = postEntityDtoConverter.convertToEntity(postRequestDto, user);
//        postJpaRepository.save(post);
//    }

    @Transactional
    public void createPostV2(PostRequestDto postRequestDto) {
        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("유저x"));
        Post post = postEntityDtoConverter.convertToEntity(postRequestDto, user);
        postRepository.save(post);
    }

    @Transactional
    public void deletePostV2(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    public PostResponseDto findPostByIdV2(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글 x"));
        User user = post.getUser();
        Long userId = user.getId();
        post.updateViewCount();

        PostResponseDto postResponseDto = PostResponseDto.builder()
                .userId(userId)
                .title(post.getTitle())
                .content(post.getContent())
                .viewCount(post.getViewCount())
                .likeCount(post.getLikeCount())
                .commentCount(post.countComments())
                .build();

        return postResponseDto;
    }

    public List<Post> getAllPostsV2() {
        return postRepository.findAll();
    }

    @Transactional
    public void modifiedPostV2(Long postId, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 x"));
        post.updateTitle(title);
        post.updateContent(content);
        post.updateLastModifiedDate();
    }

//    //게시글 삭제
//    @Transactional
//    public void deletePost(Long postId) {
//        postJpaRepository.deletePost(postId);
//
//    }

//    //ID로 게시글 조회
//    public PostResponseDto findPostById(Long postId) {
//        Post findPost = postJpaRepository.findOne(postId);
//        User user = findPost.getUser();
//        Long userId = user.getId();
//        findPost.updateViewCount();
//
//        PostResponseDto build = PostResponseDto.builder()
//                .userId(userId)
//                .title(findPost.getTitle())
//                .content(findPost.getContent())
//                .viewCount(findPost.getViewCount())
//                .likeCount(findPost.getLikeCount())
//                .commentCount(findPost.countComments())
//                .build();
//
//        return build;
//    }



//    //게시글 리스트 조회
//    public List<Post> getAllPosts() {
//        return postJpaRepository.findAll();
//    }


//    //게시글 수정
//    @Transactional
//    public void modifiedPost(Long postId, String title, String content) {
//        //postId로 해당 게시글을 리포지토리에서 찾음.
//        Post findPost = postJpaRepository.findOne(postId);
//
//        //찾은 게시글의 정보를 파라미터로 받은 제목과 내용으로 변경, 수정시간 업데이트해주고.
//        findPost.updateTitle(title);
//        findPost.updateContent(content);
//        findPost.updateLastModifiedDate();
//    }

//    // 제목 또는 내용에서 키워드 검색
//    public List<PostResponseDto> searchPosts(String keyword) {
//        List<Post> byTitleContainingOrContentContaining = postJpaRepository.findByTitleContainingOrContentContaining(keyword);
//
//        List<PostResponseDto> postResponseDtos = new ArrayList<>();
//        for (Post post : byTitleContainingOrContentContaining) {
//            PostResponseDto postResponseDto = PostResponseDto.builder()
//                    .userId(post.getUser().getId())
//                    .title(post.getTitle())
//                    .content(post.getContent())
//                    .viewCount(post.getViewCount())
//                    .likeCount(post.getLikeCount())
//                    .commentCount(post.getComments().size())
//                    .build();
//            postResponseDtos.add(postResponseDto);
//        }
//        return postResponseDtos;
//    }

    //한 페이지당 10개 게시글로 잡았고, 해당 페이지의 게시글들 조회(POST_ID높은순(즉 가장 최근에 작성된 글)으로 10개씩 1페이지, 2페이지 이렇게 쭉쭉)
    public List<PostResponseDto> findPostByPageNum(int pageNum) {
        List<Post> allByPage = postJpaRepository.findAllByPage(pageNum);
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        for (Post post : allByPage) {
            PostResponseDto dto = PostResponseDto.builder()
                    .userId(post.getUser().getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .viewCount(post.getViewCount())
                    .likeCount(post.getLikeCount())
                    .commentCount(post.getComments().size())
                    .build();
            postResponseDtos.add(dto);
        }
        return postResponseDtos;
    }










    //뷰에서 페이지 번호 1~10 이런식으로 밑에 뛰우는거에 필요한 메서드인데 지금 당장 필요 없음
    private int[] pageList() {
        int totalPage = countPost() / 10; //게시글 총 100개이면 10으로 나눳을 때 몫인 10 즉 페이지 갯수 10개
        totalPage = (countPost() % 10 == 0) ? totalPage : totalPage + 1;

        int[] pages = new int[totalPage];
        for(int i = 0; i < totalPage; i++) {
            pages[i] = i + 1;
        }

        return pages;
    }

    //전체 게시글 개수
    private int countPost() {
        return postJpaRepository.findAll().size();
    }
}
