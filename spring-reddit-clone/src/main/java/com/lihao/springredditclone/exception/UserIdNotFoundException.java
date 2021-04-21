package com.lihao.springredditclone.exception;

public class UserIdNotFoundException extends RuntimeException {
    public UserIdNotFoundException(String s) {
        super(s);
    }
}
