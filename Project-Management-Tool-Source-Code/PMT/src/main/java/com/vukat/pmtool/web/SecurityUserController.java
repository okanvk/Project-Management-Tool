package com.vukat.pmtool.web;

import com.vukat.pmtool.domain.User;
import com.vukat.pmtool.services.UserService;
import com.vukat.pmtool.services.ValidationErrorService;
import com.vukat.pmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/usersecurity")
@CrossOrigin
public class SecurityUserController {


    @Autowired
    private ValidationErrorService validationErrorService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<?> getUser(Principal principal){

        User user = userService.getUser(principal.getName());

        return new ResponseEntity<User>(user, HttpStatus.OK);

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result){

        userValidator.validate(user,result);

        ResponseEntity<?> errorMap = validationErrorService.MapValidationService(result);

        if(errorMap!=null) return errorMap;



        User newUser = userService.updateUser(user);

        return new ResponseEntity<User>(newUser,HttpStatus.OK);

    }




}
