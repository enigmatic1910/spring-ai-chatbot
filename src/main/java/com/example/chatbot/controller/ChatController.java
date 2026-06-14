package com.example.chatbot.controller;

import com.example.chatbot.dto.ChatRequestDto;
import com.example.chatbot.dto.ChatResponseDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder.defaultAdvisors(MessageChatMemoryAdvisor
                .builder(chatMemory)
                .build())
                .build();
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponseDto> response(@RequestBody ChatRequestDto request){
        ChatResponseDto response = new ChatResponseDto(
                chatClient.prompt()
                        .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "default"))
                        .system("You are a helpful java tutor")
                        .user(request.message())
                        .call()
                        .content()
        );

        return ResponseEntity.ok(response);
    }

}
