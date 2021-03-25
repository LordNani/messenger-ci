package messenger.backend.userChat;


import lombok.*;
import messenger.backend.chat.ChatSuperclass;
import messenger.backend.message.MessageEntity;
import messenger.backend.user.UserEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity
public class UserChat {
    public static UserChat generateUserChat(PermissionLevel permLvl, ChatSuperclass chat, UserEntity user) {

        return UserChat.builder()
                .id(UUID.randomUUID())
                .permissionLevel(PermissionLevel.MEMBER)
                .user(user)
                .chat(chat)
                .build();
    }

    public void appendMessages(List<MessageEntity> messages){
        if(isNull(messageEntities))
            messageEntities = new ArrayList<>();
        this.messageEntities.addAll(messages);

    }

    public enum PermissionLevel {
        OWNER,
        ADMIN,
        MEMBER
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "UserChatId")
    private UUID id;

    //    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "UserId")
    private UserEntity user;

    //    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "ChatId")
    private ChatSuperclass chat;

    @Enumerated
    @Column(name = "PermissionLevel")
    private PermissionLevel permissionLevel;

    @OneToMany(mappedBy = "userChat")
    private List<MessageEntity> messageEntities = new ArrayList<>();


}
