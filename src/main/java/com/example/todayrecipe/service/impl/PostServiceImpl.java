package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.post.PostRequest;
import com.example.todayrecipe.dto.post.PostResponse;
import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.repository.PostRepository;
import com.example.todayrecipe.entity.User;
import com.example.todayrecipe.repository.UserRepository;
import com.example.todayrecipe.service.PostService;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repo;
    private final UserRepository userRepo;

    @Override
    public List<PostResponse> selectPostList() {
        List<PostResponse> responseList = repo.findAll().stream()
                .map(PostResponse::new).collect(Collectors.toList());
        for (int i = 0; i < responseList.size(); i++) {
            System.out.println(responseList.get(i).toString());
        }
        return responseList;
    }

    @Override
    public List<PostResponse> selectRecommendList(){
        List<PostResponse> responseList = repo.findAllByOrderByRecommendDesc().stream()
                .map(PostResponse::new).collect(Collectors.toList());

        return responseList;
    }

    @Override
    public List<PostResponse> selectPostListByEmail(LoginReqDTO loginReqDTO) {
        String email = loginReqDTO.getEmail();
        User user = userRepo.findByEmail(email).orElse(null);
        return repo.findAllByUser(user).stream()
                .map(PostResponse::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addPost(PostRequest request, LoginReqDTO userReq) {
        String email = userReq.getEmail();
        try{
            User user = userRepo.findByEmail(email).orElse(null);
            repo.save(Post.builder()
                    .user(user)
                    .title(request.getTitle())
                    .writer(user.getNickname())
                    .content(request.getContent())
                    .view(0)
                    .build()
            );
        }catch (Exception err){
            err.printStackTrace();
            String message = "failed";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        String message = "success";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostResponse> viewPost(HashMap<String, Object> map) {
        Long postNo = Long.valueOf(map.get("postNo").toString());
        Post post = repo.findByPostNo(postNo).orElse(null);
        PostResponse response = new PostResponse(post);
        if (response == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        else {
            updateView(map);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<String> deletePost(HashMap<String, Object> map, LoginReqDTO loginReqDTO) {
        Integer result = null;
        String email = loginReqDTO.getEmail();
        Long postNo = Long.valueOf(map.get("postNo").toString());
        try {
            User user = userRepo.findByEmail(email).orElse(null);
            Post post = repo.findByPostNo(postNo).orElse(null);
            if (!post.getUser().getEmail().equals(user.getEmail())) {
                String message = "failed";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            result = repo.deleteByPostNo(postNo);
        } catch (Exception err) {
            String message = "failed";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        if (result > 0) {
            String message = "success";
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else {
            String message = "failed";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

    }

    @Transactional
    @Override
    public ResponseEntity<String> updatePost(PostRequest request, LoginReqDTO loginReqDTO) {
        Post post = repo.findByPostNo(request.getPostNo()).orElse(null);
        User user = userRepo.findByEmail(loginReqDTO.getEmail()).orElse(null);
        if (!user.getEmail().equals(post.getUser().getEmail())){
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
        try{
           repo.save(Post.builder()
                           .title(request.getTitle())
                           .view(post.getView())
                           .recommend(post.getRecommend())
                           .writer(post.getWriter())
                           .content(request.getContent())
                   .build());
        } catch (Exception err){
            err.printStackTrace();
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @Transactional
    @Override
    public int updateView(HashMap<String, Object> map) {
        return repo.updateView(Long.valueOf(map.get("postNo").toString()));
    }

    @Transactional
    @Override
    public int updateRecommend(HashMap<String, Object> map) {
      return repo.updateRecommend(Long.valueOf(map.get("postNo").toString()));
    }
    @Override
    @Transactional
    public PostResponse getPost(HashMap<String, Object> map){
        Optional<Post> postWrapper = repo.findById(Long.valueOf(map.get("postNo").toString()));
        if (postWrapper.isPresent()) {
            Post post = postWrapper.get();
            PostResponse response = new PostResponse(post);
            return response;
        }
        return null;
    }
}
