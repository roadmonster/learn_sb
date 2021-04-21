package com.lihao.springredditclone.service;

import com.lihao.springredditclone.Repository.UserRepository;
import com.lihao.springredditclone.Repository.VerificationTokenRepository;
import com.lihao.springredditclone.dto.AuthenticationResponse;
import com.lihao.springredditclone.dto.LoginRequest;
import com.lihao.springredditclone.dto.RegisterRequest;
import com.lihao.springredditclone.exception.SpringRedditException;
import com.lihao.springredditclone.exception.UserIdNotFoundException;
import com.lihao.springredditclone.model.AppUser;
import com.lihao.springredditclone.model.NotificationEmail;
import com.lihao.springredditclone.model.VerificationToken;
import com.lihao.springredditclone.security.JwtProvider;
import com.lihao.springredditclone.util.Constants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        AppUser appUser = new AppUser();
        appUser.setUserName(registerRequest.getUsername());
        appUser.setEmail(registerRequest.getEmail());
        appUser.setPassword(encodePassword(registerRequest.getPassword()));
        appUser.setCreatedTime(Instant.now());
        appUser.setEnabled(false);

        userRepository.save(appUser);

        String token = generateVerificationToken(appUser);
        String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
                + Constants.ACTIVATION_EMAIL + "/" + token);

        mailService.sendMail(new NotificationEmail("Please Activate your account", appUser.getEmail(), message));
    }

    private String generateVerificationToken(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setAppUser(appUser);
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenRepository.findByToken(token);
        verificationTokenOptional.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationTokenOptional.get());
    }

    @Transactional
    void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getAppUser().getUserName();
        AppUser appUser = userRepository.findByUserName(username).orElseThrow(() -> new SpringRedditException("User Not Found with id - " + username));
        appUser.setEnabled(true);
        userRepository.save(appUser);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()
                ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }

    @Transactional(readOnly = true)
    public AppUser getCurrentUser(){
        AppUser currUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.userRepository.findById(currUser.getUserId())
                .orElseThrow(()-> new UserIdNotFoundException("User: " + currUser.getUserId()
                        + " not exist in database"));

    }


}
