package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.constants.APIconstants;
import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.service.RestService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsHelper {
	Logger logger = LogManager.getLogger(SmsHelper.class);

	@Value("${server.applications.sms_api_key}")
	private String smsApiKey;

	@Value("${server.applications.sms_client_id}")
	private String smsClientId;

	//@Value("${server.applications.sms_base_url}")
	private String smsBaseUrl = "https://api.mylogin.co.in/api/v2/";

	@Value("${server.applications.sms_sender_id}")
	private String smsSenderId;

	@Autowired
	private RestService restService;

	/**
	 * 
	 * @param mobileNumber
	 * @return
	 */
	public Object sendSms(String mobileNumber, String otp) {
		logger.trace("sendSms started!");
		Map<String, Object> map = new HashMap<>();
		map.put("SenderId", smsSenderId);
		map.put("Is_Unicode", false);
		map.put("Is_Flash", false);
		map.put("Message", String.format(APIconstants.EN_OTP_TEMPLATE, otp));
		map.put("MobileNumbers", mobileNumber);
		map.put("ApiKey", smsApiKey);
		map.put("ClientId", smsClientId);
		String sendSMSUrl = smsBaseUrl + APIconstants.SEND_SMS_ENDPOINT;
		// call rest service createObject function to send sms
		Object object = restService.postObject(sendSMSUrl, map);
		logger.info("object-" + object);
		logger.trace("sendSms ended!");
		return object;
	}

	/**
	 * 
	 * @return 4 digit otp string
	 */
	public static String generateOtp() {
		Random random = new Random();
		String otp = String.format("%04d", random.nextInt(10000));

		return otp;
	}

}
