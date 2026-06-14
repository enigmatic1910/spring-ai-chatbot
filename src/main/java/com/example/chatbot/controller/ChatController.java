package com.example.chatbot.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    @PostMapping("/chat")
    public ResponseEntity<String> response(@RequestBody String message){
        String response = chatClient.prompt()
                .user(message)
                .call()
                .content();

        return ResponseEntity.ok(response);
    }

}
