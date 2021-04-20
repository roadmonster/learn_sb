package com.lihao.springredditclone.security;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.KeyStore;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init(){

    }
}
