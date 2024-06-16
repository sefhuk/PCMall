package com.team5.project2.user.mapper;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.dto.UserPostDto;
import com.team5.project2.user.dto.UserPutDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "role", ignore = true)
    User userPostDtoToUser(UserPostDto userPostDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phone_number", ignore = true)
    @Mapping(target = "role", ignore = true)
    User userPutDtoToUser(UserPutDto userPutDto);
}
