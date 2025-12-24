package com.example.E_commerce.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Integer> , JpaSpecificationExecutor<UserEntity> {

    public List<UserEntity> findByNameContainingIgnoreCase(String name);

    boolean existsByEmail(String email);
    List<UserView> findByUserType(int usertype);
    Optional<UserEntity> findByEmail(String email);





}
