package com.vukat.pmtool.services;

import com.vukat.pmtool.domain.User;
import com.vukat.pmtool.exception.UsernameAlreadyExistsException;
import com.vukat.pmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){

        try{


            // save process
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username must be unique
            newUser.setUsername(newUser.getUsername());
            newUser.setConfirmPassword("");

            return userRepository.save(newUser);

        }catch(Exception e){
            throw new UsernameAlreadyExistsException("Mail adress already exists. Did you forget password ?");
        }








    }

    public User getUser(String username){

        User user = userRepository.findByUsername(username);
        user.setPassword("");
        return user;

    }
    public User updateUser(User newUser){

        try{

            User updatedUser = userRepository.getById(newUser.getId());

            updatedUser.setFullName(newUser.getFullName());
            updatedUser.setConfirmPassword("");
            updatedUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

            return userRepository.save(updatedUser);

        }catch(Exception e){
            throw new UsernameAlreadyExistsException("Exception");
        }








    }



}
