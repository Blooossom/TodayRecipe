package com.example.todayrecipe.service;


import com.example.todayrecipe.dto.comment.CommentReqDTO;
import com.example.todayrecipe.dto.comment.CommentResDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {

    ResponseEntity<?> viewCommentList(Long postNo);

    ResponseEntity<?> viewMyComment(String accessToken);

    ResponseEntity<?> addComment(CommentReqDTO.WriteReq writeReq, String accessToken);

    ResponseEntity<?> deleteComment(Long commentNo, String accessToken);

    ResponseEntity<?> updateComment(CommentReqDTO.UpdateReq reqDTO);


}
