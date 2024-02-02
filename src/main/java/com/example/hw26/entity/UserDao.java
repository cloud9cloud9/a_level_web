package com.example.hw26.entity;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDao {
    private static final UserDao INSTANCE = new UserDao();
    private final List<User> userList = new ArrayList<>();
    private AtomicInteger key = new AtomicInteger();

    private UserDao() {
        saveUser(User.builder().userName("dasdad").age(22).email("dasdasd").build());
        saveUser(User.builder().userName("dasdsaasdad").age(221).email("dasdadsasasd").build());
        saveUser(User.builder().userName("vlad").age(21).email("dmail").build());
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public void saveUser(User user) {
        user.setId(key.incrementAndGet());
        userList.add(user);
    }

    public User getUserById(int id) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public List<User> getAllUser() {
        return new ArrayList<>(userList);
    }

    public void update(int id, User updateUser) {
        User user = getUserById(id);
        if (updateUser != null && user != null) {
            user.setUserName(updateUser.getUserName());
            user.setAge(updateUser.getAge());
            user.setEmail(updateUser.getEmail());
            user.setId(key.incrementAndGet());
        }
    }

    public User deleteUser(int id) {
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        System.out.println("user not found");
        return null;
    }
}
