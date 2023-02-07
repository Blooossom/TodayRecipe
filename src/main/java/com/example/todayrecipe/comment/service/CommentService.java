package com.example.todayrecipe.comment.service;


import com.example.todayrecipe.comment.dto.CommentRequest;
import com.example.todayrecipe.comment.dto.CommentResponse;
import com.example.todayrecipe.post.dto.PostRequest;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CommentService {

    List<CommentResponse> viewCommentList(Long post_id);

    List<CommentResponse> viewMyComment(String userId);

    String addComment(CommentRequest commentRequest, Long postid, String userid);

    String deleteComment(Long comment_id, String userId);

    String modifyComment(Long comment_id, CommentRequest request);


}
