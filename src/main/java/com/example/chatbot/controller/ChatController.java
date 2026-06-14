package com.example.chatbot.controller;

import com.example.chatbot.dto.ChatRequestDto;
import com.example.chatbot.dto.ChatResponseDto;
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
    public ResponseEntity<ChatResponseDto> response(@RequestBody ChatRequestDto request){
        ChatResponseDto response = new ChatResponseDto(
                chatClient.prompt()
                        .user(request.message())
                        .call()
                        .content()
        );

        return ResponseEntity.ok(response);
    }

}
