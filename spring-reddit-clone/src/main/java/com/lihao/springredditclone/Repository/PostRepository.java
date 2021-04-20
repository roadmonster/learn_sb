package com.lihao.springredditclone.Repository;

import com.lihao.springredditclone.model.AppUser;
import com.lihao.springredditclone.model.Post;
import com.lihao.springredditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(AppUser appUser);
}
