package com.sosadwaden.forum.controller;

import com.sosadwaden.forum.api.request.MessagePUTRequest;
import com.sosadwaden.forum.api.request.MessagePOSTRequest;
import com.sosadwaden.forum.api.request.TopicPOSTRequest;
import com.sosadwaden.forum.api.request.TopicPUTRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.api.response.TopicResponse;
import com.sosadwaden.forum.service.MessageService;
import com.sosadwaden.forum.service.TopicService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ForumController {

    private final TopicService topicService;
    private final MessageService messageService;

    @GetMapping("${application.endpoint.topic}")
    public ResponseEntity<List<TopicResponse>> findAll(@RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                                       @RequestParam(value = "limit", defaultValue = "1") @Min(1) @Max(100) Integer limit) {
        return ResponseEntity.ok().body(topicService.findAll(offset, limit));
    }

    @GetMapping("${application.endpoint.topic}/{topicId}")
    public ResponseEntity<List<MessageResponse>> findMessages(@Min(0) @PathVariable Long topicId,
                                                              @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                                              @RequestParam(value = "limit", defaultValue = "1") @Min(1) @Max(100) Integer limit) {
        return ResponseEntity.ok().body(messageService.findMessages(topicId, offset, limit));
    }

    @PostMapping("${application.endpoint.topic}")
    public ResponseEntity<String> createTopic(@Valid @RequestBody TopicPOSTRequest topicPOSTRequest) {
        Long id = topicService.createTopic(topicPOSTRequest);
        return new ResponseEntity<>(String.format("Topic with id = %s was created", id), HttpStatus.CREATED);
    }

    // TODO при создании топика если ввести неправильное имя то ничего не происходит, поэтому необходимо обработать исключение и вернуть ответ
    @PostMapping("${application.endpoint.topic}/{topicId}")
    public ResponseEntity<String> createMessage(@Min(0) @PathVariable Long topicId, @Valid @RequestBody MessagePOSTRequest messagePOSTRequest) {
        messageService.createMessage(topicId, messagePOSTRequest);
        return new ResponseEntity<>("Message created", HttpStatus.CREATED);
    }

    @PutMapping("${application.endpoint.topic}/{topicId}")
    public ResponseEntity<TopicResponse> updateTopic(@Min(0) @PathVariable Long topicId, @Valid @RequestBody TopicPUTRequest topicPUTRequest) {
        TopicResponse topicResponse = topicService.updateTopic(topicId, topicPUTRequest);
        return new ResponseEntity<>(topicResponse, HttpStatus.OK);
    }

    @PutMapping("${application.endpoint.topic}/{topicId}/{messageId}")
    public ResponseEntity<MessageResponse> updateMessage(@Min(0) @PathVariable Long topicId, @PathVariable Long messageId, @Valid @RequestBody MessagePUTRequest messagePUTRequest) {
        MessageResponse messageResponse = messageService.updateMessage(topicId, messageId, messagePUTRequest);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @DeleteMapping("${application.endpoint.topic}/{topicId}")
    public ResponseEntity<String> deleteTopic(@Min(0) @PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
        return new ResponseEntity<>(String.format("Topic with id = %s deleted successfully", topicId), HttpStatus.OK);
    }

    @DeleteMapping("${application.endpoint.message}/{messageId}")
    public ResponseEntity<String> deleteMessage(@Min(0) @PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>(String.format("Message with id = %s deleted successfully", messageId), HttpStatus.OK);
    }

}
