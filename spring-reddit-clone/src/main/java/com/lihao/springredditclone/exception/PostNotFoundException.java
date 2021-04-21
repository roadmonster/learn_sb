package com.lihao.springredditclone.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(String message) {
        super("Post of name: " + message + " not found.");
    }
}
