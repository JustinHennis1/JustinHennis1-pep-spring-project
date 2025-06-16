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

    public Message getMessageById(Integer messageId) {
        if (messageId == null) {return null;}
        return messageDAO.findById(messageId).orElse(null);
    }

    public Integer deleteMessage(int messageId) {
        return messageDAO.deleteMessageByIdFromDatabase(messageId);
    }

    public Integer updateMessage(Integer id, Message message) {
        Integer postedBy = message.getPostedBy();
        String text = message.getMessageText();
        Long time = message.getTimePostedEpoch();

        Message currentMessage = this.getMessageById(id);

        if(!(text == null) && !(postedBy == null) && !(time == null)) { // three fields provided
            // update all three
            return messageDAO.updateMessageById(id,postedBy,text,time);
        } else if(!(text == null) && !(postedBy == null)) {             // two fields provided
            time = currentMessage.getTimePostedEpoch(); // time was not provided
            return messageDAO.updateMessageById(id, postedBy, text, time);
        } else if(!(postedBy == null) && !(time == null)) {
            text = currentMessage.getMessageText();     // text was not provided
            return messageDAO.updateMessageById(id, postedBy, text, time);
        } else if(!(text == null) && !(time == null)) {
            postedBy = currentMessage.getPostedBy();    // posted by was not provided
            return messageDAO.updateMessageById(id, postedBy, text, time);
        } else if (text != null) {                                       // one field provided
            time = currentMessage.getTimePostedEpoch();
            postedBy = currentMessage.getPostedBy();
            return messageDAO.updateMessageById(id, postedBy, text, time);
        } else if (postedBy != null) {
            time = currentMessage.getTimePostedEpoch();
            text = currentMessage.getMessageText();
            return messageDAO.updateMessageById(id, postedBy, text, time);
        } else if (time != null) {
            text = currentMessage.getMessageText();
            postedBy = currentMessage.getPostedBy();
            return messageDAO.updateMessageById(id, postedBy, text, time);
        }

        // all are null
        return null;
    }
}
