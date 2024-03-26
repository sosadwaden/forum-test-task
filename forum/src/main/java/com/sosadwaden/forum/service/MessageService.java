package com.sosadwaden.forum.service;

import com.sosadwaden.forum.api.request.MessageRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO пересмотреть т.к. при получении сообщений нужно знать ещё и топик
public interface MessageService {

    Optional<MessageResponse> findById(Long id);

    List<MessageResponse> findAll();

    Long create(MessageRequest messageRequest);

    Optional<MessageResponse> update(Long id, MessageRequest messageRequest);

    void deleteById(Long id);
}
