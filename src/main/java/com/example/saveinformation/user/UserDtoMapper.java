package com.example.saveinformation.user;

import com.example.saveinformation.common.mapper.GenericMapper;
import com.example.saveinformation.user.dto.UserCreateDto;
import com.example.saveinformation.user.dto.UserResponseDto;
import com.example.saveinformation.user.dto.UserUpdateDto;
import com.example.saveinformation.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoMapper extends GenericMapper<User, UserCreateDto, UserResponseDto, UserUpdateDto> {
    private final ModelMapper mapper;

    @Override
    public User toEntity(UserCreateDto userCreateDto) {
        return mapper.map(userCreateDto, User.class);
    }

    @Override
    public UserResponseDto toResponseDto(User user) {
        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    public void updateEntity(UserUpdateDto userUpdateDto, User user) {
        mapper.map(userUpdateDto,user);
    }
}
