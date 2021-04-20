package com.lihao.springredditclone.exception;

public class SpringRedditException extends RuntimeException{
    public SpringRedditException(String message) {
        super("Reddit Exception" + message);
    }
}
