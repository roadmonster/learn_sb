package com.lihao.springredditclone.mapper;

import com.lihao.springredditclone.dto.PostRequest;
import com.lihao.springredditclone.dto.PostResponse;
import com.lihao.springredditclone.model.AppUser;
import com.lihao.springredditclone.model.Post;
import com.lihao.springredditclone.model.Subreddit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "appUser", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequest postRequest,
             Subreddit subreddit, AppUser user);

    @Mapping(target = "postId")
    @Mapping(target = "postName")
    @Mapping(target = "userName", source = "post.appUser.userName")
    @Mapping(target = "url")
    @Mapping(target = "communityName", expression = "java(post.getSubreddit().getCommunityName())")
    PostResponse mapToDto(Post post);

}
