package com.sosadwaden.forum.service.impl;

import com.sosadwaden.forum.api.request.TopicPOSTRequest;
import com.sosadwaden.forum.api.request.TopicPUTRequest;
import com.sosadwaden.forum.api.response.TopicResponse;
import com.sosadwaden.forum.entity.Message;
import com.sosadwaden.forum.entity.Topic;
import com.sosadwaden.forum.exception.MessageNotFoundException;
import com.sosadwaden.forum.exception.NoUserPermissionsException;
import com.sosadwaden.forum.exception.TopicNotFoundException;
import com.sosadwaden.forum.repository.TopicRepository;
import com.sosadwaden.forum.service.TopicService;
import com.sosadwaden.forum.util.CheckRole;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<TopicResponse> findAll(Integer offset, Integer limit) {

        List<Topic> topicsEntity = topicRepository.findAll(PageRequest.of(offset, limit)).getContent();

        return topicsEntity.stream()
                .map(this::convertToTopicResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Long createTopic(TopicPOSTRequest topicPOSTRequest) {

        Message defaultMessage = Message.builder()
                .nickname(SecurityContextHolder.getContext().getAuthentication().getName())
                .text("The first message from the admin in the topic")
                .date(LocalDate.now())
                .build();

        Topic topic = Topic.builder()
                .name(topicPOSTRequest.getTopicName())
                .build();

        topic.addMessage(defaultMessage);

        return topicRepository.save(topic).getId();
    }

    @Override
    public TopicResponse updateTopic(Long topicId, TopicPUTRequest topicPUTRequest) {

        if (!CheckRole.hasAdminRole()) {
            throw NoUserPermissionsException.builder()
                    .message("User does not have the right to edit the topic")
                    .build();
        }

        Optional<Topic> optionalTopic = topicRepository.findById(topicId);

        if (optionalTopic.isPresent()) {

            Topic topic = optionalTopic.get();
            topic.setName(topicPUTRequest.getTopicName());
            topicRepository.save(topic);

            return convertToTopicResponse(topic);

        } else {
            throw TopicNotFoundException.builder()
                    .message(String.format("There is no topic with id: %s", topicId))
                    .build();
        }
    }

    @Override
    public void deleteTopic(Long topicId) {

        if (!CheckRole.hasAdminRole()) {
            throw NoUserPermissionsException.builder()
                    .message("User does not have the right to delete the topic")
                    .build();
        }

        Optional<Topic> optionalTopic = topicRepository.findById(topicId);

        if (optionalTopic.isPresent()) {

            Topic topic = optionalTopic.get();
            topicRepository.delete(topic);

        } else {
            throw MessageNotFoundException.builder()
                    .message(String.format("There is no topic with id: %s", topicId))
                    .build();
        }
    }

    private TopicResponse convertToTopicResponse(Topic topic) {
        return modelMapper.map(topic, TopicResponse.class);
    }

}
