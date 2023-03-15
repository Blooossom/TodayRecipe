package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.Response;
import com.example.todayrecipe.dto.post.PostReqDTO;
import com.example.todayrecipe.dto.post.PostResDTO;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.jwt.JwtTokenProvider;
import com.example.todayrecipe.repository.PostRepository;
import com.example.todayrecipe.entity.User;
import com.example.todayrecipe.repository.UserRepository;
import com.example.todayrecipe.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final JwtTokenProvider provider;

    private final PostRepository postRepo;
    private final UserRepository userRepo;

    private final RedisTemplate redisTemplate;
    private final Response response;

    public void recommendInit(){
        for (int i = 0; i < 5; i++) {
            redisTemplate.delete("recommend" + (i + 1));
        }
        List<PostResDTO.RecommendRes> responseList = postRepo.findAll().stream()
                .map(PostResDTO.RecommendRes::new).sorted((o1, o2) -> {
                    return Integer.compare(o2.getRecommend(), o1.getRecommend());
                }).collect(Collectors.toList());

        for (int i = 0; i < 5; i++) {
            redisTemplate.opsForValue().set("recommend" + (i + 1), responseList.get(i).toString());
        }

    }
    @Override
    public ResponseEntity<?> recommendListRedis() {
        recommendInit();
        List<Object> recommendlist = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            recommendlist.add(redisTemplate.opsForValue().get("recommend" + (i + 1)));
        }
        redisTemplate.opsForValue().set("recommend", recommendlist);
        return response.success(recommendlist, "success", HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> selectPostList() {
        List<PostResDTO> responseList = postRepo.findAll().stream()
                .map(PostResDTO::new).collect(Collectors.toList());
        for (int i = 0; i < responseList.size(); i++) {
            System.out.println(responseList.get(i).toString());
        }
        return response.success(responseList, "success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> selectRecommendList(){
        List<PostResDTO> responseList = postRepo.findAllByOrderByRecommendDesc().stream()
                .map(PostResDTO::new).collect(Collectors.toList());

        return response.success(responseList, "success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> selectPostListByEmail(String accessToken) {
        String email = provider.getAuthentication(accessToken).getName();
        User user = userRepo.findByEmail(email).orElse(null);
        return response.success(postRepo.findAllByUser(user).stream()
                .map(PostResDTO::new).collect(Collectors.toList()), "success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addPost(PostReqDTO.WritePost request, String accessToken) {

        String email = provider.getAuthentication(accessToken).getName();
        try{
            User user = userRepo.findByEmail(email).orElse(null);
            postRepo.save(Post.builder()
                    .user(user)
                    .title(request.getTitle())
                    .writer(user.getNickname())
                    .content(request.getContent())
                    .tag(request.getTag())
                    .view(0)
                    .build()
            );
        }catch (Exception err){
            err.printStackTrace();
            return response.fail("failed");
        }
        return response.success("success");
    }

    @Transactional
    @Override
    public ResponseEntity<?> viewPost(Long postNo) {
        Post post = postRepo.findByPostno(postNo).orElse(null);
        PostResDTO resDTO = new PostResDTO(post);
        if (resDTO == null) {
            return response.fail("잘못된 접근입니다.", HttpStatus.BAD_REQUEST);
        }
        else {
            updateView(postNo);
            return response.success(resDTO);
        }
    }
    @Transactional
    @Override
    public ResponseEntity<?> deletePost(Long postNo, String accessToken) {
        Integer result = null;
        String email = provider.getAuthentication(accessToken).getName();
        try {
            User user = userRepo.findByEmail(email).orElse(null);
            Post post = postRepo.findByPostno(postNo).orElse(null);
            if (!post.getUser().getEmail().equals(user.getEmail())) {
                String message = "failed";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            result = postRepo.deleteByPostno(postNo);
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
    public ResponseEntity<?> updatePost(PostReqDTO.UpdatePost updateReq, String accessToken) {
        User user = userRepo.findByEmail(provider.getAuthentication(accessToken).getName()).orElse(null);
        Post post = postRepo.findByPostno(updateReq.getPostNo()).orElse(null);

        String title = updateReq.getTitle().isBlank() || updateReq.getTitle() == null? post.getTitle(): updateReq.getTitle();
        String content = updateReq.getContent().isBlank() || updateReq.getContent() == null? post.getContent(): updateReq.getContent();
        try {
            postRepo.save(Post.builder().postno(post.getPostno())
                            .user(user)
                            .title(title)
                            .tag(post.getTag())
                            .content(content)
                            .writer(post.getWriter())
                            .view(post.getView())
                            .recommend(post.getRecommend())
                            .build());
        }
        catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
        return response.success(new PostResDTO.UpdateRes(post),"success", HttpStatus.OK);

    }
    @Transactional
    @Override
    public int updateView(Long postNo) {
        return postRepo.updateView(postNo);
    }

    @Transactional
    @Override
    public ResponseEntity<?> updateRecommend(HashMap<String, Object> map) {
        try {
            postRepo.updateRecommend(Long.valueOf(map.get("postNo").toString()));
        }catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
        }
      return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @Override
    @Transactional
    public PostResDTO getPost(HashMap<String, Object> map){
        Optional<Post> postWrapper = postRepo.findById(Long.valueOf(map.get("postNo").toString()));
        if (postWrapper.isPresent()) {
            Post post = postWrapper.get();
            PostResDTO response = new PostResDTO(post);
            return response;
        }
        return null;
    }


}
