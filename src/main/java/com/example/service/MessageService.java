package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageDAO;

    public Message insertMessage(Message msg) {
        return messageDAO.save(msg);
    }

    public List<Message> getMessagesByAccount(int id) {
        return messageDAO.getAllMessagesByAccountId(id);
    }

    public List<Message> getMessages() {
        return messageDAO.getAllMessagesFromDatabase();
    }

    public Message getMessageById(int messageId) {
        return messageDAO.getMessageByIdFromDatabase(messageId).orElse(null);
    }

    public Integer deleteMessage(int messageId) {
        return messageDAO.deleteMessageByIdFromDatabase(messageId);
    }

    public Integer updateMessage(Message message) {
        int id = message.getMessageId();
        int postedBy = message.getPostedBy();
        String text = message.getMessageText();
        long time = message.getTimePostedEpoch();
        return messageDAO.updateMessageById(id,postedBy,text,time);
    }
}
