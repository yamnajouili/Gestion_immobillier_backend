package com.example.gestionimmobilier.chat;

import com.example.gestionimmobilier.Dtos.ContactDTO;
import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.Repository.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.messaging.simp.user.SimpUserRegistry;

@RestController
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "chatroom")
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final UserRepository userRepository;
    private final SimpUserRegistry simpUserRegistry;




    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {

        // 1. Enregistrer le message dans PostgreSQL
        ChatMessage savedMsg = chatMessageService.save(chatMessage);

        System.out.println("========== NOUVEAU MESSAGE ==========");
        System.out.println("Expéditeur ID : " + savedMsg.getSenderId());
        System.out.println("Destinataire ID : " + savedMsg.getRecipientId());
        System.out.println("Contenu : " + savedMsg.getContent());

        // 2. Afficher les utilisateurs connectés au WebSocket
        System.out.println("========== UTILISATEURS WEBSOCKET ==========");

        simpUserRegistry.getUsers().forEach(user -> {
            System.out.println("Utilisateur connecté : " + user.getName());
        });

        // 3. Récupérer le destinataire dans PostgreSQL
        User destinataire = userRepository
                .findById(savedMsg.getRecipientId())
                .orElseThrow(() ->
                        new RuntimeException("Destinataire introuvable")
                );

        System.out.println("Email destinataire : " + destinataire.getEmail());

        // 4. Envoyer la notification WebSocket
        messagingTemplate.convertAndSendToUser(
                destinataire.getEmail(),
                "/queue/messages",
                new ChatNotification(
                        savedMsg.getId(),
                        savedMsg.getSenderId(),
                        savedMsg.getRecipientId(),
                        savedMsg.getContent()
                )
        );

        System.out.println("Notification envoyée vers : " + destinataire.getEmail());
        System.out.println("==========================================");
    }


//    @MessageMapping("/chat")
//    public void processMessage(@Payload ChatMessage chatMessage) {
//
//        ChatMessage savedMsg = chatMessageService.save(chatMessage);
//
//        messagingTemplate.convertAndSendToUser(
//                String.valueOf(chatMessage.getRecipientId()),
//                "/queue/messages",
//                new ChatNotification(
//                        savedMsg.getId(),
//                        savedMsg.getSenderId(),
//                        savedMsg.getRecipientId(),
//                        savedMsg.getContent()
//                )
//        );
//    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable Long senderId,
            @PathVariable Long recipientId) {

        return ResponseEntity.ok(
                chatMessageService.findChatMessages(senderId, recipientId)
        );
    }


    @GetMapping("/contacts/{userId}")
    public List<ContactDTO> getContacts(@PathVariable Long userId) {
        return chatMessageService.getContacts(userId);
    }

}