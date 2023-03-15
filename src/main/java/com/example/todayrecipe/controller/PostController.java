package com.example.todayrecipe.controller;


import com.example.todayrecipe.dto.post.PostReqDTO;
import com.example.todayrecipe.dto.post.PostResDTO;
import com.example.todayrecipe.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    /**
     * 추천 게시글 조회
     */
    @GetMapping("/recommendlist")
    public ResponseEntity<?> viewRecommendRecipe() {
        return service.selectRecommendList();
    }

    @GetMapping("/redisRecommend")
    public ResponseEntity<?> redisResponse(){
        return service.recommendListRedis();
    }
    /**
     * 내가 작성한 게시글 목록 출력
     */
    @GetMapping("/mypage/postlist")
    public ResponseEntity<?> viewPostListByUser(@RequestHeader("Authorization") String accessToekn){
        return service.selectPostListByEmail(accessToekn);
    }

    /**
     * 게시글 전체 조회
     */
    @ApiOperation(value = "게시글 전체 조회", notes = "게시글 전부 가져오는 API")
    @GetMapping("/viewPostList")
    public ResponseEntity<?> viewPostList(){
        return service.selectPostList();
    }

    /**
     * 게시글 검색
     */
    @ApiOperation(value = "게시글 검색", notes = "키워드에 따라 게시글을 검색하는 API")
    @GetMapping("/post/search?keyword={keyword}")
    public List<PostResDTO> search(@RequestParam(value = "keyword") String keyword){
        return null;
    }

    /**
     * 게시글 작성
     */
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성하는 API")
    @PostMapping("/post")
    public ResponseEntity<?> writePost(@RequestHeader("Authorization") String accessToken, @RequestBody PostReqDTO.WritePost request) {
        return service.addPost(request, accessToken);
    }

    /**
     * 게시글 삭제
     */
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제하는 API")
    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@RequestParam(name = "postNo")Long postNo, @RequestHeader("Authorization")String accessToken){
        return service.deletePost(postNo, accessToken);
    }

    /**
     * 게시글 이동
     */
    @GetMapping("/selectPost")
    public ResponseEntity<?> selectPost(@RequestParam(name = "postNo") Long postNo){
        return service.viewPost(postNo);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정하는 API")
    @PutMapping("/post")
    public ResponseEntity<?> updatePost(@RequestHeader("Authorization") String accessToken, @RequestBody PostReqDTO.UpdatePost updateReq) {
        return service.updatePost(updateReq, accessToken);
    }

    /**
     * 추천 수 상승
     */
    @PutMapping("/recommend")
    public ResponseEntity<?> recommend(@RequestBody HashMap<String, Object> map) {
        return service.updateRecommend(map);
    }
}
