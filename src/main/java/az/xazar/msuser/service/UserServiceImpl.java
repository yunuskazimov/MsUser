package az.xazar.msuser.service;

import az.xazar.msuser.entity.UserEntity;
import az.xazar.msuser.mapper.UserMapper;
import az.xazar.msuser.model.UserDto;
import az.xazar.msuser.repository.UserRepository;
import az.xazar.msuser.util.UserUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final UserMapper mapper;
    private final UserUtil userUtil;

    public UserServiceImpl(UserRepository repo, UserMapper mapper, UserUtil userUtil) {
        this.repo = repo;
        this.mapper = mapper;
        this.userUtil = userUtil;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity entity = repo.save(
                mapper.toEntity(userDto));

        userDto.setId(entity.getId());
        return userDto;
    }

    @Override
    public UserDto editUser(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto getById(Long id) {
        return mapper.toDto(
                userUtil.findById(id));
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<UserDto> getUsers() {
        return null;
    }
}
