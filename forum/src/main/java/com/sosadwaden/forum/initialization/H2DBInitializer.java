package com.sosadwaden.forum.initialization;

import com.sosadwaden.forum.entity.Message;
import com.sosadwaden.forum.entity.Topic;
import com.sosadwaden.forum.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class H2DBInitializer implements ApplicationRunner {

    private final TopicRepository topicRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Message message1 = Message.builder()
                .id(1L)
                .text("Привет из темы про телефоны, сообщение 1")
                .date(LocalDate.now())
                .nickname("Автор1")
                .build();

        Message message2 = Message.builder()
                .id(2L)
                .text("Привет из темы про телефоны, сообщение 2")
                .date(LocalDate.now())
                .nickname("Автор2")
                .build();

        Message message3 = Message.builder()
                .id(3L)
                .text("Привет из темы про телефоны, сообщение 3")
                .date(LocalDate.now())
                .nickname("Автор3")
                .build();

        Message message4 = Message.builder()
                .id(4L)
                .text("Привет из темы про ноутбуки, сообщение 1")
                .date(LocalDate.now())
                .nickname("Автор1")
                .build();

        Message message5 = Message.builder()
                .id(5L)
                .text("Привет из темы про ноутбуки, сообщение 2")
                .date(LocalDate.now())
                .nickname("Автор2")
                .build();

        Message message6 = Message.builder()
                .id(6L)
                .text("Привет из темы про ноутбуки, сообщение 3")
                .date(LocalDate.now())
                .nickname("Автор3")
                .build();

        Message message7 = Message.builder()
                .id(7L)
                .text("Привет из темы про животных, сообщение 1")
                .date(LocalDate.now())
                .nickname("Автор1")
                .build();

        Message message8 = Message.builder()
                .id(8L)
                .text("Привет из темы про животных, сообщение 2")
                .date(LocalDate.now())
                .nickname("Автор2")
                .build();

        Message message9 = Message.builder()
                .id(9L)
                .text("Привет из темы про животных, сообщение 3")
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
        topic1.addMessage(message2);
        topic1.addMessage(message3);

        topic2.addMessage(message4);
        topic2.addMessage(message5);
        topic2.addMessage(message6);

        topic3.addMessage(message7);
        topic3.addMessage(message8);
        topic3.addMessage(message9);

        topicRepository.save(topic1);
        topicRepository.save(topic2);
        topicRepository.save(topic3);

    }
}

