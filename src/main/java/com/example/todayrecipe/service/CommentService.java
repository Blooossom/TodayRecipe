package com.example.todayrecipe.service;


import com.example.todayrecipe.dto.comment.CommentReqDTO;
import com.example.todayrecipe.dto.comment.CommentResDTO;
import com.example.todayrecipe.dto.comment.UpdateCommentReqDTO;
import com.example.todayrecipe.dto.user.LoginReqDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface CommentService {

    List<CommentResDTO> viewCommentList(HashMap<String, Object> map);

    List<CommentResDTO> viewMyComment(LoginReqDTO user);

    ResponseEntity<String> addComment(CommentReqDTO commentReqDTO, LoginReqDTO loginReqDTO);

    ResponseEntity<String> deleteComment(HashMap<String, Object> commentMap, LoginReqDTO loginReqDTO);

    ResponseEntity<String> updateComment(UpdateCommentReqDTO reqDTO, HashMap<String, Object> map);


}
