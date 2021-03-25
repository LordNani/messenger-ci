package messenger.backend.chat.group.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AddMemberToGroupChatRequestDto {
    @NotNull
    private UUID chatId;
    @NotNull
    private UUID targetUserId;
}
