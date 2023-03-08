package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.comment.CommentReqDTO;
import com.example.todayrecipe.dto.comment.CommentResponse;
import com.example.todayrecipe.dto.comment.UpdateCommentReqDTO;
import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.entity.Comment;
import com.example.todayrecipe.repository.CommentRepository;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.repository.PostRepository;
import com.example.todayrecipe.entity.User;
import com.example.todayrecipe.repository.UserRepository;
import com.example.todayrecipe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    @Override
    public List<CommentResponse> viewCommentList(HashMap<String, Object> map) {
        Post post = postRepo.findByPostNo(Long.valueOf(map.get("postNo").toString())).orElse(null);
        return repo.findAllByPost(post)
                .stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResponse> viewMyComment(LoginReqDTO loginReqDTO) {
        User user = userRepo.findByEmail(loginReqDTO.getEmail()).orElse(null);
        return repo.findAllByUser(user)
                .stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addComment(CommentReqDTO commentReqDTO, LoginReqDTO loginReqDTO, HashMap<String, Object> map) {
        try {
            Post post = postRepo.findByPostNo(Long.valueOf(map.get("postNo").toString())).orElse(null);
            User user = userRepo.findByEmail(loginReqDTO.getEmail()).orElse(null);
            repo.save(Comment.builder()
                    .post(post)
                    .user(user)
                    .writer(user.getNickname())
                    .content(commentReqDTO.getContent())
                    .build());
        }catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);

    }
    @Transactional
    @Override
    public ResponseEntity<String> deleteComment(HashMap<String, Object> map, LoginReqDTO loginReqDTO) {
        try{
            User user = userRepo.findByEmail(loginReqDTO.getEmail()).orElse(null);
            Comment comment = repo.findByCommentNo(Long.valueOf(map.get("commentNo").toString()));
            if (!user.getEmail().equals(comment.getUser().getEmail())) {
                return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
            }
            else {
                Integer result = repo.deleteByCommentNo(comment.getCommentNo());
                return new ResponseEntity<>("success", result>0? HttpStatus.OK:HttpStatus.BAD_REQUEST);
            }
        }catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> updateComment(UpdateCommentReqDTO reqDTO, HashMap<String, Object> map) {
        Comment comment = repo.findByCommentNo(Long.valueOf(map.get("commentNo").toString()));
        String content = reqDTO.getContent();
        if (content == null || content.isBlank()) {
            content = comment.getContent();
        }
        Integer result = repo.updateComment(content, comment.getCommentNo());
        String message = result > 0? "success":"failed";
        return  new ResponseEntity<>(message, message.equals("success")? HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }



}
