package com.abnerkaizer.rest_springboot_java.service;

import com.abnerkaizer.rest_springboot_java.config.EmailConfig;
import com.abnerkaizer.rest_springboot_java.data.dto.request.EmailRequestDTO;
import com.abnerkaizer.rest_springboot_java.mail.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EmailConfig emailConfig;

    public void sendSimpleEmail(EmailRequestDTO emailRequest){
        emailSender
                .to(emailRequest.getTo())
                .withSubject(emailRequest.getSubject())
                .withMessage(emailRequest.getBody())
                .send(emailConfig);
    }
}
