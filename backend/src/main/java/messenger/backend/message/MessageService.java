package messenger.backend.message;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.jwt.JwtTokenService;
import messenger.backend.chat.exceptions.ChatNotFoundException;
import messenger.backend.message.dto.LastMessageResponseDto;
import messenger.backend.message.dto.MessageResponseDto;
import messenger.backend.message.dto.SendMessageRequestDto;
import messenger.backend.sockets.SocketSender;
import messenger.backend.sockets.SubscribedOn;
import messenger.backend.userChat.UserChat;
import messenger.backend.userChat.UserChatRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserChatRepository userChatRepository;
    private final SocketSender socketSender;

    public List<MessageResponseDto> getAllByChat(UUID chatId) {
        var currentUserId = JwtTokenService.getCurrentUserId();
        var chat = userChatRepository
                .findByUserIdAndChatId(currentUserId, chatId)
                .map(UserChat::getChat)
                .orElseThrow(ChatNotFoundException::new);

        return messageRepository.findAllMessagesByChatIdOrderByCreatedAtAsc(chat.getId())
                .stream()
                .map(MessageResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public MessageResponseDto sendMessage(SendMessageRequestDto requestDto) {
        var currentUserId = JwtTokenService.getCurrentUserId();
        var userChat = userChatRepository
                .findByUserIdAndChatId(currentUserId, requestDto.getChatId())
                .orElseThrow(ChatNotFoundException::new);

        var message = MessageEntity.builder()
                .messageBody(requestDto.getText())
                .user(userChat.getUser())
                .chat(userChat.getChat())
                .build();
        messageRepository.save(message);

        MessageResponseDto responseDto = MessageResponseDto.fromEntity(message);
        socketSender.send(
                SubscribedOn.MESSAGE,
                userChat.getChat().getUserChats().stream().map(chat -> chat.getUser().getId()).collect(Collectors.toList()),
                responseDto);

        return responseDto;
    }

    public LastMessageResponseDto getLastMessageByChatId(UUID chatId) {
        return messageRepository
                .findLastByChatId(chatId, PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .map(LastMessageResponseDto::fromEntity)
                .orElse(null);
    }
}
