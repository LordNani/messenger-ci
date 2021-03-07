package messenger.backend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager entityManager;

    @Transactional
    public void createUser(UserEntity userEntity) {
        //todo throw exception when same username?
        entityManager.merge(userEntity);
    }

    @Transactional
    public UserEntity getUserByUsername(String username) {
        return entityManager
                .createQuery("SELECT u FROM UserEntity u WHERE username = :currentUsername", UserEntity.class)
                .setParameter("currentUsername", username).getSingleResult();
    }

    @Transactional
    public List<UserEntity> getAllUsers() {
        return entityManager.createQuery("FROM UserEntity", UserEntity.class).getResultList();
    }
}
