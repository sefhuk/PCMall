package com.team5.project2.user.mapper;

import com.team5.project2.user.domain.User;
import com.team5.project2.user.dto.UserPostDto;
import com.team5.project2.user.dto.UserPutDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-01T09:57:39+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userPostDtoToUser(UserPostDto userPostDto) {
        if ( userPostDto == null ) {
            return null;
        }

        User user = new User();

        user.setName( userPostDto.getName() );
        user.setEmail( userPostDto.getEmail() );
        user.setPassword( userPostDto.getPassword() );
        user.setPhone_number( userPostDto.getPhone_number() );

        return user;
    }

    @Override
    public User userPutDtoToUser(UserPutDto userPutDto) {
        if ( userPutDto == null ) {
            return null;
        }

        User user = new User();

        user.setName( userPutDto.getName() );
        user.setEmail( userPutDto.getEmail() );
        user.setPassword( userPutDto.getPassword() );
        user.setPhone_number( userPutDto.getPhone_number() );
        user.setAddress( userPutDto.getAddress() );

        return user;
    }
}
