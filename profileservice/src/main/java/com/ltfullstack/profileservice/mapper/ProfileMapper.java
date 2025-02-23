package com.ltfullstack.profileservice.mapper;

import org.mapstruct.Mapper;

import com.ltfullstack.profileservice.dto.request.RegistrationRequest;
import com.ltfullstack.profileservice.dto.response.ProfileResponse;
import com.ltfullstack.profileservice.entity.Profile;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile toProfile(RegistrationRequest request);

    ProfileResponse toProfileResponse(Profile profile);
}
