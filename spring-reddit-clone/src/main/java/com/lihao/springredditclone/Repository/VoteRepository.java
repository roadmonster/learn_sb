package com.lihao.springredditclone.Repository;

import com.lihao.springredditclone.model.AppUser;
import com.lihao.springredditclone.model.Post;
import com.lihao.springredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndAppUserOrderByVoteIdDesc(Post post, AppUser appUser);

}
