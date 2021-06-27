package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dao;

import javax.transaction.Transactional;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @Query("SELECT u from User u WHERE u.mobile_no=:mobile_no")
  User findByMobile_no(@Param("mobile_no") String mobile_no);

  @Transactional
  @Modifying
  @Query("UPDATE User u SET u.user_status = 'VERIFIED' where u.user_id=:user_id")
  void verifyUser(@Param("user_id") String user_id);

  @Query("SELECT u from User u WHERE u.user_id=:user_id")
  User findByUser_Id(@Param("user_id") String user_id);

}