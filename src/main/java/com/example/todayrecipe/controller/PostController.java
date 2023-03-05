package com.example.todayrecipe.controller;


import com.example.todayrecipe.dto.post.PostRequest;
import com.example.todayrecipe.dto.post.PostResponse;
import com.example.todayrecipe.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping("/recommendRecipeList")
    public List<PostResponse> viewRecommendRecipe() {
        System.out.println(service.selectRecommendList());
        return service.selectRecommendList();
    }

    @GetMapping("/goMyPostList")
    public List<PostResponse> viewPostListByUser(HttpSession session){
        String userId = (session.getAttribute("userid").toString());
        return service.selectPostListByUserid(userId);
    }


    @ApiOperation(value = "게시글 전체 조회", notes = "게시글 전부 가져오는 API")
    @GetMapping("/viewPostList")
    public List<PostResponse> viewPostList(HttpSession session){
        session.setAttribute("post_id", null);
        return service.selectPostList();
    }

    @ApiOperation(value = "게시글 검색", notes = "키워드에 따라 게시글을 검색하는 API")
    @GetMapping("/post/search?keyword={keyword}")
    public List<PostResponse> viewPostListByNickName(HttpSession session) {
        session.setAttribute("post_id", null);
        return null;
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성하는 API")
    @PostMapping("/addRecipe")
    public ResponseEntity<String>writePost(PostRequest request, HttpSession session) {
        String user_id = String.valueOf(session.getAttribute("userid"));
        String message = service.addPost(request, user_id);
        return new ResponseEntity<>(message, message.equals("success")? HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }


    @Transactional
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제하는 API")
    @DeleteMapping("/deletePost")
    public ResponseEntity<String> deletePost(@RequestParam String id, HttpSession session){
        String userId = String.valueOf(session.getAttribute("userid"));
        String message = service.deletePost(Long.valueOf(id), userId);
        return new ResponseEntity<>(message, message.equals("success")? HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/selectPost")
    public ResponseEntity<PostResponse> selectPost(HttpSession session){
        Long postId = Long.valueOf(String.valueOf(session.getAttribute("post_id")));
        service.updateView(postId);
        PostResponse response = service.viewPost(postId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정하는 API")
    @PutMapping("/updatePost")
    public ResponseEntity<String> updatePost(PostRequest request, HttpSession session) {
        String userId = String.valueOf(session.getAttribute("userId"));
        String message = service.updatePost(request, userId);
        return new ResponseEntity<>(message, message.equals("success")? HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/recommendRecipe")
    public void recommend(Long postid) {
        System.out.println(postid);
        service.updateRecommend(postid);
    }

}
