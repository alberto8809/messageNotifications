package org.example.message.controller;


import org.example.message.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class MessageController {


    @Autowired
    private MessageService messageService;


    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("user/mail")
    public void sendMail() {
        messageService.sendMessage("");
        messageService.sendSMS();

    }


}
