package com.zack.projects.chatapp.conversationservice.controller;

import com.zack.projects.chatapp.conversationservice.dto.Message;
import com.zack.projects.chatapp.conversationservice.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @GetMapping("/{userA}/{userB}")
    public Collection<Message> getConversation(@PathVariable String userA, @PathVariable String userB) {
        return conversationService.getConversation(userA, userB);
    }

}
