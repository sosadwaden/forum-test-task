package com.sosadwaden.forum.controller;

import com.sosadwaden.forum.api.request.TopicRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.api.response.TopicResponse;
import com.sosadwaden.forum.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1" + "${application.endpoint.topic}")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping()
    public ResponseEntity<List<TopicResponse>> findAll() {
        return ResponseEntity.ok().body(topicService.findAll());
    }

    @GetMapping("/{topicId}")
    public ResponseEntity<List<MessageResponse>> findMessages(@PathVariable Long topicId) {
        return ResponseEntity.ok().body(topicService.findMessages(topicId));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody TopicRequest topicRequest) {
        Long id = topicService.create(topicRequest);
        return new ResponseEntity<>("Topic with id = " + id + " was created", HttpStatus.CREATED);
    }

}
