package com.alvayonara.portfolioservice.admin.controller;

import com.alvayonara.portfolioservice.admin.dto.ContactRequest;
import com.alvayonara.portfolioservice.admin.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> submit(@RequestBody ContactRequest contactRequest) {
        return contactService.send(contactRequest);
    }
}
