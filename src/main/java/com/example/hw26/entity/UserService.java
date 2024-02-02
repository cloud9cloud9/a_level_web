package com.example.hw26.entity;

import com.example.hw26.framework.Kikernate;

import java.util.List;

public class UserService {
    private final UserDao userDao = UserDao.getInstance();

    public void saveUser(User user){
        Kikernate.generateInsert(user);
        userDao.saveUser(user);
    }
    public User getUserById(int id){
        Kikernate.generateSelectById();
        return userDao.getUserById(id);
    }
    public List<User> getAllUser(){
        Kikernate.genereteSelectAll();
        return userDao.getAllUser();
    }
    public void update(int id, User updateUser){
        Kikernate.generateUpdate(updateUser);
        userDao.update(id, updateUser);
    }
    public User deleteUser(int id){
        Kikernate.generateDelete();
        return userDao.deleteUser(id);
    }
}
