package com.personal.finance.repository;

import com.personal.finance.model.User;

import java.util.Optional;

public interface UserRepository {
    void save(User user);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    Integer findUserIdByEmail(String email);
}
