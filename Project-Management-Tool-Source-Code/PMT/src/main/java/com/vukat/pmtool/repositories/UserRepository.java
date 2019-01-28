package com.vukat.pmtool.repositories;

import com.vukat.pmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {


    User findByUsername(String username);
    User getById(Long id);

    //@Query("SELECT U.name FROM User U WHERE LOWER(U.name) LIKE LOWER(?1)")
    //List<String> findByName(String matchPhrase);


}
