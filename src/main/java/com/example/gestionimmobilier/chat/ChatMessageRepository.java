package com.example.gestionimmobilier.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    // Messages d'une conversation, du plus ancien au plus récent
    List<ChatMessage> findByChatIdOrderByTimestampAsc(String chatId);

    // Tous les messages concernant un utilisateur
    @Query("""
        SELECT m FROM ChatMessage m
        WHERE m.senderId = :userId
           OR m.recipientId = :userId
        ORDER BY m.timestamp DESC
    """)
    List<ChatMessage> findAllByUserId(
            @Param("userId") Long userId
    );
}