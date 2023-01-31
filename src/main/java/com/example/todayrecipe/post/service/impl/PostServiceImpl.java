package com.example.todayrecipe.post.service.impl;

import com.example.todayrecipe.post.dto.PostEditor;
import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.dto.PostResponse;
import com.example.todayrecipe.post.dto.PostUpdate;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.post.repository.PostRepository;
import com.example.todayrecipe.post.service.PostService;
import com.example.todayrecipe.user.entity.User;
import com.example.todayrecipe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.Comparator;
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
        List<PostResponse> responseList = repo.findAll().stream()
                .map(PostResponse::new).collect(Collectors.toList());
        Collections.sort(responseList, (o1, o2) -> {
            if (o1.getRecommend() > o2.getRecommend()) {
                return -1;
            }
            else if (o1.getRecommend() == o2.getRecommend()) {
                return 0;
            }
            else {
                return 1;
            }
        });
        return responseList;
    }

    @Override
    public List<PostResponse> selectPostListByUserid(String userId) {
        User user = userRepo.findUserByUserid(userId).orElse(null);
        Long id = user.getId();
        List<PostResponse> responseList = repo.findAllByUserId(id).stream()
                .map(PostResponse::new).collect(Collectors.toList());
        return responseList;
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
                    /*.created_date("2023-01-17") //바꿔야 함ㅇㅇ
                    .modified_date("2023-01-17") //이것도*/
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
    public String deletePost(Long postId) {
        try {
            repo.deletePostById(postId);
        } catch (Exception err) {
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }

    @Transactional
    @Override
    public String updatePost(PostRequest request, String id) {
        Post post = repo.findPostById(Long.valueOf(id));
        try{
           repo.save(Post.builder()
                           .id(post.getId())
                           .title(request.getTitle())
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
