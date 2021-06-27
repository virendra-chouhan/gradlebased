package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dao;

import javax.transaction.Transactional;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.UserOtp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, String> {
  @Transactional
  @Modifying
  @Query("UPDATE UserOtp u SET u.otp_status = 'INVALID' where u.user_id=:user_id")
  void updateByUserId(@Param("user_id") String user_id);

  @Query("SELECT u from UserOtp u WHERE u.user_id=:user_id AND u.otp_status=:status AND u.otp_value=:otp")
  UserOtp CheckOtp(@Param("user_id") String user_id, @Param("status") String status, @Param("otp") int otp);

}
