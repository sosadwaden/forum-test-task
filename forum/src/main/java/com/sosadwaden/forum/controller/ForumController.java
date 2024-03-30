package com.sosadwaden.forum.controller;

import com.sosadwaden.forum.api.request.MessagePUTRequest;
import com.sosadwaden.forum.api.request.MessagePOSTRequest;
import com.sosadwaden.forum.api.request.TopicPOSTRequest;
import com.sosadwaden.forum.api.request.TopicPUTRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.api.response.TopicResponse;
import com.sosadwaden.forum.service.MessageService;
import com.sosadwaden.forum.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Main forum Controller", description = "Only authorized users have access to this controller")
@Validated
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ForumController {

    private final TopicService topicService;
    private final MessageService messageService;

    @Operation(
            summary = "Find all",
            description = "Find all the topics and messages in it"
    )
    @ApiResponse(responseCode = "200", description = "The data was received successfully")
    @GetMapping("${application.endpoint.topic}")
    public ResponseEntity<List<TopicResponse>> findAll(@RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                                       @RequestParam(value = "limit", defaultValue = "1") @Min(1) @Max(100) Integer limit) {
        return ResponseEntity.ok().body(topicService.findAll(offset, limit));
    }

    @Operation(
            summary = "Find messages",
            description = "Find all messages in topic by topicId"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Messages was received successfully"),
            @ApiResponse(responseCode = "404", description = "Messages not found, bad topicId")
    })
    @GetMapping("${application.endpoint.topic}/{topicId}")
    public ResponseEntity<List<MessageResponse>> findMessages(@Min(0) @PathVariable Long topicId,
                                                              @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                                              @RequestParam(value = "limit", defaultValue = "3") @Min(0) @Max(100) Integer limit) {
        return ResponseEntity.ok().body(messageService.findMessages(topicId, offset, limit));
    }

    @Operation(
            summary = "Create topic",
            description = "Creating a new topic"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic was created successfully"),
            @ApiResponse(responseCode = "422", description = "Validation exception")
    })
    @PostMapping("${application.endpoint.topic}")
    public ResponseEntity<String> createTopic(@Valid @RequestBody TopicPOSTRequest topicPOSTRequest) {
        Long id = topicService.createTopic(topicPOSTRequest);
        return new ResponseEntity<>(String.format("Topic with id = %s was created", id), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Create message",
            description = "Creating a new message in topic by topicId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message was created successfully"),
            @ApiResponse(responseCode = "404", description = "Topic not found, bad topicId"),
            @ApiResponse(responseCode = "422", description = "Validation exception")
    })
    @PostMapping("${application.endpoint.topic}/{topicId}")
    public ResponseEntity<String> createMessage(@Min(0) @PathVariable Long topicId, @Valid @RequestBody MessagePOSTRequest messagePOSTRequest) {
        messageService.createMessage(topicId, messagePOSTRequest);
        return new ResponseEntity<>("Message created", HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update topic",
            description = "Update topic by topicId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic was updated successfully"),
            @ApiResponse(responseCode = "403", description = "User does not have rights to edit the topic"),
            @ApiResponse(responseCode = "404", description = "Topic not found, bad topicId"),
            @ApiResponse(responseCode = "422", description = "Validation exception")
    })
    @PutMapping("${application.endpoint.topic}/{topicId}")
    public ResponseEntity<TopicResponse> updateTopic(@Min(0) @PathVariable Long topicId, @Valid @RequestBody TopicPUTRequest topicPUTRequest) {
        TopicResponse topicResponse = topicService.updateTopic(topicId, topicPUTRequest);
        return new ResponseEntity<>(topicResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Update message",
            description = "Update message by topicId and messageId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message was updated successfully"),
            @ApiResponse(responseCode = "403", description = "This user does not have the rights to edit another user's message"),
            @ApiResponse(responseCode = "404", description = "Topic not found, bad topicId"),
            @ApiResponse(responseCode = "404", description = "Message not found, bad messageId"),
            @ApiResponse(responseCode = "422", description = "Validation exception")
    })
    @PutMapping("${application.endpoint.topic}/{topicId}/{messageId}")
    public ResponseEntity<MessageResponse> updateMessage(@Min(0) @PathVariable Long topicId, @PathVariable Long messageId, @Valid @RequestBody MessagePUTRequest messagePUTRequest) {
        MessageResponse messageResponse = messageService.updateMessage(topicId, messageId, messagePUTRequest);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete topic",
            description = "Delete topic by topicId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Topic was deleted successfully"),
            @ApiResponse(responseCode = "403", description = "User does not have the right to delete the topic"),
            @ApiResponse(responseCode = "404", description = "Topic not found, bad topicId")
    })
    @DeleteMapping("${application.endpoint.topic}/{topicId}")
    public ResponseEntity<String> deleteTopic(@Min(0) @PathVariable Long topicId) {
        topicService.deleteTopic(topicId);
        return new ResponseEntity<>(String.format("Topic with id = %s deleted successfully", topicId), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete message",
            description = "Delete message by messageId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The message was deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Only the creator of this message has the right to delete it"),
            @ApiResponse(responseCode = "404", description = "Topic not found, bad topicId"),
            @ApiResponse(responseCode = "404", description = "Message not found, bad topicId")
    })
    @DeleteMapping("${application.endpoint.message}/{messageId}")
    public ResponseEntity<String> deleteMessage(@Min(0) @PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>(String.format("Message with id = %s deleted successfully", messageId), HttpStatus.OK);
    }

}
