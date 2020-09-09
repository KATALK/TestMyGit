package com.edimen.dao;


import com.edimen.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
    User findByUsernameLike(String username);
}
