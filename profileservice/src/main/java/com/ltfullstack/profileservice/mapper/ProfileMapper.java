package com.devteria.profile.mapper;

import com.devteria.profile.dto.request.RegistrationRequest;
import com.devteria.profile.dto.response.ProfileResponse;
import com.devteria.profile.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(RegistrationRequest request);
    ProfileResponse toProfileResponse(Profile profile);
}
