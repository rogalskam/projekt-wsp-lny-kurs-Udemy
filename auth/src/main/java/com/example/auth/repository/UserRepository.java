package com.example.auth.repository;

import com.example.auth.entity.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByEmail(@Email String email);

    Optional<User> findUserByUuid(String uuid);

    @Query(nativeQuery = true, value = "SELECT * FROM users where login=?1 and islock=false and isenable=true")
    Optional<User> findUserByLoginAndLockAndEnabled(String login);

    @Query(nativeQuery = true, value = "SELECT * FROM users where login=?1 and islock=false and isenabled=true and role='ADMIN'")
    Optional<User> findUserByLoginAndLockAndEnabledAndIsAdmin(String login);

}
