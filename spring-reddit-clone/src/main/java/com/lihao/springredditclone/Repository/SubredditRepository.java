package com.lihao.springredditclone.Repository;

import com.lihao.springredditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
    Optional<Subreddit> findByCommunityName(String subredditName);
}
