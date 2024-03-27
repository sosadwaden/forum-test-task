package com.sosadwaden.forum.controller;

import com.sosadwaden.forum.api.request.MessagePUTRequest;
import com.sosadwaden.forum.api.request.MessageRequest;
import com.sosadwaden.forum.api.request.TopicRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.api.response.TopicResponse;
import com.sosadwaden.forum.service.MessageService;
import com.sosadwaden.forum.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;
    private final MessageService messageService;

    @GetMapping("${application.endpoint.topic}")
    public ResponseEntity<List<TopicResponse>> findAll() {
        return ResponseEntity.ok().body(topicService.findAll());
    }

    @GetMapping("${application.endpoint.topic}/{topicId}")
    public ResponseEntity<List<MessageResponse>> findMessages(@PathVariable Long topicId) {
        return ResponseEntity.ok().body(messageService.findMessages(topicId));
    }

    @PostMapping("${application.endpoint.topic}")
    public ResponseEntity<String> create(@RequestBody TopicRequest topicRequest) {
        Long id = topicService.createTopic(topicRequest);
        return new ResponseEntity<>("Topic with id = " + id + " was created", HttpStatus.CREATED);
    }

    @PostMapping("${application.endpoint.topic}/{topicId}")
    public ResponseEntity<String> createMessage(@PathVariable Long topicId, @RequestBody MessageRequest messageRequest) {
        messageService.createMessage(topicId, messageRequest);
        return new ResponseEntity<>("Message created", HttpStatus.CREATED);
    }

    @PutMapping("${application.endpoint.topic}/{topicId}/{messageId}")
    public ResponseEntity<MessageResponse> updateMessage(@PathVariable Long topicId, @PathVariable Long messageId, @RequestBody MessagePUTRequest messageRequest) {
        MessageResponse messageResponse = messageService.updateMessage(topicId, messageId, messageRequest);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @DeleteMapping("${application.endpoint.message}/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>("Message deleted successfully", HttpStatus.OK);
    }

}
