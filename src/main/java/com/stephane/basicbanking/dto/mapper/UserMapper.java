package com.stephane.basicbanking.dto.mapper;

import com.stephane.basicbanking.domain.User;
import com.stephane.basicbanking.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {
    default List<String> map(String roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of();
        }
        return Arrays.asList(roles.split(","));
    }

    default String map(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return "";
        }
        return String.join(",", roles);
    }
}
