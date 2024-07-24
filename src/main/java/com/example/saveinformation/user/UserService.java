package com.example.saveinformation.user;


import com.example.saveinformation.common.Exeptions.OtpException;
import com.example.saveinformation.common.service.GenericService;
import com.example.saveinformation.user.dto.UserCreateDto;
import com.example.saveinformation.user.dto.UserResponseDto;
import com.example.saveinformation.user.dto.UserSignInDto;
import com.example.saveinformation.user.dto.UserUpdateDto;
import com.example.saveinformation.user.entity.User;
import com.example.saveinformation.user.otp.OtpRepository;
import com.example.saveinformation.user.otp.entity.Otp;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
@Slf4j
public class UserService extends GenericService<User, UUID, UserCreateDto, UserResponseDto, UserUpdateDto> {
    private final UserRepository repository;
    private final Class<User> entityClass = User.class;
    private final UserDtoMapper mapper;
    private final OtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected UserResponseDto internalCreate(UserCreateDto userCreateDto) {
        User entity = mapper.toEntity(userCreateDto);


        isPhoneNumberVerified(userCreateDto.getPhoneNumber());

        entity.setId(UUID.randomUUID());
        entity.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        entity.setConfirmPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        return mapper.toResponseDto(repository.save(entity));
    }




    @Override
    protected UserResponseDto internalUpdate(UUID uuid, UserUpdateDto userUpdateDto) {

        Optional<User> userById = repository.findById(uuid);
        if (userById.isPresent()){
            User user = userById.orElseThrow(() -> new EntityNotFoundException("User not found by id: %s".formatted(uuid)));
            mapper.updateEntity(userUpdateDto, user);
            User save = repository.save(user);
            return mapper.toResponseDto(save);
        }

        log.error("User is not found by id: %s".formatted(uuid));

        return null;
    }



    private void isPhoneNumberVerified(String phoneNumber) {
        Otp otp = otpRepository.findById(phoneNumber)
                .orElseThrow(() -> new OtpException.PhoneNumberNotVerification(phoneNumber));

        if (!otp.isVerified()) {
            throw new OtpException.PhoneNumberNotVerification(phoneNumber);
        }
    }

    @Transactional
    public UserResponseDto signIn(UserSignInDto signInDto) {
        User user = repository.findByPhoneNumber(signInDto.getPhoneNumber())
                .orElseThrow(() -> new BadCredentialsException("phone number or password is not correct "));


        if (!passwordEncoder.matches(signInDto.getPassword(),user.getPassword())){
            throw new BadCredentialsException("phone number or password is not correct ");
        }

        return mapper.toResponseDto(user);
    }
}
