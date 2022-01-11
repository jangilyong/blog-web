package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;


// DAO
// 자동으로 bean 등록이 된다.
// @Repository 자동으로 등록되기 때문에 생략가능
public interface UserRepository extends JpaRepository<User, Integer>{ // JpaRepository<테이블명, PK타입>

}
