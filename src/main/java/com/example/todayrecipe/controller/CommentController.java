package com.example.todayrecipe.controller;

import com.example.todayrecipe.dto.comment.CommentReqDTO;
import com.example.todayrecipe.dto.comment.CommentResDTO;
import com.example.todayrecipe.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"댓글 컨트롤러"}, description = "댓글 기본 CRUD, 비밀댓글, 작성자 및 게시글 작성자만 확인가능 등")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    /**
     * 게시글이 호출되었을 때 그 게시글의 댓글 목록 출력
     */
    @ApiOperation(value = "댓글 출력", notes = "게시글과 함께 댓글을 가져오는 API")
    @ApiImplicitParam(name = "post_id", value = "게시글 식별 ID", required = true)
    @GetMapping("/viewComment")
    public ResponseEntity<?> selectComment(@RequestParam(name = "postNo") Long postNo){
        return service.viewCommentList(postNo);
    }

    /**
     * 내가 작성한 댓글 조회
     */
    @GetMapping("/mypage/commentlist")
    public ResponseEntity<?> selectMyCommentList(@RequestHeader("Authorization") String accessToken) {
        return service.viewMyComment(accessToken);
    }

    /**
     * 댓글 작성
     */
    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성하는 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content", value = "댓글 내용", required = true),
            @ApiImplicitParam(name = "writer", value = "댓글 작성자", required = true),
            @ApiImplicitParam(name = "user_id", value = "유저 식별 ID", required = true),
            @ApiImplicitParam(name = "post_id", value = "게시글 식별 ID", required = true)
    })
    @PostMapping("/comment")
    public ResponseEntity<?> addComment(@RequestHeader("Authorization") String accessToken, @RequestBody CommentReqDTO.WriteReq request) {
        return service.addComment(request, accessToken);
    }

    /**
     * 댓글 수정
     */
    @ApiOperation(value = "댓글 수정", notes = "댓글 수정하는 API")
    @PutMapping("/comment")
    public ResponseEntity<?> modifyComment(@RequestBody CommentReqDTO.UpdateReq reqDTO){
        return service.updateComment(reqDTO);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제하는 API")
    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestParam(name = "commentNo") Long commentNo, @RequestHeader("Authorization") String accessToken){
        return service.deleteComment(commentNo, accessToken);
    }
}
