package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    // get all messages
    @Query("SELECT m FROM Message m")
    List<Message> getAllMessagesFromDatabase();

    // get all messages
    @Query("SELECT m FROM Message m WHERE m.postedBy = ?1")
    List<Message> getAllMessagesByAccountId(Integer id);

    // delete messages
    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.messageId = ?1")
    int deleteMessageByIdFromDatabase(Integer messageId);

    // update message
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.postedBy = ?2, m.messageText = ?3, m.timePostedEpoch = ?4 WHERE m.messageId = ?1")
    int updateMessageById(Integer messageId, Integer postedBy, String messageText, Long timePostedEpoch);
}
