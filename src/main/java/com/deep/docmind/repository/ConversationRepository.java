package com.deep.docmind.repository;

import com.deep.docmind.entity.Conversation;
import com.deep.docmind.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, String> {

    List<Conversation> findByUserOrderByUpdateAtDesc(User user);
}
