package com.lihao.springredditclone.security;

import com.lihao.springredditclone.exception.SpringRedditException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try{
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());

        }catch (Exception e){
            e.printStackTrace();
            throw new SpringRedditException("Error occurred initializing key store");
        }
    }

    public String generateToken(Authentication authenticate) {
        User principal = (User) authenticate.getPrincipal();
        return Jwts.builder().setSubject(principal.getUsername())
                .signWith(this.getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey(){
        try{
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        }catch (Exception e){
            e.printStackTrace();
            throw new SpringRedditException("Error getting the private key from key store");
        }
    }
}
