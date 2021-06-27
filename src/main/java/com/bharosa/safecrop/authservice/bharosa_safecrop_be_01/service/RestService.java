package com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.service;

import java.util.Map;

import com.bharosa.safecrop.authservice.bharosa_safecrop_be_01.constants.APIconstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    Logger logger = LogManager.getLogger(RestService.class);

    @Autowired
    public RestTemplate restTemplate;

    public Object postObject(String url, Map<String, Object> map) {
        logger.trace("postObject started!");
        logger.info("Map data!" + map.toString());
        logger.info("Url data!" + url);

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map);
        // send POST request
        ResponseEntity<Object> response = restTemplate.postForEntity(url, entity, Object.class);
        // check response status code
        if (response.getStatusCode() == HttpStatus.CREATED) {
            logger.info("response: " + response.getBody());
            return response.getBody();
        } else {
            return null;
        }

    }
}
