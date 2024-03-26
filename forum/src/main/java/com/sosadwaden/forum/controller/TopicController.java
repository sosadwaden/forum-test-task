package com.sosadwaden.forum.controller;

import com.sosadwaden.forum.api.request.TopicRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.api.response.TopicResponse;
import com.sosadwaden.forum.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Optional<List<MessageResponse>>> findMessages(@PathVariable Long topicId) {
        return ResponseEntity.ok().body(topicService.findMessages(topicId));
    }



//    @PostMapping
//    public ResponseEntity<Long> create(@RequestBody TopicRequest topicRequest) {
//        Long id = topicService.create(topicRequest);
//    }

}
