//package com.example.gestionimmobilier.chatroom;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
//
//    Optional<ChatRoom> findBySenderIdAndRecipientId(
//            Long senderId,
//            Long recipientId
//    );}



package com.example.gestionimmobilier.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByChatId(String chatId);
}