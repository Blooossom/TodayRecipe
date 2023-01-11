package com.example.todayrecipe.post.service.impl;

import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.dto.PostResponse;
import com.example.todayrecipe.post.entity.Post;
import com.example.todayrecipe.post.repository.PostRepository;
import com.example.todayrecipe.post.service.PostService;
import com.example.todayrecipe.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repo;

    @Override
    public List<PostResponse> selectPostList() {
        List<PostResponse> responseList = repo.findAll().stream()
                .map(PostResponse::new).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public String addPost(PostRequest request, Long user_id) {
        try{
            repo.save(Post.builder()
                    .user(User.builder().id(user_id).build())
                    .title(request.getTitle())
                    .writer(request.getWriter())
                    .content(request.getContent())
                    .created_date(request.getCreated_date())
                    .modified_date(request.getModified_date())
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
        PostResponse response = new PostResponse(repo.findPostById(postId));
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

    @Override
    public String updatePost(Long postId, PostRequest request) {
        try{
            Post post = repo.findPostById(postId);
            post.update(request.getTitle(), request.getContent());
        } catch (Exception err){
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }


}
