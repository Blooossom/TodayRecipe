package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.comment.CommentReqDTO;
import com.example.todayrecipe.dto.comment.CommentResponse;
import com.example.todayrecipe.dto.comment.UpdateCommentReqDTO;
import com.example.todayrecipe.entity.Comment;
import com.example.todayrecipe.repository.CommentRepository;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.repository.PostRepository;
import com.example.todayrecipe.entity.User;
import com.example.todayrecipe.repository.UserRepository;
import com.example.todayrecipe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public String addComment(CommentReqDTO commentReqDTO, Long post_id, String userid) {
        try {
            Post post = postRepo.findPostById(post_id);
            User user = userRepo.findUserByUserid(userid).orElse(null);
            repo.save(Comment.builder()
                    .post(post)
                    .user(user)
                    .writer(user.getNickname())
                    .content(commentReqDTO.getContent())
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
            Comment comment = repo.findByCommentNo(comment_id);
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

    @Override
    public String updateComment(UpdateCommentReqDTO reqDTO, Long commentNo) {
        Comment comment = repo.findByCommentNo(commentNo);
        String content = reqDTO.getContent();
        if (content == null || content.isBlank()) {
            content = comment.getContent();
        }
        Integer result = repo.updateComment(content, commentNo);
        return result > 0? "success":"failed";
    }



}
