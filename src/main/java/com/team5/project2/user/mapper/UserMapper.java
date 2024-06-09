package com.team5.project2.user.mapper;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.dto.UserPostDto;
import com.team5.project2.user.dto.UserPutDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userPostDtoToUser(UserPostDto userPostDto);

    User userPutDtoToUser(UserPutDto userPutDto);



}
