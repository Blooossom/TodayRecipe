package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.comment.CommentReqDTO;
import com.example.todayrecipe.dto.comment.CommentResDTO;
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
    public List<CommentResDTO> viewCommentList(HashMap<String, Object> map) {
        Post post = postRepo.findByPostno(Long.valueOf(map.get("postNo").toString())).orElse(null);
        return repo.findAllByPost(post)
                .stream()
                .map(CommentResDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentResDTO> viewMyComment(LoginReqDTO loginReqDTO) {
        User user = userRepo.findByEmail(loginReqDTO.getEmail()).orElse(null);
        return repo.findAllByUser(user)
                .stream()
                .map(CommentResDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addComment(CommentReqDTO commentReqDTO, LoginReqDTO loginReqDTO) {
        try {
            Post post = postRepo.findByPostno(commentReqDTO.getPostno()).orElse(null);
            User user = userRepo.findByEmail(loginReqDTO.getEmail()).orElse(null);
            repo.save(commentReqDTO.toEntity(user, post));
//                    Comment.builder()
//                            .post(post)
//                            .user(user)
//                            .writer(user.getNickname())
//                            .content(commentReqDTO.getContent())
//                    .build());
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
    @Transactional
    @Override
    public ResponseEntity<String> updateComment(UpdateCommentReqDTO reqDTO) {
        Comment comment = repo.findByCommentNo(reqDTO.getCommentNo());
        Integer result = null;
        try {
            result =  repo.updateComment(reqDTO.getContent(), comment.getCommentNo());
        }
        catch (Exception err) {
            err.printStackTrace();
            System.out.println(comment.getCommentNo().toString());
            System.out.println(comment.getContent().toString());
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
        if (result > 0) {
            return new ResponseEntity<>("success", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
    }



}
