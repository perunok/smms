package com.act.smms.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.act.smms.dto.SMSRequest;
import com.act.smms.dto.StatusRequest;
import com.act.smms.enums.ISPConfigOption;
import com.act.smms.enums.SMSStatus;
import com.act.smms.model.ISPConfig;
import com.act.smms.model.Status;
import com.act.smms.repository.ISPConfigRepo;
import com.act.smms.repository.StatusRepo;

@Service
public class SMSHandler {

    ISPConfigRepo ispConfigRepo;
    StatusRepo statusRepo;
    PhoneVerifier phoneVerifier;
    final RestTemplate restTemplate;

    public SMSHandler(ISPConfigRepo ispConfigRepo, RestTemplateBuilder restTemplateBuilder, StatusRepo statusRepo,
            PhoneVerifier phoneVerifier) {
        this.ispConfigRepo = ispConfigRepo;
        this.statusRepo = statusRepo;
        this.phoneVerifier = phoneVerifier;
        this.restTemplate = restTemplateBuilder.build();
    }

    @SuppressWarnings("null")
    public boolean sendMessage(SMSRequest request) {
        try {
            List<ISPConfig> configs = ispConfigRepo.findByOption(ISPConfigOption.DEFAULT);
            if (configs.isEmpty()) {
                System.out.println("ISP params not configured!");
                return false;
            }
            ISPConfig config = configs.getFirst();

            // Read the response (if needed)
            String username = config.getUsername();
            String password = config.getPassword();
            List<String> recipients = request.getRecipient();
            String message = request.getMessage();
            String smsGatewayUrl = "http://localhost:8000/api/cgi-bin/sendsms";

            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> map = new HashMap<>();
            map.put("text", message);
            map.put("username", username);
            map.put("password", password);

            if (recipients.isEmpty()) {
                return false;
            }
            ResponseEntity<StatusRequest> response = null;

            for (String recipient : recipients) {
                if (phoneVerifier.isValid(recipient)) {

                    map.put("to", recipient);
                    response = this.restTemplate.postForEntity(smsGatewayUrl, new HttpEntity<>(map, headers),
                            StatusRequest.class);
                    StatusRequest statusRequest = response.getBody();

                    statusRepo.save(new Status(statusRequest.getStatus(), statusRequest.getMessage(),
                            statusRequest.getSender(),
                            statusRequest.getRecipient(), LocalDateTime.now(), "this is a remark"));
                } else {
                    statusRepo.save(new Status(SMSStatus.FAILD, message, username, recipient, LocalDateTime.now(),
                            "the provided phone number is invalid"));
                }

            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean configISP(ISPConfig config) {
        try {
            ispConfigRepo.save(config);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
