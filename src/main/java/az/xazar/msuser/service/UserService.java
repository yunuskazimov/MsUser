package az.xazar.msuser.service;

import az.xazar.msuser.model.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto editUser(UserDto userDto);

    UserDto getById(Long id);

    void deleteById(Long id);

    List<UserDto> getUsers();
}
