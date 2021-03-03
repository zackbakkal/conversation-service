package com.zack.projects.chatapp.conversationservice.service;

import com.zack.projects.chatapp.conversationservice.dto.Message;
import com.zack.projects.chatapp.conversationservice.entity.Conversation;
import com.zack.projects.chatapp.conversationservice.repository.ConversationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Collection<Message> getConversation(String userA, String userB) {

        boolean userAIsRegistered =
                restTemplate.getForObject("http://USER-SERVICE/users/registered/" + userA, Boolean.class);

        boolean userBIsRegistered =
                restTemplate.getForObject("http://USER-SERVICE/users/registered/" + userB, Boolean.class);

        if(userAIsRegistered && userBIsRegistered) {
            Conversation conversation;
            Timestamp dateStarted = new Timestamp(System.currentTimeMillis());

            log.info(String.format("Retrieving conversation between [%s] and [%s]", userA, userB));
            conversation = conversationRepository.findConversationByConversationId(userA, userB);

            if(conversation != null) {
                return restTemplate.getForObject(
                        "http://MESSAGE-SERVICE/messages/load/" + userA + "/" + userB, List.class);
            }

            log.info(String.format
                    ("Conversation between [%s] and [%s] does not exist yet, " +
                            "creating one and returning an empty list of messages", userA, userB));

            conversation = new Conversation(userA, userB, dateStarted);
            conversationRepository.save(conversation);
            return new ArrayList<>();
        }

        return null;

    }

    public Collection<Message> getMessagesByNumber(String userA, String userB, int from, int to) {

        boolean userAIsRegistered =
                restTemplate.getForObject("http://USER-SERVICE/users/registered/" + userA, Boolean.class);

        boolean userBIsRegistered =
                restTemplate.getForObject("http://USER-SERVICE/users/registered/" + userB, Boolean.class);

        if(userAIsRegistered && userBIsRegistered) {
            Conversation conversation;
            Timestamp dateStarted = new Timestamp(System.currentTimeMillis());

            log.info(String.format("Retrieving conversation between [%s] and [%s]", userA, userB));
            conversation = conversationRepository.findConversationByConversationId(userA, userB);

            if(conversation != null) {
                return restTemplate.getForObject(
                        "http://MESSAGE-SERVICE/messages/load/" + userA + "/" + userB + "/" + from + "/" + to,
                        List.class);
            }

            log.info(String.format
                    ("Conversation between [%s] and [%s] does not exist yet, " +
                            "creating one and returning an empty list of messages", userA, userB));

            conversation = new Conversation(userA, userB, dateStarted);
            conversationRepository.save(conversation);
            return new ArrayList<>();
        }

        return null;

    }
}
