package com.example.chatbot.controller;

import com.example.chatbot.dto.ChatRequestDto;
import com.example.chatbot.dto.ChatResponseDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;
    @Autowired
    private final ChatMemory chatMemory;

    public ChatController(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory){
        this.chatClient = chatClientBuilder.build();
        this.chatMemory = chatMemory;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDto> response(@RequestBody ChatRequestDto request){
        ChatResponseDto response = new ChatResponseDto(
                chatClient.prompt()
                        .advisors(
                                MessageChatMemoryAdvisor.builder(chatMemory)
                                        .build()
                        )
                        .system("You are a helpful java tutor")
                        .user(request.message())
                        .call()
                        .content()
        );

        return ResponseEntity.ok(response);
    }

}
