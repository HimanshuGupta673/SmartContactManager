package com.learner.smartContactManager.dao;

import com.learner.smartContactManager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
