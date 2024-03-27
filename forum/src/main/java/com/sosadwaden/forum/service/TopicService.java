package com.sosadwaden.forum.service;

import com.sosadwaden.forum.api.request.MessageRequest;
import com.sosadwaden.forum.api.request.TopicRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.api.response.TopicResponse;

import java.util.List;

// TODO непонятно, должна быть возможность получения одно сообщения|всех|и то и то
public interface TopicService {

    List<TopicResponse> findAll();

    //List<MessageResponse> findMessages(Long topicId);

    Long createTopic(TopicRequest topicRequest);

    //boolean createMessage(Long topicId, MessageRequest messageRequest);

}
