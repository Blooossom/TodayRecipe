package com.example.todayrecipe.post.controller;


import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.dto.PostResponse;
import com.example.todayrecipe.post.dto.PostUpdate;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping("/recommendRecipeList")
    public List<PostResponse> viewRecommendRecipe() {
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
    public String writePost(PostRequest request, HttpSession session) {
        String user_id = String.valueOf(session.getAttribute("userid"));
        return service.addPost(request, user_id);
    }

    @Transactional
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제하는 API")
    @DeleteMapping("/deletePost")
    public String deletePost(@RequestParam String id){
        System.out.println(id);
        return service.deletePost(Long.valueOf(id));
    }

    @GetMapping("/selectPost")
    public PostResponse selectPost(HttpSession session){
        Long postId = Long.valueOf(String.valueOf(session.getAttribute("post_id")));
        service.updateView(postId);
        return service.viewPost(postId);
    }

    @Transactional
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정하는 API")
    @PutMapping("/updatePost/{id}")
    public String updatePost(@PathVariable String id, PostRequest request, Model model) {
        try{
            System.out.println(id);
            System.out.println(request.toString());
            service.updatePost(request, id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @PutMapping("/recommendRecipe")
    public void recommend(Long postid) {
        System.out.println(postid);
        service.updateRecommend(postid);
    }

}
