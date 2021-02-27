package com.zack.projects.chatapp.conversationservice.service;

import com.zack.projects.chatapp.conversationservice.dto.Message;
import com.zack.projects.chatapp.conversationservice.entity.Conversation;
import com.zack.projects.chatapp.conversationservice.repository.ConversationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

@Service
@Slf4j
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    public Collection<Message> getConversation(String userA, String userB) {

        // TODO: rest call to user service to check if userA and userB exist

        Conversation conversation;
        Timestamp dateStarted = new Timestamp(System.currentTimeMillis());

        log.info(String.format
                ("Retrieving conversation between [%s] and [%s]", userA, userB));

        conversation = conversationRepository.findConversationByConversationId(userA, userB);

        if(conversation != null) {
            // TODO: rest request to message service to get conversation messages and send a list of messages
            return new ArrayList<>();
        }

        log.info(String.format
                ("Conversation between [%s] and [%s] does not exist yet, " +
                        "creating one and returning an empty list of messages", userA, userB));

        conversation = new Conversation(userA, userB, dateStarted);
        conversationRepository.save(conversation);
        return new ArrayList<>();

    }
}
