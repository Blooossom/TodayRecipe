package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.Response;
import com.example.todayrecipe.dto.comment.CommentReqDTO;
import com.example.todayrecipe.dto.comment.CommentResDTO;
import com.example.todayrecipe.entity.Comment;
import com.example.todayrecipe.jwt.JwtTokenProvider;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepo;
    private final Response response;
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final JwtTokenProvider provider;
    @Override
    public ResponseEntity<?> viewCommentList(Long postNo) {
        Post post = postRepo.findByPostno(postNo).orElse(null);
        return response.success(commentRepo.findAllByPost(post)
                .stream()
                .map(CommentResDTO::new)
                .collect(Collectors.toList()), "success", HttpStatus.OK );
    }

    @Override
    public ResponseEntity<?> viewMyComment(String accessToken) {
        User user = userRepo.findByEmail(provider.getAuthentication(accessToken).getName()).orElse(null);
        return response.success(commentRepo.findAllByUser(user)
                .stream()
                .map(CommentResDTO::new)
                .collect(Collectors.toList()), "success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addComment(CommentReqDTO.WriteReq commentReqDTO, String accessToken) {
        try {
            Post post = postRepo.findByPostno(commentReqDTO.getPostNo()).orElse(null);
            User user = userRepo.findByEmail(provider.getAuthentication(accessToken).getName()).orElse(null);
            commentRepo.save(commentReqDTO.toEntity(post, user));
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
    public ResponseEntity<?> deleteComment(Long commentNo, String accessToken) {
        try{
            User user = userRepo.findByEmail(provider.getAuthentication(accessToken).getName()).orElse(null);
            Comment comment = commentRepo.findByCommentNo(commentNo);
            if (!user.getEmail().equals(comment.getUser().getEmail())) {
                return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
            }
            else {
                Integer result = commentRepo.deleteByCommentNo(comment.getCommentNo());
                return new ResponseEntity<>("success", result>0? HttpStatus.OK:HttpStatus.BAD_REQUEST);
            }
        }catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);

        }
    }
    @Transactional
    @Override
    public ResponseEntity<?> updateComment(CommentReqDTO.UpdateReq reqDTO) {
        Comment comment = commentRepo.findByCommentNo(reqDTO.getCommentNo());
        Integer result = null;
        try {
            result =  commentRepo.updateComment(reqDTO.getContent(), comment.getCommentNo());
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
