package com.example.todayrecipe.comment.service.impl;

import com.example.todayrecipe.comment.dto.CommentRequest;
import com.example.todayrecipe.comment.dto.CommentResponse;
import com.example.todayrecipe.comment.entity.Comment;
import com.example.todayrecipe.comment.repository.CommentRepository;
import com.example.todayrecipe.comment.service.CommentService;
import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repo;

    @Override
    public List<CommentResponse> viewCommentList(PostRequest request) {
       return repo.findByPostId(request.toEntity()).stream()
               .map(en -> new CommentResponse(en))
               .collect(Collectors.toList());
    }

    @Override
    public String addComment(CommentRequest commentRequest) {
        try {
            repo.save(Comment.builder()
                    .post(Post.builder().id(Long.valueOf(commentRequest.getId())).build())
                    .user(User.builder().nickname(commentRequest.getWriter()).build())
                    .content(commentRequest.getContent())
                    .created_date(commentRequest.getCreated_date())
                    .modified_date(commentRequest.getModified_date())
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
            repo.deleteById(comment_id);
        }catch (Exception err) {
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Override
    public String modifyComment(Long comment_id) {
        return null;
    }

}
