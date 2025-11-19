package com.bag.complaint_system.identity.application.mapper;

import com.bag.complaint_system.identity.application.dto.response.AuthResponse;
import com.bag.complaint_system.identity.application.dto.response.UserProfileResponse;
import com.bag.complaint_system.identity.application.dto.response.UserResponse;
import com.bag.complaint_system.identity.domain.model.aggregate.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "fullName", source = "user", qualifiedByName = "getFullName")
    @Mapping(target = "email", source = "user", qualifiedByName = "getEmailValue")
    @Mapping(target = "phone", source = "user", qualifiedByName = "getPhoneValue")
    UserProfileResponse toUserProfileResponse(User user);

    @Mapping(target = "token", source = "token")
    @Mapping(target = "type", constant = "Bearer")
    @Mapping(target = "email", source = "user", qualifiedByName = "getEmailValue")
    AuthResponse toAuthResponse(User user, String token);

    @Mapping(target = "email", source = "user", qualifiedByName = "getEmailValue")
    @Mapping(target = "phone", source = "user", qualifiedByName = "getPhoneValue")
    UserResponse toUserResponse(User user);

    @Named("getFullName")
    default String getFullName(User user) {
        return user.getFullName();
    }

    @Named("getEmailValue")
    default String getEmailValue(User user) {
        return user.getEmail().getValue();
    }

    @Named("getPhoneValue")
    default String getPhoneValue(User user) {
        return user.getPhone().getValue();
    }
}
