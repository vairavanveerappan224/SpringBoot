package com.example.springOne.repository;

import com.example.springOne.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // Custom JPQL – Find by name
    @Query("SELECT u FROM UserEntity u WHERE u.name = :name")
    List<UserEntity> findByUserName(@Param("name") String name);

    //  Custom JPQL – Find by course
    @Query("SELECT u FROM UserEntity u WHERE u.course = :course")
    List<UserEntity> findByCourse(@Param("course") String course);

    // Custom JPQL – Get all users (replacement for findAll())
    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> loadAllUsers();

    //  Custom JPQL – Find users whose name contains text
    @Query("SELECT u FROM UserEntity u WHERE u.name LIKE %:text%")
    List<UserEntity> searchByName(@Param("text") String text);

    //  Custom SQL – native query
    @Query(value = "SELECT * FROM users WHERE course = :course", nativeQuery = true)
    List<UserEntity> getUsersByCourseNative(@Param("course") String course);


    // Custom GET by ID
    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    UserEntity getUserByIdCustom(@Param("id") Integer id);

    // Custom UPDATE by ID
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.name = :name, u.course = :course WHERE u.id = :id")
    int updateNameAndCourseById(@Param("id") Integer id,
                                @Param("name") String name,
                                @Param("course") String course);

    // Custom DELETE by ID
    @Modifying
    @Transactional
    @Query("DELETE FROM UserEntity u WHERE u.id = :id")
    int deleteUserByIdCustom(@Param("id") Integer id);

}
