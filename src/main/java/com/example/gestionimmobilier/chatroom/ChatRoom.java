package com.example.gestionimmobilier.chatroom;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatId;
    private Long senderId;
    private Long recipientId;
}