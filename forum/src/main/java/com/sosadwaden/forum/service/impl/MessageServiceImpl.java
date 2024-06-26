package com.sosadwaden.forum.service.impl;

import com.sosadwaden.forum.api.request.MessagePUTRequest;
import com.sosadwaden.forum.api.request.MessagePOSTRequest;
import com.sosadwaden.forum.api.response.MessageResponse;
import com.sosadwaden.forum.entity.Message;
import com.sosadwaden.forum.entity.Topic;
import com.sosadwaden.forum.exception.MessageNotFoundException;
import com.sosadwaden.forum.exception.NoUserPermissionsException;
import com.sosadwaden.forum.exception.TopicNotFoundException;
import com.sosadwaden.forum.exception.UserNotAuthenticatedException;
import com.sosadwaden.forum.repository.MessageRepository;
import com.sosadwaden.forum.repository.TopicRepository;
import com.sosadwaden.forum.service.MessageService;
import com.sosadwaden.forum.util.CheckRole;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public List<MessageResponse> findMessages(Long topicId, Integer offset, Integer limit) {

        Optional<Topic> topicEntity = topicRepository.findById(topicId);

        if (topicEntity.isPresent()) {

            List<Message> messagesInTopic = topicEntity.get().getMessages();
            List<Message> subListMessages = messagesInTopic.subList(offset, limit);

            return subListMessages.stream()
                    .map(this::convertToMessageResponse)
                    .collect(Collectors.toList());

        } else{
            throw TopicNotFoundException.builder()
                    .message(String.format("There is no topic with id: %s", topicId))
                    .build();
        }
    }

    @Override
    public void createMessage(Long topicId, MessagePOSTRequest messagePOSTRequest) {
        Optional<Topic> optionalTopic = topicRepository.findById(topicId);

        if (optionalTopic.isPresent()) {

            Message message = Message.builder()
                    .nickname(SecurityContextHolder.getContext().getAuthentication().getName())
                    .text(messagePOSTRequest.getText())
                    .date(LocalDate.now())
                    .build();

            Topic topic = optionalTopic.get();
            topic.addMessage(message);

            topicRepository.save(topic);

        } else {
            throw TopicNotFoundException.builder()
                    .message(String.format("There is no topic with id: %s", topicId))
                    .build();
        }
    }

    @Override
    public MessageResponse updateMessage(Long topicId, Long messageId, MessagePUTRequest messagePUTRequest) {

        if (!CheckRole.hasAdminRole() && !CheckRole.hasUserRole()) {
            throw UserNotAuthenticatedException.builder()
                    .message("User is not authenticated")
                    .build();
        }

        Optional<Topic> optionalTopic = topicRepository.findById(topicId);

        if (optionalTopic.isPresent()) {

            List<Message> messages = optionalTopic.get().getMessages();

            if (messageId - messages.size() == 0) {

                Topic topic = optionalTopic.get();
                Message message = messages.get(Math.toIntExact(messageId) - 1);
                String messageAuthor = message.getNickname();

                if (!messageAuthor.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
                    throw NoUserPermissionsException.builder()
                            .message(String.format("User with nickname %s does not have access to edit this message", messageAuthor))
                            .build();
                }

                message.setText(String.format("%s. Сообщение было отредактировано в этот день: %s", messagePUTRequest.getText(), LocalDate.now()));
                topicRepository.save(topic);

                return convertToMessageResponse(message);

            } throw MessageNotFoundException.builder()
                    .message(String.format("There is no message with id: %s", messageId))
                    .build();

        } throw TopicNotFoundException.builder()
                .message(String.format("There is no topic with id: %s", topicId))
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

        } else {
            throw MessageNotFoundException.builder()
                    .message(String.format("There is no message with id: %s", messageId))
                    .build();
        }
    }

    private MessageResponse convertToMessageResponse(Message message) {
        return modelMapper.map(message, MessageResponse.class);
    }
}
