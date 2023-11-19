package com.personal.finance.repository;

import com.personal.finance.model.User;

public interface UserRepository {
    void save(User user);
    boolean existsByEmail(String email);
}
