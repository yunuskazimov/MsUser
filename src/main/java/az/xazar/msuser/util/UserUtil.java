package az.xazar.msuser.util;

import az.xazar.msuser.entity.UserEntity;
import az.xazar.msuser.model.exception.ErrorCodes;
import az.xazar.msuser.model.exception.UserNotFoundException;
import az.xazar.msuser.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {
    private final UserRepository repository;

    public UserUtil(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException(ErrorCodes.NOT_FOUND));
    }
}
