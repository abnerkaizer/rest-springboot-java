package com.abnerkaizer.rest_springboot_java.controllers;

import com.abnerkaizer.rest_springboot_java.controllers.docs.EmailControllerDocs;
import com.abnerkaizer.rest_springboot_java.data.dto.request.EmailRequestDTO;
import com.abnerkaizer.rest_springboot_java.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/email/v1")
public class EmailController implements EmailControllerDocs {

    @Autowired
    private EmailService service;

    @PostMapping
    @Override
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequest) {
        service.sendSimpleEmail(emailRequest);
        return new ResponseEntity<>("e-Mail sent with success!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> sendEmailWithAttachment(String emailRequestJson, MultipartFile multipartFile) {
        return null;
    }
}
