package az.xazar.msuser.service;

import az.xazar.msuser.client.model.permission.RolesAddDto;
import az.xazar.msuser.model.PasswordDto;
import az.xazar.msuser.model.UserAuthClientDto;
import az.xazar.msuser.model.UserCreateDto;
import az.xazar.msuser.model.UserEditDto;

import java.util.List;

public interface UserService {

    UserCreateDto createUser(UserCreateDto userCreateDto);

    UserEditDto editUser(Long id, UserEditDto editDto);

    void addRoleToUser(RolesAddDto roles);

    UserEditDto getById(Long id);

    UserAuthClientDto getByUsername(String username);

    String deleteById(Long id);

    List<UserEditDto> getUsers();

    boolean changePassword(PasswordDto dto);
}
