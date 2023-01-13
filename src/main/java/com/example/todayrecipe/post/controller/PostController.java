package com.example.todayrecipe.post.controller;


import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.dto.PostResponse;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.post.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @ApiOperation(value = "게시글 전체 조회", notes = "게시글 전부 가져오는 API")
    @GetMapping("/viewPostList")
    public List<PostResponse> viewPostList(){
       return service.selectPostList();
    }

    @ApiOperation(value = "게시글 검색", notes = "키워드에 따라 게시글을 검색하는 API")
    @GetMapping("/post/search?keyword={keyword}")
    public List<PostResponse> viewPostListByNickName(@PathVariable String nickname) {
        return null;
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성하는 API")
    @PostMapping("/addRecipe")
    public String writePost(PostRequest post, HttpSession session) {
        Long user_id = Long.valueOf((String) session.getAttribute("id"));
        return service.addPost(post, user_id);
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제하는 API")
    @DeleteMapping("/post")
    public String deletePost(Long post_id){
        return service.deletePost(post_id);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정하는 API")
    @PutMapping("/updatePost")
    public String modifiedPost(PostRequest request, Long post_id){
        return service.updatePost(post_id, request);
    }

    @ApiOperation(value = "게시글 보기", notes = "클릭을 통해 post id를 받고, 해당하는 post를 리턴함")
    @GetMapping("/viewPost")
    public String viewPost(@RequestParam final Long id, Model model) {
        PostResponse response = service.viewPost(id);
        service.viewPost(id);
        model.addAttribute("post", response);
        return "/post";
    }
}
