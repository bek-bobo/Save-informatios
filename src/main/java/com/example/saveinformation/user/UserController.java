package com.example.saveinformation.user;


import com.example.saveinformation.common.response.CommonResponse;
import com.example.saveinformation.security.JwtService;
import com.example.saveinformation.user.dto.*;
import com.example.saveinformation.user.otp.OtpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User Controller for Swagger")
public class UserController {

    private final UserService userService;
    private final OtpService otpService;
    private final JwtService jwtService;


    @PostMapping("/auth/validate/phone-number")
    @Operation(summary = "Phone number Validation", description = "Returns a String message")
    public ResponseEntity<CommonResponse> validatePhoneNumber(@RequestBody @Valid ValidatePhoneNumberDto requestDto) {
        CommonResponse commonResponse = otpService.sendSms(requestDto.getPhoneNumber());
        return ResponseEntity.ok(commonResponse);
    }


    @PostMapping("/auth/validate/code")
    @Operation(summary = "Validation code check", description = "Returns a greeting message")
    public ResponseEntity<CommonResponse> validateOtpCode(@RequestBody @Valid ValidateOtpCodeDto validateCode) {
        CommonResponse commonResponse = otpService.checkCode(validateCode);
        return ResponseEntity.ok(commonResponse);
    }


    @PostMapping("/auth/sign-up")
    @Operation(summary = "Create a new User", description = "Returns a User response")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.create(userCreateDto);
        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userResponseDto);
    }


    @PostMapping("/auth/sign-in")
    public ResponseEntity<UserResponseDto> signIn(@RequestBody @Valid UserSignInDto signInDto) {
        UserResponseDto userResponseDto = userService.signIn(signInDto);
        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userResponseDto);
    }

    @GetMapping("/account")
    public ResponseEntity<UserResponseDto> getUser(@RequestBody UserGetByIdDto userGetByIdDto) {
        UserResponseDto userResponseDto = userService.get(userGetByIdDto.getId());
        return ResponseEntity.ok(userResponseDto);

    }


    @PutMapping("/account/update")
    public ResponseEntity<UserResponseDto> userUpdate(@RequestBody UserUpdateDto updateDto) {
        UserResponseDto update = userService.update(updateDto.getId(), updateDto);
        return ResponseEntity.ok(update);
    }


    @PutMapping("/account/delete")
    public ResponseEntity<Object> userUpdate(@RequestBody UserDeleteDto userDelete) {
        if (userService.delete(userDelete.getId())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
