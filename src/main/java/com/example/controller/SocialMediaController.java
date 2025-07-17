package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public Account register(@RequestBody Account account){
        return accountService.register(account);
    }

    @PostMapping("/login")
    public Account login(@RequestBody Account account){
        return accountService.login(account);
    }

    @PostMapping("/messages")
    public Message createMessage(@RequestBody Message message){
        return messageService.createMessage(message);
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable Integer messageId){
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId){
        if (messageService.deleteMessageById(messageId) == 0) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok(1);
        }
    }

    @PatchMapping("/messages/{messageId}")
    public int updateMessageById(@PathVariable Integer messageId, @RequestBody Message message){
        return messageService.updateMessageById(messageId, message);
    }

    @GetMapping("/accounts/{accountId}/messages")
    public List<Message> getMessagesByAccount(@PathVariable Integer accountId){
        return messageService.getMessagesByAccount(accountId);
    }

}
