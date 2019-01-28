package com.vukat.pmtool.web;

import com.vukat.pmtool.domain.User;
import com.vukat.pmtool.security.JwtTokenProvider;
import com.vukat.pmtool.services.UserService;
import com.vukat.pmtool.services.ValidationErrorService;
import com.vukat.pmtool.validator.UserValidator;
import com.vukat.pmtool.viewmodel.LoginRequest;
import com.vukat.pmtool.viewmodel.LoginSuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static  com.vukat.pmtool.security.SecurityConstants.TOKEN_PREFIX;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ValidationErrorService validationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){

        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);

        if(errorMap != null)return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new LoginSuccessResponse(true,jwt));

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){

        userValidator.validate(user,result);

        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);

        if(errorMap!=null) return errorMap;

        User newUser = userService.saveUser(user);

        return new ResponseEntity<User>(newUser,HttpStatus.CREATED);

    }







}
