package com.example.CAR_RENT.service.repos;

import com.example.CAR_RENT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByUsername(String username);
}
