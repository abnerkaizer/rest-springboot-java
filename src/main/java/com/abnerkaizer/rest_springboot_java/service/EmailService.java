package com.abnerkaizer.rest_springboot_java.service;

import com.abnerkaizer.rest_springboot_java.config.EmailConfig;
import com.abnerkaizer.rest_springboot_java.data.dto.request.EmailRequestDTO;
import com.abnerkaizer.rest_springboot_java.mail.EmailSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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

    public void sendEmailWithAttachment(String emailRequestJson, MultipartFile attachment){
        File tempfile = null;
        try {
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);
            tempfile = File.createTempFile("attachment", attachment.getOriginalFilename());
            attachment.transferTo(tempfile);

            emailSender
                    .to(emailRequest.getTo())
                    .withSubject(emailRequest.getSubject())
                    .withMessage(emailRequest.getBody())
                    .attach(tempfile.getAbsolutePath())
                    .send(emailConfig);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing email request JSON",e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing the attachment",e);
        }finally {
            if (tempfile != null && tempfile.exists()) tempfile.delete();
        }
    }
}
