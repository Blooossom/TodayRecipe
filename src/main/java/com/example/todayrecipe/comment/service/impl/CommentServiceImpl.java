package com.example.todayrecipe.comment.service.impl;

import com.example.todayrecipe.comment.dto.CommentRequest;
import com.example.todayrecipe.comment.dto.CommentResponse;
import com.example.todayrecipe.comment.entity.Comment;
import com.example.todayrecipe.comment.repository.CommentRepository;
import com.example.todayrecipe.comment.service.CommentService;
import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.entity.User;
import com.example.todayrecipe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repo;
    private final UserRepository userRepo;

    @Override
    public List<CommentResponse> viewCommentList(Long post_id) {
        return repo.findByPostId(post_id)
                .stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public String addComment(CommentRequest commentRequest, Long post_id, String userid) {
        try {
            User user = userRepo.findUserByUserid(userid).orElse(null);
            repo.save(Comment.builder()
                    .post(Post.builder().id(post_id).build())
                    .user(user)
                    .writer(user.getNickname())
                    .content(commentRequest.getContent())
                    .created_date("2023-01-17")
                    .modified_date("2023-01-17")
                    .build());
        }catch (Exception err) {
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Override
    public String deleteComment(Long comment_id) {
        try{
            repo.deleteCommentById(comment_id);
        }catch (Exception err) {
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Override
    public String modifyComment(Long comment_id, CommentRequest request) {
        try{
            Comment comment = repo.findCommentById(comment_id);
            comment.update(request.getContent());
        }catch (Exception err){
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }

}
