package com.sosadwaden.forum.service;

import com.sosadwaden.forum.api.request.MessagePUTRequest;
import com.sosadwaden.forum.api.request.MessageRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO пересмотреть т.к. при получении сообщений нужно знать ещё и топик
public interface MessageService {

    List<MessageResponse> findMessages(Long topicId);

    void createMessage(Long topicId, MessageRequest messageRequest);

    MessageResponse updateMessage(Long topicId, Long messageId, MessagePUTRequest messageRequest);

    void deleteMessage(Long messageId);
}
