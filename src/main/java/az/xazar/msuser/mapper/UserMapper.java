package az.xazar.msuser.mapper;

import az.xazar.msuser.entity.UserEntity;
import az.xazar.msuser.model.UserDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "isDeleted", source = "deleted")
    UserDto toDto(UserEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "isDeleted", source = "deleted")
    UserEntity toEntity(UserDto dto);
}
