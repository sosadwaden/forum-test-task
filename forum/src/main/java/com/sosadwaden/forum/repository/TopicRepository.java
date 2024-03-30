package com.sosadwaden.forum.repository;

import com.sosadwaden.forum.entity.Message;
import com.sosadwaden.forum.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Message> findMessagesInTopicById(Long Id);

    int findMessageById(Long messageId);
}
