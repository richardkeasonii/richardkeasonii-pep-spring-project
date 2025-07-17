package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }


    public Message createMessage(Message message){
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message text cannot be blank");
        }

        if (message.getMessageText().length() > 255){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message text must be 255 characters or fewer");
        }

        Integer postedById = message.getPostedBy();
        if (accountRepository.findById(postedById).isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account does not exist");
        }

        return messageRepository.save(message);
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId){
        return messageRepository.findById(messageId).orElse(null);
    }

    public int deleteMessageById(Integer messageId){
        if (messageRepository.existsById(messageId)){
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public int updateMessageById(Integer messageId, Message message){
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message to update does not exist");
        }

        String newMessageText = message.getMessageText();
        if (newMessageText == null || newMessageText.trim().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message text does not exist");
        }

        if (newMessageText.length() > 255){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Message text must be 255 characters or fewer");
        }

        Message existing = existingMessage.get();
        existing.setMessageText(newMessageText);

        messageRepository.save(existing);

        return 1;
    }

    public List<Message> getMessagesByAccount(Integer accountId){
        return messageRepository.findByPostedBy(accountId);
    }
    
}
