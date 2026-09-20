package com.deep.docmind.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {

    public ResponseEntity<String> chat(){
        return ResponseEntity.ok("CHATTING");
    }

}
