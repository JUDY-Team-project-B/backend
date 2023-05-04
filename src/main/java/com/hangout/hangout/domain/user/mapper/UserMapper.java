package com.hangout.hangout.domain.user.mapper;

import com.hangout.hangout.domain.auth.dto.request.SignUpRequest;
import com.hangout.hangout.domain.user.dto.UserDto;
import com.hangout.hangout.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(source = "id", target = "userId")
  UserDto toDto(User user);
  @Mapping(target = "password", expression = "java(passwordEncoder.encode(signUpRequest.getPassword()))")
  @Mapping(target = "role", constant = "USER")
  User toEntity(SignUpRequest signUpRequest);
  User toEntity(UserDto userDto);
}