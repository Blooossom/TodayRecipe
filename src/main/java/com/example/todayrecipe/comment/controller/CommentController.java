package com.example.todayrecipe.comment.controller;

import com.example.todayrecipe.comment.dto.CommentRequest;
import com.example.todayrecipe.comment.repository.CommentRepository;
import com.example.todayrecipe.post.dto.PostRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"댓글 컨트롤러"}, description = "댓글 기본 CRUD, 비밀댓글, 작성자 및 게시글 작성자만 확인가능 등")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository repo;


    @ApiOperation(value = "댓글 출력", notes = "게시글과 함께 댓글을 가져오는 API")
    @ApiImplicitParam(name = "PostRequest", value = "게시글 정보 요청, 통해서 PostID에 연결된 댓글 가져옴", required = true)
    @GetMapping("/comment")
    public String selectComment(PostRequest request){


        return null;
    }

    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성하는 API")
    @PostMapping("/comment")
    public String addComment(CommentRequest request) {
        return null;
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글 수정하는 API")
    @PutMapping("/comment")
    public String modifyComment(String commentId, CommentRequest request){
        return null;
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제하는 API")
    @DeleteMapping("/comment")
    public String deleteComment(String commentId){
        return null;
    }







}
