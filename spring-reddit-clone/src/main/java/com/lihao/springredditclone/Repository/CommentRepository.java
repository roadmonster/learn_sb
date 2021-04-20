package com.lihao.springredditclone.Repository;

import com.lihao.springredditclone.model.AppUser;
import com.lihao.springredditclone.model.Comment;
import com.lihao.springredditclone.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
    List<Comment> findByUser(AppUser appUser);
}
