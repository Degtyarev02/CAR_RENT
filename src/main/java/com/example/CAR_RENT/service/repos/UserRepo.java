package com.example.CAR_RENT.service.repos;

import com.example.CAR_RENT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
