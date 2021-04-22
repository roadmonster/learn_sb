package com.lihao.springredditclone.service;

import com.lihao.springredditclone.Repository.PostRepository;
import com.lihao.springredditclone.Repository.SubredditRepository;
import com.lihao.springredditclone.Repository.UserRepository;
import com.lihao.springredditclone.dto.PostRequest;
import com.lihao.springredditclone.dto.PostResponse;
import com.lihao.springredditclone.exception.PostNotFoundException;
import com.lihao.springredditclone.exception.SubredditNotFoundException;
import com.lihao.springredditclone.mapper.PostMapper;
import com.lihao.springredditclone.model.AppUser;
import com.lihao.springredditclone.model.Post;
import com.lihao.springredditclone.model.Subreddit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;


    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByCommunityName(postRequest.getCommunityName())
                .orElseThrow(()->new SubredditNotFoundException(postRequest.getCommunityName()));
        this.postRepository.save(postMapper.map(postRequest, subreddit, this.authService.getCurrentUser()));

    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id){
        Post myPost = this.postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("" + id));
        return postMapper.mapToDto(myPost);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return this.postRepository.findAll().stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUserName(String username){
        AppUser user = this.userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return this.postRepository.findByAppUser(user).stream().map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
