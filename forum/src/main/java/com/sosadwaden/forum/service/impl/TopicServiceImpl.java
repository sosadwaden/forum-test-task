package com.sosadwaden.forum.service.impl;

import com.sosadwaden.forum.api.request.TopicRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.api.response.TopicResponse;
import com.sosadwaden.forum.entity.Topic;
import com.sosadwaden.forum.repository.MessageRepository;
import com.sosadwaden.forum.repository.TopicRepository;
import com.sosadwaden.forum.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

    @Override
    public List<TopicResponse> findAll() {
        List<Topic> topicsDAO = topicRepository.findAll();
        List<TopicResponse> topicsDTO = new ArrayList<>();

        for (Topic topic: topicsDAO) {
            topicsDTO.add(TopicResponse.builder()
                     .name(topic.getName())
                    .build());
        }

        return topicsDTO;
//        return topicRepository.findAll().stream()
//                .
//        List<TopicResponse> topicsDTO = topicRepository.findAll().stream()
//                .map(topic -> topic.)
    }

    @Override
    public Optional<List<MessageResponse>> findMessages(Long id) {
//        Optional<Topic> topic = topicRepository.findById(id);
//        if (topic.isPresent()) {
//
//        }
        return Optional.empty();
    }

    @Override
    public Long create(TopicRequest topicRequest) {
        return null;
    }
}
