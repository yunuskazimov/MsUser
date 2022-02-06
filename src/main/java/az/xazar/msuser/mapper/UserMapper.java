package az.xazar.msuser.mapper;

import az.xazar.msuser.dao.entity.UserEntity;
import az.xazar.msuser.model.UserAuthClientDto;
import az.xazar.msuser.model.UserCreateDto;
import az.xazar.msuser.model.UserDto;
import az.xazar.msuser.model.UserEditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "isDeleted", source = "deleted")
    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(UserEntity entity);

    @Mapping(target = "isDeleted", source = "deleted")
    @Mapping(target = "password", ignore = true)
    List<UserDto> toUserDtoList(List<UserEntity> entityList);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", source = "deleted")
    UserEntity toUserEntity(UserDto dto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", source = "deleted")
    @Mapping(target = "password", ignore = true)
    UserEntity editDtoToUserEntity(UserEditDto editDto);

    UserEditDto toUserEditDto(UserEntity entity);

    UserAuthClientDto toUserAuthClientDto(UserEntity entity);

    @Mapping(target = "isDeleted", source = "deleted")
    List<UserEditDto> toUserEditDtoList(List<UserEntity> entityList);

    @Mapping(target = "isDeleted", source = "deleted")
    @Mapping(target = "password", ignore = true)
    UserDto createDtoToUserEntity(UserCreateDto userDto);
}
