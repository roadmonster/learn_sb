package com.lihao.jwt.service;

import com.lihao.jwt.entity.ApplicationUser;
import com.lihao.jwt.repository.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser appUser = applicationUserRepository.findByUsername(username);
        if(appUser == null){
            throw  new UsernameNotFoundException(username);
        }

        return new User(appUser.getUsername(), appUser.getPassword(), Collections.emptyList());

    }
}
