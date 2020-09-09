package com.edimen.service;

import com.edimen.dao.UserDao;
import com.edimen.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//用户业务层
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    //根据用户名查询用户
    public User findUserByUserName(String username) {
       return userDao.findByUsernameLike(username);
    }
    //查询所有
    public Page<User> findAll(Pageable page){
       return userDao.findAll(page);
    }
    @Transactional
    public User saveUser(User user) {
        return userDao.save(user);
    }

    public User findUserById(Long id){
        Optional<User> user = userDao.findById(id);
        User u = user.get();
        return u;
    }

    public User updateUser(User user) {
        return userDao.saveAndFlush(user);
    }

    public void deleteUser(long id) {
        userDao.deleteById(id);
    }
}
