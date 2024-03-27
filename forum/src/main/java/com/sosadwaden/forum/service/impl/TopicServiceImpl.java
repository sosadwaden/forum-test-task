package com.sosadwaden.forum.service.impl;

import com.sosadwaden.forum.api.request.TopicRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.api.response.TopicResponse;
import com.sosadwaden.forum.entity.Message;
import com.sosadwaden.forum.entity.Topic;
import com.sosadwaden.forum.exception.TopicNotFoundException;
import com.sosadwaden.forum.repository.MessageRepository;
import com.sosadwaden.forum.repository.TopicRepository;
import com.sosadwaden.forum.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<MessageResponse> findMessages(Long id) {
        Optional<Topic> topicEntity = topicRepository.findById(id);
        if (topicEntity.isPresent()) {
            List<Message> messagesInTopic = topicEntity.get().getMessages();
            return messagesInTopic.stream()
                    .map(this::convertToMessageResponse)
                    .collect(Collectors.toList());

        }
        throw TopicNotFoundException.builder()
                .message("There is no topic with this id")
                .build();

    }

    @Override
    public Long create(TopicRequest topicRequest) {
        // TODO придумать способ проверки что приходит в JSON именно topicName чтобы не создавался topic с name = null
        Message defaultMessage = Message.builder()
                .nickname("Admin")
                .text("Первое сообщение от админа в топике")
                .date(LocalDate.now())
                .build();

        Topic topic = Topic.builder()
                .name(topicRequest.getTopicName())
                .build();

        System.out.println(topicRequest.getTopicName());

        topic.addMessage(defaultMessage);
        Long topicId = topicRepository.save(topic).getId();
        return topicId;
    }

    private TopicResponse convertToTopicResponse(Topic topic) {
        return modelMapper.map(topic, TopicResponse.class);
    }

    private MessageResponse convertToMessageResponse(Message message) {
        return modelMapper.map(message, MessageResponse.class);
    }
}
