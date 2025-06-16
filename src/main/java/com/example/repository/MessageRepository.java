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
    // create table message (
    //     messageId int primary key auto_increment,
    //     postedBy int,
    //     messageText varchar(255),
    //     timePostedEpoch bigint,
    //     foreign key (postedBy) references account(accountId)
    // );
    
    // get all messages
    @Query("SELECT m FROM Message m")
    List<Message> getAllMessagesFromDatabase();

    // get all messages
    @Query("SELECT m FROM Message m WHERE m.postedBy = ?1")
    List<Message> getAllMessagesByAccountId(Integer id);

    // get messages by id
    @Query("SELECT m FROM Message m WHERE m.messageId = ?1")
    Optional<Message> getMessageByIdFromDatabase(Integer messageId);

    // delete messages
    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.messageId = ?1")
    int deleteMessageByIdFromDatabase(Integer messageId);

    // update message
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageId = ?1, m.postedBy = ?2, m.messageText = ?3, m.timePostedEpoch = ?4")
    int updateMessageById(Integer messageId, Integer postedBy, String messageText, Long timePostedEpoch);
}
