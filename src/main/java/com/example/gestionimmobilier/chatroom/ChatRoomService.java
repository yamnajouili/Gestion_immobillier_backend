//package com.example.gestionimmobilier.chatroom;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class ChatRoomService {
//
//    private final ChatRoomRepository chatRoomRepository;
//
//    public Optional<String> getChatRoomId(
//            Long senderId,
//            Long recipientId,
//            boolean createNewRoomIfNotExists
//    ) {
//
//        return chatRoomRepository
//                .findBySenderIdAndRecipientId(senderId, recipientId)
//                .map(ChatRoom::getChatId)
//                .or(() -> {
//
//                    if (createNewRoomIfNotExists) {
//                        String chatId = createChatId(senderId, recipientId);
//                        return Optional.of(chatId);
//                    }
//
//                    return Optional.empty();
//                });
//    }
//
//    private String createChatId(Long senderId, Long recipientId) {
//
//        String chatId = senderId + "_" + recipientId;
//
//        ChatRoom senderRoom = ChatRoom.builder()
//                .chatId(chatId)
//                .senderId(senderId)
//                .recipientId(recipientId)
//                .build();
//
//        ChatRoom recipientRoom = ChatRoom.builder()
//                .chatId(chatId)
//                .senderId(recipientId)
//                .recipientId(senderId)
//                .build();
//
//        chatRoomRepository.save(senderRoom);
//        chatRoomRepository.save(recipientRoom);
//
//        return chatId;
//    }
//}



package com.example.gestionimmobilier.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Optional<String> getChatRoomId(
            Long senderId,
            Long recipientId,
            boolean createNewRoomIfNotExists
    ) {

        String chatId = createChatId(senderId, recipientId);

        Optional<ChatRoom> existingRoom =
                chatRoomRepository.findByChatId(chatId);

        if (existingRoom.isPresent()) {
            return Optional.of(chatId);
        }

        if (createNewRoomIfNotExists) {

            ChatRoom chatRoom = ChatRoom.builder()
                    .chatId(chatId)
                    .senderId(senderId)
                    .recipientId(recipientId)
                    .build();

            chatRoomRepository.save(chatRoom);

            return Optional.of(chatId);
        }

        return Optional.empty();
    }

    private String createChatId(Long senderId, Long recipientId) {

        Long min = Math.min(senderId, recipientId);
        Long max = Math.max(senderId, recipientId);

        return min + "_" + max;
    }
}