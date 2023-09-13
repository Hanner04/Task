package com.proyect.task.repository;

import com.proyect.task.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUserRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findByName(String username);
}
