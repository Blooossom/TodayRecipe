package com.example.todayrecipe.comment.service;


import com.example.todayrecipe.comment.dto.CommentRequest;
import com.example.todayrecipe.comment.dto.CommentResponse;
import com.example.todayrecipe.post.dto.PostRequest;

import java.util.List;

public interface CommentService {

    List<CommentResponse> viewCommentList(Long post_id);

    String addComment(CommentRequest commentRequest, Long postid, Long userid);

    String deleteComment(Long comment_id);

    String modifyComment(Long comment_id, CommentRequest request);


}
