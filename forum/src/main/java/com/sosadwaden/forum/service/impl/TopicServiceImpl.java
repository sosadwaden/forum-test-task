package com.sosadwaden.forum.service.impl;

import com.sosadwaden.forum.api.request.TopicPOSTRequest;
import com.sosadwaden.forum.api.response.TopicResponse;
import com.sosadwaden.forum.entity.Message;
import com.sosadwaden.forum.entity.Topic;
import com.sosadwaden.forum.repository.MessageRepository;
import com.sosadwaden.forum.repository.TopicRepository;
import com.sosadwaden.forum.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TopicResponse> findAll() {

        List<Topic> topicsEntity = topicRepository.findAll();

        return topicsEntity.stream()
                .map(this::convertToTopicResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Long createTopic(TopicPOSTRequest topicPOSTRequest) {

        Message defaultMessage = Message.builder()
                .nickname("Admin")
                .text("Первое сообщение от админа в топике")
                .date(LocalDate.now())
                .build();

        Topic topic = Topic.builder()
                .name(topicPOSTRequest.getTopicName())
                .build();

        topic.addMessage(defaultMessage);

        return topicRepository.save(topic).getId();
    }

    private TopicResponse convertToTopicResponse(Topic topic) {
        return modelMapper.map(topic, TopicResponse.class);
    }

}
