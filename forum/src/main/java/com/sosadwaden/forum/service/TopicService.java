package com.sosadwaden.forum.service;

import com.sosadwaden.forum.api.request.TopicRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.api.response.TopicResponse;

import java.util.List;
import java.util.Optional;

// TODO непонятно, должна быть возможность получения одно сообщения|всех|и то и то
public interface TopicService {

    List<TopicResponse> findAll();

    List<MessageResponse> findMessages(Long id);

    Long create(TopicRequest topicRequest);

}
