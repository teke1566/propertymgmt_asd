package edu.miu.PropertyManagement.service.impl;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    //@Value("${spring.mail.username}") private String sender;


    public void sendEmail(String to, String subject, String body)
    {

        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom("teketsel.beyene@gmail.com");
            mailMessage.setTo(to);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);
            // Sending the mail
            mailSender.send(mailMessage);
        }

        catch (Exception e) {
        }
    }
}
