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
import com.act.smms.enums.ISP;
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
            String username = config.getUsername();
            int from = config.getShortNumber();
            String password = config.getPassword();
            List<String> recipients = request.getRecipient();
            String message = request.getMessage();
            String smsGatewayUrl = "http://localhost:8000/api/cgi-bin/sendsms";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> map = new HashMap<>();
            map.put("text", message);
            map.put("username", username);
            map.put("password", password);
            map.put("from", from);

            if (recipients == null) {
                return false;
            }
            ResponseEntity<StatusRequest> response = null;

            for (String recipient : recipients) {
                Status status = new Status();
                if (phoneVerifier.isValid(recipient)) {
                    // if the provided recipient number is not one of the two providers'
                    if (!(phoneVerifier.getISP(recipient) == ISP.ETHIOTEL
                            || phoneVerifier.getISP(recipient) == ISP.SAFARICOM)) {
                        status.setStatus(SMSStatus.FAILD);
                        status.setMessage(message);
                        status.setSender(String.valueOf(from));
                        status.setRecipient(recipient);
                        status.setDate(LocalDateTime.now());
                        status.setRemark("the number does not refer to a known ISP");
                        statusRepo.save(status);
                        continue;
                    }

                    map.put("to", recipient);
                    response = this.restTemplate.postForEntity(smsGatewayUrl, new HttpEntity<>(map, headers),
                            StatusRequest.class);
                    StatusRequest statusRequest = response.getBody();
                    status.setStatus(statusRequest.getStatus());
                    status.setMessage(statusRequest.getMessage());
                    status.setSender(statusRequest.getSender());
                    status.setRecipient(statusRequest.getRecipient());
                    status.setDate(LocalDateTime.now());
                    status.setRemark("remark from the server");
                    statusRepo.save(status);
                } else {
                    status.setStatus(SMSStatus.FAILD);
                    status.setMessage(message);
                    status.setSender(String.valueOf(from));
                    status.setRecipient(recipient);
                    status.setDate(LocalDateTime.now());
                    status.setRemark("the provided phone number is invalid");
                    statusRepo.save(status);
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
