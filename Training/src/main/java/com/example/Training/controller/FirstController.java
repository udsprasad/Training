package com.example.Training.controller;

import com.example.Training.config.AppConfig;
import com.example.Training.model.JwtRequest;
import com.example.Training.model.Person;
import com.example.Training.model.User;
import com.example.Training.service.FirstService;
import com.example.Training.service.UserService;
import com.example.Training.utils.JwtUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RefreshScope
@RestController
@OpenAPIDefinition(info = @Info(
        title = "the title",
        version = "0.0",
        description = "My API"
))
public class FirstController {

    @Autowired
    private FirstService firstService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/hai")
    @Operation(summary = "get Hai response")
    @ApiResponse(responseCode="200",description = "successful")
    ResponseEntity<Object> get() throws Exception {
        return ResponseEntity.ok(firstService.getMethod());
    }

    @GetMapping(value = "/", produces = "application/json")
    ResponseEntity<Object> index(@Value("${my.greetings}") String value){
        return ResponseEntity.ok(value);
    }

    @GetMapping("/user/{id}")
    Optional<Person> getPerson(@PathVariable Long id){
        return firstService.getPerson(id);
    }

    @PostMapping(value = "/user", produces = "application/json")
    void insertUser(@RequestBody User user){
        userService.insertUser(User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .build());
    }

    @PostMapping(value = "/authenticate", produces = "application/json")
    ResponseEntity<String> authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println("authenticate");
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("user not found");
       }
        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());

        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
    }


}
