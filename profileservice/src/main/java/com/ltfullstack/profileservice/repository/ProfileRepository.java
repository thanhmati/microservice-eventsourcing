package com.ltfullstack.profileservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ltfullstack.profileservice.entity.Profile;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {}
