package com.lihao.springredditclone.mapper;

import com.lihao.springredditclone.dto.SubredditDto;
import com.lihao.springredditclone.model.Post;
import com.lihao.springredditclone.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    @Mapping(target = "id")
    @Mapping(target = "communityName")
    @Mapping(target = "description")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts){
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")

    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
