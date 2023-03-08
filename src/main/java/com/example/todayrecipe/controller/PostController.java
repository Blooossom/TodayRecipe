package com.example.todayrecipe.controller;


import com.example.todayrecipe.dto.post.PostRequest;
import com.example.todayrecipe.dto.post.PostResponse;
import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    /**
     * 추천 게시글 조회
     */
    @GetMapping("/recommendRecipeList")
    public List<PostResponse> viewRecommendRecipe() {
        return service.selectRecommendList();
    }

    /**
     * 내가 작성한 게시글 목록 출력
     */
    @GetMapping("/goMyPostList")
    public List<PostResponse> viewPostListByUser(@AuthenticationPrincipal LoginReqDTO user){
        return service.selectPostListByEmail(user);
    }

    /**
     * 게시글 전체 조회
     */
    @ApiOperation(value = "게시글 전체 조회", notes = "게시글 전부 가져오는 API")
    @GetMapping("/viewPostList")
    public List<PostResponse> viewPostList(){
        return service.selectPostList();
    }

    /**
     * 게시글 검색
     */
    @ApiOperation(value = "게시글 검색", notes = "키워드에 따라 게시글을 검색하는 API")
    @GetMapping("/post/search?keyword={keyword}")
    public List<PostResponse> viewPostListByNickName(HttpSession session) {
        session.setAttribute("post_id", null);
        return null;
    }

    /**
     * 게시글 작성
     */
    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성하는 API")
    @PostMapping("/addRecipe")
    public ResponseEntity<String> writePost(@AuthenticationPrincipal LoginReqDTO user, @RequestBody PostRequest request) {
        return service.addPost(request, user);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제하는 API")
    @DeleteMapping("/deletePost")
    public ResponseEntity<String> deletePost(@RequestBody HashMap<String, Object> map, @AuthenticationPrincipal LoginReqDTO user){
        return service.deletePost(map, user);
    }

    /**
     * 게시글 이동
     */
    @GetMapping("/selectPost")
    public ResponseEntity<PostResponse> selectPost(@RequestBody HashMap<String, Object> map){
        return service.viewPost(map);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정하는 API")
    @PutMapping("/updatePost")
    public ResponseEntity<String> updatePost(@AuthenticationPrincipal LoginReqDTO user, @RequestBody PostRequest request) {
        return service.updatePost(request, user);
    }

    /**
     * 추천 수 상승
     */
    @PutMapping("/recommendRecipe")
    public void recommend(@RequestBody HashMap<String, Object> map) {
        service.updateRecommend(map);
    }

}
