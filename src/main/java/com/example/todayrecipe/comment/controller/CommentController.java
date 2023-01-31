package com.example.todayrecipe.comment.controller;

import com.example.todayrecipe.comment.dto.CommentRequest;
import com.example.todayrecipe.comment.dto.CommentResponse;
import com.example.todayrecipe.comment.repository.CommentRepository;
import com.example.todayrecipe.comment.service.CommentService;
import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Api(tags = {"댓글 컨트롤러"}, description = "댓글 기본 CRUD, 비밀댓글, 작성자 및 게시글 작성자만 확인가능 등")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;


    @ApiOperation(value = "댓글 출력", notes = "게시글과 함께 댓글을 가져오는 API")
    @ApiImplicitParam(name = "post_id", value = "게시글 식별 ID", required = true)
    @GetMapping("/viewComment")
    public List<CommentResponse> selectComment(HttpSession session){
        Long post_id = Long.valueOf(String.valueOf(session.getAttribute("post_id")));
        return  service.viewCommentList(post_id);
    }

    @GetMapping("/goMyCommentList")
    public List<CommentResponse> selectMyCommentList(HttpSession session) {
        String userid = session.getAttribute("userid").toString();
        return service.viewMyComment(userid);
    }


    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성하는 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "댓글 내용", required = true),
            @ApiImplicitParam(name = "writer", value = "댓글 작성자", required = true),
            @ApiImplicitParam(name = "user_id", value = "유저 식별 ID", required = true),
            @ApiImplicitParam(name = "post_id", value = "게시글 식별 ID", required = true)
    })
    @PostMapping("/addComment")
    public String addComment(CommentRequest request, Long postid, HttpSession session) {
        String userid = String.valueOf(session.getAttribute("userid"));
        System.out.println(request.getContent());
        System.out.println(userid);
        return service.addComment(request, postid, userid);
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글 수정하는 API")
    @PutMapping("/updateComment")
    public String modifyComment(Long commentId, CommentRequest request){
        return service.modifyComment(commentId, request);
    }
    @Transactional
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제하는 API")
    @DeleteMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable("id") Long commentId){
        return service.deleteComment(commentId);
    }
}
