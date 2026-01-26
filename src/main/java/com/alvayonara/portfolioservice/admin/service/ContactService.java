package com.alvayonara.portfolioservice.admin.service;

import com.alvayonara.portfolioservice.admin.dto.ContactRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ContactService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String recipient;

    public Mono<Void> send(ContactRequest request) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(recipient);
            mail.setSubject("[Portfolio] " + request.subject());
            mail.setReplyTo(request.email());
            mail.setText("""
                    Name: %s
                    Email: %s

                    Message:
                    %s
                    """.formatted(
                    request.name(),
                    request.email(),
                    request.message()
            ));
            javaMailSender.send(mail);
        });
    }
}
