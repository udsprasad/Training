package com.example.Training.controller;

import com.example.Training.aop.ExecutionTimer;
import com.example.Training.model.JwtRequest;
import com.example.Training.model.Person;
import com.example.Training.model.PersonDTO;
import com.example.Training.model.User;
import com.example.Training.service.FirstService;
import com.example.Training.service.RestTemplateService;
import com.example.Training.service.UserService;
import com.example.Training.utils.JwtUtil;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    RestTemplateService restTemplateService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/hai")
    @Operation(summary = "get Hai response")
    @ApiResponse(responseCode="200",description = "successful")
    Object get() throws Exception {
        return firstService.getMethod();
    }

    @PostMapping("/post")
    ResponseEntity<PersonDTO> insertPerson(@RequestBody PersonDTO person){
        return restTemplateService.addPerson(person);
    }

    @PutMapping("/put")
    void updatePerson(@RequestParam String name,@RequestParam String phonenumber){
        restTemplateService.updatePerson(name, phonenumber);
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
