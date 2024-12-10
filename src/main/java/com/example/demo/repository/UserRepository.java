package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query(value = "select u from User u where u.email = :email and u.verifyCode = :code")
    Optional<User> findByEmainCode(String email, String code);


    @Query(value = "SELECT u.* FROM user u WHERE u.id = ?1", nativeQuery = true)
    Optional<User> findById(Integer id);

}
