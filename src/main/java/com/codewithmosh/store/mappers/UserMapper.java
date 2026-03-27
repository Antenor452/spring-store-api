package com.codewithmosh.store.mappers;

import com.codewithmosh.store.authentication.dtos.RegisterUserRequest;
import com.codewithmosh.store.users.UpdateUserRequest;
import com.codewithmosh.store.users.UserDto;
import com.codewithmosh.store.users.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);

    void update(UpdateUserRequest request, @MappingTarget User user);
}
