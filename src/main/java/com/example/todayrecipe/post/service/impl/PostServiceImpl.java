package com.example.todayrecipe.post.service.impl;

import com.example.todayrecipe.post.dto.PostRequest;
import com.example.todayrecipe.post.dto.PostResponse;
import com.example.todayrecipe.post.repository.PostRepository;
import com.example.todayrecipe.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repo;

    @Override
    public List<PostResponse> selectPostList() {
        List<PostResponse> responseList = repo.findAll().stream()
                .map(en -> new PostResponse(en)).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public String addPost(PostRequest request) {
        try{
            repo.save(request.toEntity());
        }catch (Exception err){
            err.printStackTrace();
            return "failed";
        }
        return "success";
    }
}
