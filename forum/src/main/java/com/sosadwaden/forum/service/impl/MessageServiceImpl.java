package com.sosadwaden.forum.service.impl;

import com.sosadwaden.forum.api.request.MessagePUTRequest;
import com.sosadwaden.forum.api.request.MessageRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.api.response.TopicResponse;
import com.sosadwaden.forum.entity.Message;
import com.sosadwaden.forum.entity.Topic;
import com.sosadwaden.forum.exception.MessageNotFoundException;
import com.sosadwaden.forum.exception.TopicNotFoundException;
import com.sosadwaden.forum.repository.MessageRepository;
import com.sosadwaden.forum.repository.TopicRepository;
import com.sosadwaden.forum.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<MessageResponse> findMessages(Long topicId) {
        Optional<Topic> topicEntity = topicRepository.findById(topicId);
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
    public void createMessage(Long topicId, MessageRequest messageRequest) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);

        if (optionalTopic.isPresent()) {

            Message message = Message.builder()
                    .nickname(messageRequest.getNickname())
                    .text(messageRequest.getText())
                    .date(LocalDate.now())
                    .build();

            Topic topic = optionalTopic.get();
            topic.addMessage(message);

            topicRepository.save(topic);

        } throw TopicNotFoundException.builder()
                .message("There is no topic with this id")
                .build();
    }

    @Override
    public MessageResponse updateMessage(Long topicId, Long messageId, MessagePUTRequest messageRequest) {

        Optional<Topic> optionalTopic = topicRepository.findById(topicId);

        if (optionalTopic.isPresent()) {

            List<Message> messages = optionalTopic.get().getMessages();

            if (messageId - messages.size() == 0) {

                Topic topic = optionalTopic.get();
                Message message = messages.get(Math.toIntExact(messageId) - 1);
                message.setText(messageRequest.getText() + ", сообщение было отредактировано в этот день: " + LocalDate.now());
                topicRepository.save(topic);
                return convertToMessageResponse(message);

            } throw MessageNotFoundException.builder()
                    .message("There is no message with this id")
                    .build();

        } throw TopicNotFoundException.builder()
                .message("There is no topic with this id")
                .build();
    }

    @Override
    public void deleteMessage(Long messageId) {

        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (optionalMessage.isPresent()) {

            Message message = optionalMessage.get();
            Topic topic = message.getTopic();
            topic.removeMessage(message);
            messageRepository.deleteById(messageId);

        } throw MessageNotFoundException.builder()
                .message("There is no message with this id")
                .build();
    }

    private MessageResponse convertToMessageResponse(Message message) {
        return modelMapper.map(message, MessageResponse.class);
    }
}
