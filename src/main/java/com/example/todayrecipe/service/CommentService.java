package com.example.todayrecipe.service;


import com.example.todayrecipe.dto.comment.CommentReqDTO;
import com.example.todayrecipe.dto.comment.CommentResponse;
import com.example.todayrecipe.dto.comment.UpdateCommentReqDTO;

import java.util.List;

public interface CommentService {

    List<CommentResponse> viewCommentList(Long post_id);

    List<CommentResponse> viewMyComment(String userId);

    String addComment(CommentReqDTO commentReqDTO, Long postid, String userid);

    String deleteComment(Long comment_id, String userId);

    String updateComment(UpdateCommentReqDTO reqDTO, Long commentNo);


}
