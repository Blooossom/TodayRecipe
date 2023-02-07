package com.example.todayrecipe.comment.service.impl;

import com.example.todayrecipe.comment.dto.CommentRequest;
import com.example.todayrecipe.comment.dto.CommentResponse;
import com.example.todayrecipe.comment.entity.Comment;
import com.example.todayrecipe.comment.repository.CommentRepository;
import com.example.todayrecipe.comment.service.CommentService;
import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.post.repository.PostRepository;
import com.example.todayrecipe.user.entity.User;
import com.example.todayrecipe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    @Override
    public List<CommentResponse> viewCommentList(Long post_id) {
        return repo.findAllByPostId(post_id)
                .stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> viewMyComment(String userId) {
        User user = userRepo.findUserByUserid(userId).orElse(null);
        Long id = user.getId();
        return repo.findAllByUserId(id)
                .stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public String addComment(CommentRequest commentRequest, Long post_id, String userid) {
        try {
            Post post = postRepo.findPostById(post_id);
            User user = userRepo.findUserByUserid(userid).orElse(null);
            repo.save(Comment.builder()
                    .post(post)
                    .user(user)
                    .writer(user.getNickname())
                    .content(commentRequest.getContent())
                    .build());
        }catch (Exception err) {
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }
    @Transactional
    @Override
    public String deleteComment(Long comment_id, String userId) {
        try{
            User user = userRepo.findUserByUserid(userId).orElse(null);
            Comment comment = repo.findCommentById(comment_id);
            Long user_Id = user.getId();
            Long commentId = comment.getUser().getId();
            if (!user_Id.equals(commentId)) {
                return "failed";
            }
            else {
                repo.deleteCommentById(comment_id);
            }
        }catch (Exception err) {
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Transactional
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
