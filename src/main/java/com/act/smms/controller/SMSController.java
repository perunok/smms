package com.act.smms.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.act.smms.dto.SMSRequest;
import com.act.smms.model.ISPConfig;
import com.act.smms.service.SMSHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class SMSController {
    SMSHandler handler;

    public SMSController(SMSHandler handler) {
        this.handler = handler;
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<SMSRequest> postMethodName(@RequestBody SMSRequest request) {
        if (handler.sendMessage(request)) {
            return ResponseEntity.ok(request);
        }
        return new ResponseEntity<>(request, HttpStatus.EXPECTATION_FAILED);

    }

    @PostMapping("/configISP")
    public ResponseEntity<String> configISP(@RequestBody ISPConfig config) {
        if (handler.configISP(config)) {
            return ResponseEntity.ok("sucess");
        }
        return new ResponseEntity<>("failed", HttpStatus.CONFLICT);
    }

}
