package com.example.todayrecipe.service;


import com.example.todayrecipe.dto.comment.CommentRequest;
import com.example.todayrecipe.dto.comment.CommentResponse;

import java.util.List;

public interface CommentService {

    List<CommentResponse> viewCommentList(Long post_id);

    List<CommentResponse> viewMyComment(String userId);

    String addComment(CommentRequest commentRequest, Long postid, String userid);

    String deleteComment(Long comment_id, String userId);

    String modifyComment(Long comment_id, CommentRequest request);


}
