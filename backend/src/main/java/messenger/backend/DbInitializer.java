package messenger.backend;

import lombok.RequiredArgsConstructor;
import messenger.backend.auth.access_levels.Role;
import messenger.backend.seeds.FakerService;
import messenger.backend.user.UserEntity;
import messenger.backend.user.UserRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class DbInitializer {

    private final UserRepository userRepository;
    private final FakerService fakerService;

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        fakerService.generateRandomData();
        userRepository.saveAndFlush(
                UserEntity.builder()
                        .username("user")
                        .fullName("userFullName")
                        .password("$2y$12$ixe4Lh4uQVncJDzPJWckfeyTXPMkuVZm55miqLdnn/TjH0FoF8HOq") //user (BCryptPasswordEncoder(12))
                        .role(Role.USER)
                        .build());
        userRepository.saveAndFlush(
                UserEntity.builder()
                        .username("user2")
                        .fullName("user2FullName")
                        .password("$2y$12$ixe4Lh4uQVncJDzPJWckfeyTXPMkuVZm55miqLdnn/TjH0FoF8HOq") //user (BCryptPasswordEncoder(12))
                        .role(Role.USER)
                        .build());
    }
}
