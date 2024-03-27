package com.sosadwaden.forum.initialization;

import com.sosadwaden.forum.entity.Message;
import com.sosadwaden.forum.entity.Topic;
import com.sosadwaden.forum.repository.MessageRepository;
import com.sosadwaden.forum.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class InMemoryDBInitializer implements ApplicationRunner {

    private final TopicRepository topicRepository;
    private final MessageRepository messageRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Message message1 = Message.builder()
                .id(1L)
                .text("Привет из темы про телефоны")
                .date(LocalDate.now())
                .nickname("Автор1")
                .build();

        Message message2 = Message.builder()
                .id(2L)
                .text("Привет из темы про ноутбуки")
                .date(LocalDate.now())
                .nickname("Автор2")
                .build();

        Message message3 = Message.builder()
                .id(3L)
                .text("Привет из темы про животных")
                .date(LocalDate.now())
                .nickname("Автор3")
                .build();

        Topic topic1 = Topic.builder()
                .id(1L)
                .name("Телефоны")
                .build();

        Topic topic2 = Topic.builder()
                .id(2L)
                .name("Ноутбуки")
                .build();

        Topic topic3 = Topic.builder()
                .id(3L)
                .name("Животные")
                .build();

        topic1.addMessage(message1);
        topic2.addMessage(message2);
        topic3.addMessage(message3);

        topicRepository.save(topic1);
        topicRepository.save(topic2);
        topicRepository.save(topic3);

    }
}

