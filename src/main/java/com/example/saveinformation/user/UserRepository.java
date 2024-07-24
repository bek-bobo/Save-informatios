package com.example.saveinformation.user;


import com.example.saveinformation.common.repository.GenericRepository;
import com.example.saveinformation.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends GenericRepository<User, UUID> {
    Optional<User> findByPhoneNumber(String phoneNumber);
}
