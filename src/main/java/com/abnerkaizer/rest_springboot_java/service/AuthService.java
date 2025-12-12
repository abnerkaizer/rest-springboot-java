package com.abnerkaizer.rest_springboot_java.service;

import com.abnerkaizer.rest_springboot_java.data.dto.security.AccountCredentialsDTO;
import com.abnerkaizer.rest_springboot_java.data.dto.security.TokenDTO;
import com.abnerkaizer.rest_springboot_java.exception.RequiredObjectIsNullException;
import com.abnerkaizer.rest_springboot_java.model.User;
import com.abnerkaizer.rest_springboot_java.repository.UserRepository;
import com.abnerkaizer.rest_springboot_java.security.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.abnerkaizer.rest_springboot_java.mapper.ObjectMapper.parseObject;

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(AuthService.class.getName());


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentials){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword()
            )
        );

        var user = repository.findByUsername(credentials.getUsername());
        if (user == null) throw new UsernameNotFoundException("Username "+ credentials.getUsername()+" not found!");

        var token = tokenProvider.createAccessToken(
                credentials.getUsername(),
                user.getRoles()
        );
        return ResponseEntity.ok(token);
    }

    public ResponseEntity<TokenDTO> refreshToken(String username, String refreshToken){
        var user = repository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Username "+ username+" not found!");

        TokenDTO token = tokenProvider.refreshToken(refreshToken);
        return ResponseEntity.ok(token);
    }


    public AccountCredentialsDTO create(AccountCredentialsDTO user) {

        if (user == null) throw new RequiredObjectIsNullException();

        logger.info("Creating new user!");
        var entity = new User();

        entity.setFullName(user.getFullName());
        entity.setUsername(user.getUsername());
        entity.setPassword(generateHashedPassword(user.getPassword()));
        entity.setAccountNonExpired(true);
        entity.setAccountNonLocked(true);
        entity.setCredentialsNonExpired(true);
        entity.setEnabled(true);

        return parseObject(repository.save(entity), AccountCredentialsDTO.class);
    }

    private String generateHashedPassword(String password) {
        PasswordEncoder pdkdf2Encoder = new Pbkdf2PasswordEncoder(
                "", 8, 185000,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("pbkdf2", pdkdf2Encoder);
        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

        passwordEncoder.setDefaultPasswordEncoderForMatches(pdkdf2Encoder);
        return passwordEncoder.encode(password);
    }
}