package com.sosadwaden.forum.service;

import com.sosadwaden.forum.api.request.TopicPOSTRequest;
import com.sosadwaden.forum.api.response.TopicResponse;

import java.util.List;

public interface TopicService {

    List<TopicResponse> findAll();

    Long createTopic(TopicPOSTRequest topicPOSTRequest);

}
