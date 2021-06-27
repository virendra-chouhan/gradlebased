package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dao.UserOtpRepository;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dao.UserRepository;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.helper.SmsHelper;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.User;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.UserOtp;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.security.JwtTokenUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	Timestamp systemtime = new Timestamp(System.currentTimeMillis());
	Logger logger = LogManager.getLogger(AuthService.class);

	@Autowired
	UserRepository userRepository;
	@Autowired
	SmsHelper smsHelper;
	@Autowired
	UserOtp userOtp;
	@Autowired
	UserOtpRepository userOtpRepository;

	@Autowired
	JwtTokenUtil jwtTokenUtil;

	public User userRegistration(User user) {
		// TODO Auto-generated method stub
		logger.trace("Authservice ,userRegistration started");
		String timestamp = String.valueOf(systemtime);
		user.setRow_created_at(timestamp);
		user.setRow_updated_at(timestamp);
		user.setUser_id(UUID.randomUUID().toString());
		user.setDate_of_registration(timestamp);
		logger.info("Before Find method");
		user.setUser_status("REGISTERED");
		User checkuser = userRepository.findByMobile_no(user.getMobile_no());
		if (checkuser != null && checkuser.getUser_status().equalsIgnoreCase("VERIFIED")) { //
			logger.error("User is already register with this number");
			logger.trace("Authservice ,userRegistration Ended");

			return null;
		} else {
			String otp = SmsHelper.generateOtp();
			long milisecond = System.currentTimeMillis();
			logger.info("number-" + user.getMobile_no());
			Object o = smsHelper.sendSms("91" + user.getMobile_no(), otp);
			if (checkuser != null) {
				if (checkuser.getUser_status().equalsIgnoreCase("REGISTERED")) {
					this.saveUserOtp(checkuser.getUser_id(), Integer.parseInt(otp), milisecond);
					return checkuser;

				}
			}
			logger.info("Else come to save");

			logger.trace("Authservice ,after send msg");
			userRepository.save(user);
			this.saveUserOtp(user.getUser_id(), Integer.parseInt(otp), milisecond);
			logger.trace("Authservice ,userRegistration Ended");

			return user;

		}

	}

	public UserOtp saveUserOtp(String user_id, int otp, long time) {
		logger.trace("Authservice ,saveUserOtp started");

		userOtp.setUser_id(user_id);
		userOtp.setOtp_value(otp);
		userOtp.setOtp_created_ts(time);
		userOtp.setOtp_status("VALID");

		// UserOtp(int id,user_id,otp,time,"VALID");
		this.userOtpRepository.updateByUserId(userOtp.getUser_id());
		userOtpRepository.save(userOtp);
		logger.trace("Authservice ,saveUserOtp Ended");

		return userOtp;
	}

	public String verifyOtp(UserOtp userotpcheck) {

		//
		logger.trace("Authservice ,verifyOtp Started");

		UserOtp dbuserotp = this.userOtpRepository.CheckOtp(userotpcheck.getUser_id(), "VALID",
				userotpcheck.getOtp_value());
		if (dbuserotp != null) {
			System.out.println("user details-" + dbuserotp.toString());
			long t1 = dbuserotp.getOtp_created_ts();
			long t2 = System.currentTimeMillis();
			long time = t2 - t1;
			logger.info("time=" + time);
			if (time > 180000) {
				logger.trace("Authservice ,verifyOtp Ended");

				return "ERROR_1";

			} else {
				this.userRepository.verifyUser(dbuserotp.getUser_id());
				String token = this.saveToken(dbuserotp.getUser_id());
				logger.info("Token=[" + token + "]");
				logger.trace("Authservice ,verifyOtp Ended");

				return token;
			}
		} else {
			logger.trace("Authservice ,verifyOtp Ended");

			return "ERROR_2";
		}
	}

	public String saveToken(String user_id) {
		logger.trace("Authservice ,saveToken Started");

		User user = this.userRepository.findByUser_Id(user_id);

		final String token = jwtTokenUtil.generateToken(user.getMobile_no());
		logger.trace("Authservice ,saveToken Ended");

		return token;
	}

	public String login(String no) {
		logger.trace("Authservice ,login Started");

		User user = this.userRepository.findByMobile_no(no);
		if (user == null) {
			logger.trace("Authservice ,login Ended");

			return "ERROR_4";
		} else {
			String otp = SmsHelper.generateOtp();
			Object object = smsHelper.sendSms("91" + user.getMobile_no(), otp);
			logger.info("check value of object" + object);
			if (object != null) {
				logger.trace("Authservice ,login Ended");

				return "ERROR_3";
			} else {
				long milisecond = System.currentTimeMillis();

				this.saveUserOtp(user.getUser_id(), Integer.parseInt(otp), milisecond);
				logger.trace("Authservice ,login Ended");

				return user.getUser_id();
			}
		}
	}

	public boolean isTokenValid(String s) {
		try {
			boolean t = this.jwtTokenUtil.isTokenExpired(s);
			return true;
		} catch (Exception e) {

			return false;
		}

	}

	public User getUserDetailsByMobileNumber(String mobile_no) {

		return this.userRepository.findByMobile_no(mobile_no);
	}

	public User getUserDetails(String user_id) {
		return this.userRepository.findByUser_Id(user_id);
	}

}
