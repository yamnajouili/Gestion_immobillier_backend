package com.example.gestionimmobilier.chat;


import com.example.gestionimmobilier.Repository.UserRepository;
import com.example.gestionimmobilier.chatroom.ChatRoomService;
import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.Dtos.ContactDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;
    private final UserRepository userRepository;


    // =========================================================
    // ENREGISTRER UN MESSAGE
    // =========================================================

    public ChatMessage save(ChatMessage chatMessage) {

        System.out.println("==================================");
        System.out.println("MESSAGE RECU");
        System.out.println("content = " + chatMessage.getContent());
        System.out.println("files = " + chatMessage.getFiles());

        if (chatMessage.getFiles() != null) {
            System.out.println(
                    "nombre fichiers = " + chatMessage.getFiles().size()
            );
        }

        System.out.println("==================================");

        String chatId = chatRoomService
                .getChatRoomId(
                        chatMessage.getSenderId(),
                        chatMessage.getRecipientId(),
                        true
                )
                .orElseThrow(() ->
                        new RuntimeException("Impossible de créer la conversation")
                );

        chatMessage.setChatId(chatId);
        chatMessage.setTimestamp(new Date());

        if (chatMessage.getFiles() != null) {
            chatMessage.getFiles().forEach(file -> {
                file.setChatMessage(chatMessage);
            });
        }

        return repository.save(chatMessage);
    }
    // =========================================================
    // RÉCUPÉRER LES MESSAGES D'UNE CONVERSATION
    // =========================================================

    public List<ChatMessage> findChatMessages(
            Long senderId,
            Long recipientId) {

        Optional<String> chatId =
                chatRoomService.getChatRoomId(
                        senderId,
                        recipientId,
                        false
                );

        return chatId
                .map(repository::findByChatIdOrderByTimestampAsc)
                .orElse(new ArrayList<>());
    }


    // =========================================================
    // RÉCUPÉRER LES CONTACTS
    // =========================================================


    public List<ContactDTO> getContacts(Long userId) {

        // 1. Récupérer les messages de l'utilisateur
        List<ChatMessage> messages =
                repository.findAllByUserId(userId);

        // Dernier message pour chaque contact
        Map<Long, ChatMessage> lastMessageByContact =
                new LinkedHashMap<>();

        // 2. Identifier les contacts
        for (ChatMessage message : messages) {

            Long contactId;

            if (message.getSenderId().equals(userId)) {
                contactId = message.getRecipientId();
            } else {
                contactId = message.getSenderId();
            }

            // Les messages sont déjà triés DESC
            if (!lastMessageByContact.containsKey(contactId)) {
                lastMessageByContact.put(contactId, message);
            }
        }

        // 3. Construire la liste des contacts
        List<ContactDTO> contacts = new ArrayList<>();

        for (Map.Entry<Long, ChatMessage> entry :
                lastMessageByContact.entrySet()) {

            Long contactId = entry.getKey();
            ChatMessage lastMessage = entry.getValue();

            // 4. Récupérer l'utilisateur
            Optional<User> userOptional =
                    userRepository.findById(contactId);

            if (userOptional.isEmpty()) {
                continue;
            }

            User user = userOptional.get();

            // 5. Construire le DTO
            ContactDTO contact = new ContactDTO();

            contact.setId(user.getId());

            contact.setName(user.getNom());

            // Photo de profil du contact
            contact.setImage(user.getPhotoProfil());

            // Dernier message
            contact.setLastMessage(lastMessage.getContent());

            // Date du dernier message
            contact.setLastMessageTime(lastMessage.getTimestamp());

            // Statut par défaut
            contact.setStatus("offline");

            contacts.add(contact);
        }

        return contacts;
    }
}