package com.example.gestionimmobilier.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_file")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // URL permettant à Angular d'afficher le fichier
    private String url;

    // IMAGE ou VIDEO
    private String type;

    // Nom original du fichier
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    @JsonIgnore
    private ChatMessage chatMessage;
}