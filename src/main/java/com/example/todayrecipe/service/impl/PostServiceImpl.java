package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.post.PostReqDTO;
import com.example.todayrecipe.dto.post.PostResDTO;
import com.example.todayrecipe.dto.post.UpdatePostReqDTO;
import com.example.todayrecipe.dto.user.LoginReqDTO;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.repository.PostRepository;
import com.example.todayrecipe.entity.User;
import com.example.todayrecipe.repository.UserRepository;
import com.example.todayrecipe.service.PostService;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repo;
    private final UserRepository userRepo;

    @Override
    public List<PostResDTO> selectPostList() {
        List<PostResDTO> responseList = repo.findAll().stream()
                .map(PostResDTO::new).collect(Collectors.toList());
        for (int i = 0; i < responseList.size(); i++) {
            System.out.println(responseList.get(i).toString());
        }
        return responseList;
    }

    @Override
    public List<PostResDTO> selectRecommendList(){
        List<PostResDTO> responseList = repo.findAllByOrderByRecommendDesc().stream()
                .map(PostResDTO::new).collect(Collectors.toList());

        return responseList;
    }

    @Override
    public List<PostResDTO> selectPostListByEmail(LoginReqDTO loginReqDTO) {
        String email = loginReqDTO.getEmail();
        User user = userRepo.findByEmail(email).orElse(null);
        return repo.findAllByUser(user).stream()
                .map(PostResDTO::new).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> addPost(PostReqDTO request, LoginReqDTO userReq) {
        String email = userReq.getEmail();
        try{
            User user = userRepo.findByEmail(email).orElse(null);
            repo.save(Post.builder()
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
            String message = "failed";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        String message = "success";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<PostResDTO> viewPost(HashMap<String, Object> map) {
        Long postNo = Long.valueOf(map.get("postNo").toString());
        Post post = repo.findByPostno(postNo).orElse(null);
        PostResDTO response = new PostResDTO(post);
        if (response == null) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        else {
            updateView(postNo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

    }
    @Transactional
    @Override
    public ResponseEntity<String> deletePost(HashMap<String, Object> map, LoginReqDTO loginReqDTO) {
        Integer result = null;
        String email = loginReqDTO.getEmail();
        Long postNo = Long.valueOf(map.get("postNo").toString());
        try {
            User user = userRepo.findByEmail(email).orElse(null);
            Post post = repo.findByPostno(postNo).orElse(null);
            if (!post.getUser().getEmail().equals(user.getEmail())) {
                String message = "failed";
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
            result = repo.deleteByPostno(postNo);
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
    public ResponseEntity<String> updatePost(UpdatePostReqDTO reqDTO, LoginReqDTO loginReqDTO) {
//       User user = userRepo.findByEmail(loginReqDTO.getEmail()).orElse(null);
//       Post post = repo.findByPostNo(reqDTO.getPostNo()).orElse(null);
//       Long result = null;
//       if (!post.getUser().getEmail().equals(user.getEmail())) {
//           return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
//       }
//       Long postNo = reqDTO.getPostNo();
//       String title = reqDTO.getTitle().isBlank() || reqDTO.getTitle() == null? post.getTitle() : reqDTO.getTitle();
//       String content = reqDTO.getContent().isBlank() || reqDTO.getContent() == null? post.getContent() : reqDTO.getTitle();
//
//       try {
//           result = repo.updatePost(title, content, postNo);
//       }
//       catch (Exception err) {
//           err.printStackTrace();
//           return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
//       }
//       if (result > 0) {
//           return new ResponseEntity<>("success", HttpStatus.OK);
//       }
//       else {
//           return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
//
//       }
        return null;

    }
    @Transactional
    @Override
    public int updateView(Long postNo) {
        return repo.updateView(postNo);
    }

    @Transactional
    @Override
    public int updateRecommend(HashMap<String, Object> map) {
      return repo.updateRecommend(Long.valueOf(map.get("postNo").toString()));
    }
    @Override
    @Transactional
    public PostResDTO getPost(HashMap<String, Object> map){
        Optional<Post> postWrapper = repo.findById(Long.valueOf(map.get("postNo").toString()));
        if (postWrapper.isPresent()) {
            Post post = postWrapper.get();
            PostResDTO response = new PostResDTO(post);
            return response;
        }
        return null;
    }
}
