package com.sosadwaden.forum.initialization;

import com.sosadwaden.forum.entity.Topic;
import com.sosadwaden.forum.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InMemoryDBInitializer implements ApplicationRunner {

    private final TopicRepository topicRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Topic topic = Topic.builder()
                .id(1L)
                .name("test")
                .build();
        topicRepository.save(topic);
    }
}
