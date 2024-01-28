package hello.mycrud.crud.controller;

import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.requestdto.PostRequestDto;
import hello.mycrud.crud.domain.responsedto.PostResponseDto;
import hello.mycrud.crud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    //게시글 작성
    @PostMapping
    public ResponseEntity<String> createPostV2(@RequestBody PostRequestDto postRequestDto) {
        postService.createPostV2(postRequestDto);
        return new ResponseEntity<>("게시글 작성 완료", HttpStatus.OK);
    }

    //게시글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> findPostV2(@PathVariable Long postId) {
        PostResponseDto findPost = postService.findPostByIdV2(postId);
        return ResponseEntity.ok(findPost);
    }

    //게시글 수정
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePostV2(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        postService.modifiedPostV2(postId, postRequestDto.getTitle(), postRequestDto.getContent());
        return new ResponseEntity<>("게시글 수정 완료", HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePostV2(@PathVariable Long postId) {
        postService.deletePostV2(postId);
        return new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
    }

    //페이징 --> 해당 파라미터값 페이지마다 10개씩 게시글 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPostByPage(@RequestParam int pageNum) {
        List<PostResponseDto> postsByPageNum = postService.findPostByPageNum(pageNum);

        return ResponseEntity.ok(postsByPageNum);
    }
}





/**
 * 순수 Jpa 로직
 */
//    //게시글 작성
//    @PostMapping
//    public ResponseEntity<String> createPost(@RequestBody PostRequestDto postRequestDto) {
//        postService.createPost(postRequestDto);
//        return new ResponseEntity<>("게시글 작성 완료", HttpStatus.OK);
//    }

//    //게시글 조회
//    @GetMapping("/{postId}")
//    public ResponseEntity<PostResponseDto> findPost(@PathVariable Long postId) {
//        PostResponseDto findPost = postService.findPostById(postId);
//        return ResponseEntity.ok(findPost);
//    }

//    //게시글 수정
//    @PutMapping("/{postId}")
//    public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
//        postService.modifiedPost(postId, postRequestDto.getTitle(), postRequestDto.getContent());
//        return new ResponseEntity<>("게시글 수정 완료", HttpStatus.OK);
//    }

//    //게시글 삭제
//    @DeleteMapping("/{postId}")
//    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
//        postService.deletePost(postId);
//        return new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
//    }

/*    // 특정 키워드로 게시글 검색 //다다다다다다다다ㅣ시시시시시ㅣ시 만들어야야야됌
    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDto>> searchPosts(@RequestParam String keyword) {
        List<PostResponseDto> postResponseDtos = postService.searchPosts(keyword);
        return ResponseEntity.ok(postResponseDtos);
    }*/


