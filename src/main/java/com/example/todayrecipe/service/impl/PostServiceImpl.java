package com.example.todayrecipe.service.impl;

import com.example.todayrecipe.dto.post.PostRequest;
import com.example.todayrecipe.dto.post.PostResponse;
import com.example.todayrecipe.entity.Post;
import com.example.todayrecipe.repository.PostRepository;
import com.example.todayrecipe.entity.User;
import com.example.todayrecipe.repository.UserRepository;
import com.example.todayrecipe.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public List<PostResponse> selectPostListByUserid(String userId) {
        User user = userRepo.findUserByUserid(userId).orElse(null);
        Long id = user.getId();
        return repo.findAllByUserId(id).stream()
                .map(PostResponse::new).collect(Collectors.toList());
    }

    @Override
    public String addPost(PostRequest request, String user_id) {
        try{
            User user = userRepo.findUserByUserid(user_id).orElse(null);
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
            return "failed";
        }
        return "success";
    }

    @Override
    public PostResponse viewPost(Long postId) {
        Post post = repo.findPostById(postId);
        PostResponse response = new PostResponse(post);
        return response;
    }

    @Override
    public String deletePost(Long postId, String userId) {
        try {
            User user = userRepo.findUserByUserid(userId).orElse(null);
            Long postUserId = repo.findPostById(postId).getUser().getId();
            if (!postUserId.equals(user.getId())) {
                return "failed";
            }
            repo.deletePostById(postId);
        } catch (Exception err) {
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Transactional
    @Override
    public String updatePost(PostRequest request, String userId) {
        Post post = repo.findPostById(request.getId());
        User user = userRepo.findUserByUserid(userId).orElse(null);
        if (!user.getId().equals(post.getUser().getId())){
            return "failed";
        }
        try{
           repo.save(Post.builder()
                           .id(post.getId())
                           .title(request.getTitle())
                           .view(post.getView())
                           .recommend(post.getRecommend())
                           .writer(post.getWriter())
                           .content(request.getContent())
                   .build());
        } catch (Exception err){
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }
    @Transactional
    @Override
    public int updateView(Long id) {
        return repo.updateView(id);
    }

    @Transactional
    @Override
    public int updateRecommend(Long id) {
      return repo.updateRecommend(id);
    }
    @Override
    @Transactional
    public PostResponse getPost(Long postId){
        Optional<Post> postWrapper = repo.findById(postId);
        if (postWrapper.isPresent()) {
            Post post = postWrapper.get();
            PostResponse response = new PostResponse(post);
            return response;
        }
        return null;
    }
}
