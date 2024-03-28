package com.sosadwaden.forum.service;

import com.sosadwaden.forum.api.request.MessagePUTRequest;
import com.sosadwaden.forum.api.request.MessagePOSTRequest;
import com.sosadwaden.forum.api.response.MessageResponse;

import java.util.List;

public interface MessageService {

    List<MessageResponse> findMessages(Long topicId);

    void createMessage(Long topicId, MessagePOSTRequest messagePOSTRequest);

    MessageResponse updateMessage(Long topicId, Long messageId, MessagePUTRequest messagePUTRequest);

    void deleteMessage(Long messageId);
}
