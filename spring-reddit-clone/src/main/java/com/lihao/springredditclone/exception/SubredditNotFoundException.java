package com.lihao.springredditclone.exception;

public class SubredditNotFoundException extends RuntimeException {
    public SubredditNotFoundException(String msg) {
        super("Subreddit: " + msg + " not found.");
    }
}
