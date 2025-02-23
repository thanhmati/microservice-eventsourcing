package com.ltfullstack.profileservice.service;

import java.util.List;

import com.ltfullstack.profileservice.exception.ErrorNormalizer;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ltfullstack.profileservice.dto.identity.Credential;
import com.ltfullstack.profileservice.dto.identity.TokenExchangeParam;
import com.ltfullstack.profileservice.dto.identity.UserCreationParam;
import com.ltfullstack.profileservice.dto.request.RegistrationRequest;
import com.ltfullstack.profileservice.dto.response.ProfileResponse;
import com.ltfullstack.profileservice.mapper.ProfileMapper;
import com.ltfullstack.profileservice.repository.IdentityClient;
import com.ltfullstack.profileservice.repository.ProfileRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileService {
    ProfileRepository profileRepository;
    ProfileMapper profileMapper;
    IdentityClient identityClient;

    ErrorNormalizer errorNormalizer;

    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    public List<ProfileResponse> getAllProfiles() {
        var profiles = profileRepository.findAll();
        return profiles.stream().map(profileMapper::toProfileResponse).toList();
    }

    public ProfileResponse register(RegistrationRequest request) {
        try {
            // Create account in KeyCloak
            // Exchange client Token
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());

            log.info("TokenInfo {}", token);
            // Create user with client Token and given info

            // Get userId of keyCloak account
            var creationResponse = identityClient.createUser(
                    "Bearer " + token.getAccessToken(),
                    UserCreationParam.builder()
                            .username(request.getUsername())
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .email(request.getEmail())
                            .enabled(true)
                            .emailVerified(false)
                            .credentials(List.of(Credential.builder()
                                    .type("password")
                                    .temporary(false)
                                    .value(request.getPassword())
                                    .build()))
                            .build());

            String userId = extractUserId(creationResponse);
            log.info("UserId {}", userId);

            var profile = profileMapper.toProfile(request);
            profile.setUserId(userId);

            profile = profileRepository.save(profile);

            return profileMapper.toProfileResponse(profile);
        } catch (FeignException exception){
            throw errorNormalizer.handleKeyCloakException(exception);
        }
    }

    private String extractUserId(ResponseEntity<?> response) {
        List<String> locations = response.getHeaders().get("Location");
        if (locations == null || locations.isEmpty()) {
            throw new IllegalStateException("Location header is missing in the response");
        }

        String location = locations.get(0);
        String[] splitedStr = location.split("/");
        return splitedStr[splitedStr.length - 1];
    }
}
