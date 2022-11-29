package org.code.repository;

import org.code.data.User;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public Optional<User> save(User user) {
        var existing = users.put(user.getUsername(), user);

        return Optional.ofNullable(existing);
    }

    public Collection<User> getAll() {
        return users.values();
    }

    public User findByUsername(String username) {
        return users.get(username);
    }


    public List<User> findAll() {
        return users.values().stream().toList();
    }
}
