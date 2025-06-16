package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
public class SocialMediaController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private AccountService accountService;

    // User Login
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account body) {
        String username = body.getUsername();
        String password = body.getPassword();
        
        Account result = accountService.login(username, password);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(401).body(result);
    }

    // User Registration
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account body) {
        String username = body.getUsername();
        if (username.isBlank()                             // username is blank
            || body.getPassword().length() < 4             // password length req
            ) {
            return ResponseEntity.status(400).body(null);
        }
        if (accountService.usernameExists(username)){
            return ResponseEntity.status(409).body(null);
        }

        Account result = accountService.create(body);
        if (result != null) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(400).body(result);
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer account_id){
        List<Message> msgs = messageService.getMessagesByAccount(account_id);
        return ResponseEntity.status(200).body(msgs);
    }

    // User Creates/Adds Message
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message msg) {
        int userid = msg.getPostedBy();
        if (msg.getMessageText().isBlank()          // message text is blank
            || msg.getMessageText().length() > 255  // message length exceeds limit
            || !accountService.isValidId(userid)) { // no user with that id
            return ResponseEntity.status(400).body(null);
        } 
            // insert message into database
            Message addedMessage = messageService.insertMessage(msg);

            // return message thats added
            return ResponseEntity.status(200).body(addedMessage);
    }

    // User Retrieves All of Their Messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {       
        List<Message> msgList = messageService.getMessages();
        return ResponseEntity.ok(msgList);
    }

    // User gets single message by Id
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id) {
        // retrieve Message here
        Message foundMessage = messageService.getMessageById(message_id);
        if (foundMessage != null) {
            return ResponseEntity.status(200).body(foundMessage);
        }
        return ResponseEntity.ok(null);
    }

    // User updates message by id
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable Integer message_id, @RequestBody Message message){
        // message id needs validation
        Message prevMessage = messageService.getMessageById(message_id);
        if (prevMessage == null) {
            return ResponseEntity.status(400).body(0);
        }
        // message text cannot be blank
        String text = message.getMessageText();
        if (text.isBlank() || text.length() > 255) {
            return ResponseEntity.status(400).body(0);
        }
        Integer rowsEffected = messageService.updateMessage(message_id, message);
        return ResponseEntity.status(200).body(rowsEffected);
    }

    // User deletes single message by Id
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer message_id){
        // message id validation
        Message validMessage = messageService.getMessageById(message_id);
        if (validMessage == null) {
            return ResponseEntity.status(200).body(null);
        }
        Integer rowsEffected = messageService.deleteMessage(message_id);
        return ResponseEntity.status(200).body(rowsEffected);
    }

}
