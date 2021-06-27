/**
 * This is APIController for AuthService for safecrop_backend_v0.1
 * all rights are reserved by Baelworks Innovations Pvt. Ltd. from @2021
 */

package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.constants.ResponseMessages;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dto.LoginDto;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.dto.TehsilDto;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.exceptionhandler.CustomeException;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.helper.BaseResponseHelper;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.helper.SmsHelper;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.JwtResponse;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.User;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.model.UserOtp;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.security.JwtTokenUtil;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.service.AuthService;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.service.MasterService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.impl.DefaultClaims;

/**
 * Handles requests to "/api/v1" endpoints.
 * 
 * @see security.SecurityConfig to see how these endpoints are protected.
 */
@RestController
@RequestMapping(path = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
// For simplicity of this sample, allow all origins. Real applications should
// configure CORS for their use case.
@CrossOrigin(origins = "*")

public class AuthController {

	private static Logger logger = LogManager.getLogger(AuthController.class);
	@Autowired
	MasterService masterService;

	@Autowired
	AuthService authService;

	@Autowired
	SmsHelper smsHelper;
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@GetMapping(value = "/home")
	public String privateEndpoint(Principal principal) {
		User user = this.authService.getUserDetailsByMobileNumber(principal.getName());
		return user.getUser_name();
	}

	@GetMapping("/tehsil")
	public BaseResponseHelper<List<TehsilDto>> getTehsil(@RequestParam(defaultValue = "hi") String lang)
			throws CustomeException {
		logger.trace("Authconrtoller, tehsil started");

		List<TehsilDto> tehsillist = this.masterService.getTehsil(lang);

		logger.info("lang" + lang);
		logger.info("district api controller");
		if (tehsillist.size() > 0) {
			logger.trace("Authconrtoller, tehsil Ended");

			return new BaseResponseHelper<List<TehsilDto>>(HttpStatus.OK.value(), ResponseMessages.FETCHED_SUCCESSFULLY,
					tehsillist);
		} else {
			logger.trace("Authconrtoller, tehsil Ended");

			throw new CustomeException(ResponseMessages.NO_RECORDS);
		}
	}

	@PostMapping("/register-user")
	public BaseResponseHelper<User> userRegistration(@Valid @RequestBody User userdata) throws CustomeException {
		logger.trace("Authconrtoller, register-user started");

		User user = this.authService.userRegistration(userdata);
		if (user != null) {
			logger.trace("Authconrtoller, register-user Ended");

			return new BaseResponseHelper<User>(HttpStatus.OK.value(), ResponseMessages.FETCHED_SUCCESSFULLY, user);
		} else {
			logger.trace("Authconrtoller, register-user Ended");

			throw new CustomeException(ResponseMessages.USER_ALREADY_EXIST);
		}
	}

	@GetMapping("/test")
	public String test(@RequestParam String mobileNumber) {
		Object object = smsHelper.sendSms("91" + mobileNumber, "4564");
		if (object != null) {

		}
		return "This is for testing purpose\n OTP->";
	}

	@PostMapping(value = "/verify-otp")
	public BaseResponseHelper<JwtResponse> verifyOtp(@Valid @RequestBody UserOtp userOtp) throws CustomeException {
		// TODO: process POST request
		logger.trace("Authconrtoller, verify-otp started");

		String result = this.authService.verifyOtp(userOtp);
		if (result.equals("ERROR_1")) {
			throw new CustomeException(ResponseMessages.ERROR_1);
		} else {
			if (result.equals("ERROR_2")) {
				throw new CustomeException(ResponseMessages.ERROR_2);
			} else {
				User user = this.authService.getUserDetails(userOtp.getUser_id());

				return new BaseResponseHelper<JwtResponse>(HttpStatus.OK.value(), ResponseMessages.REFRESH_TOKEN_SUCCESS,
						new JwtResponse(result, user));

			}

		}

	}

	@GetMapping("/login")
	public BaseResponseHelper<LoginDto> login(@RequestParam(name = "mobileNumber") String No) throws CustomeException {
		logger.trace("Authconrtoller, login started");

		String result = this.authService.login(No);
		if (result.equals("ERROR_3")) {
			throw new CustomeException(ResponseMessages.ERROR_3);
		} else {
			if (result.equals("ERROR_4")) {
				throw new CustomeException(ResponseMessages.ERROR_4);
			} else {
				LoginDto loginDto = new LoginDto(result, No);
				return new BaseResponseHelper<LoginDto>(HttpStatus.OK.value(), ResponseMessages.FETCHED_SUCCESSFULLY, loginDto);

			}
		}
	}

	@GetMapping("/is-token-valid")
	public JSONObject istokenValid(HttpServletRequest request) {
		logger.trace("Authconrtoller, is-token-valid started");
		JSONObject obj = new JSONObject();
		String t = request.getHeader("Authorization");
		String token = t.substring(7);
		logger.info("REquest Token->" + token);
		boolean result = this.authService.isTokenValid(token);
		logger.trace("Authconrtoller, is-token-valid Ended");
		obj.put("isTokenValid", result);

		return obj;
	}

	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public BaseResponseHelper<JwtResponse> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		logger.trace("Authconrtoller, refreshtoken started");

		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");
		logger.info("claims-" + claims);
		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		logger.trace("Authconrtoller, refreshtoken Ended" + expectedMap.get("sub").toString());
		String mobileNumber = expectedMap.get("sub").toString();
		User user = this.authService.getUserDetailsByMobileNumber(mobileNumber);

		return new BaseResponseHelper<JwtResponse>(HttpStatus.OK.value(), ResponseMessages.REFRESH_TOKEN_SUCCESS,
				new JwtResponse(token, user));

	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		logger.trace("Authconrtoller, getMapFromIoJsonwebtokenClaims started");

		Map<String, Object> expectedMap = new HashMap<String, Object>();
		logger.info("getMapFrom");
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());

		}
		logger.info("ExpectedMap" + expectedMap);
		logger.trace("Authconrtoller, getMapFromIoJsonwebtokenClaims ended");
		return expectedMap;
	}

	@GetMapping("/tehsils")
	public BaseResponseHelper<List<TehsilDto>> getTehsilSearch(@RequestParam(defaultValue = "en") String lang,
			@RequestParam String keyword) throws CustomeException {
		logger.trace("Authconrtoller, tehsil started");

		List<TehsilDto> tehsillist = this.masterService.getTehsilSearch(lang, keyword);

		logger.info("lang" + lang);
		logger.info("district api controller");
		if (tehsillist.size() > 0) {
			logger.trace("Authconrtoller, tehsil Ended");

			return new BaseResponseHelper<List<TehsilDto>>(HttpStatus.OK.value(), ResponseMessages.FETCHED_SUCCESSFULLY,
					tehsillist);
		} else {
			logger.trace("Authconrtoller, tehsil Ended");

			throw new CustomeException(ResponseMessages.NO_RECORDS);
		}
	}

}