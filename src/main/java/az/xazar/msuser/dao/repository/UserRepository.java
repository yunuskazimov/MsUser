package az.xazar.msuser.dao.repository;

import az.xazar.msuser.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByPassword(String password);

    Optional<UserEntity> findByEmail(String email);
}
