package com.example.saveinformation.user.otp;


import com.example.saveinformation.common.Exeptions.OtpException;
import com.example.saveinformation.common.response.CommonResponse;
import com.example.saveinformation.notification.sms.SmsNotificationService;
import com.example.saveinformation.user.dto.ValidateOtpCodeDto;
import com.example.saveinformation.user.otp.entity.Otp;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final Random random = new Random();

    private final SmsNotificationService smsNotificationService;

    @Value("${spring.saveinformation.otp.retry-wait-time}")
    private int retryWaitTime;

    @Value("${spring.saveinformation.otp.retry-count}")
    private int retryCount;

    @Value("${spring.saveinformation.otp.time-to-leave}")
    private int timeToLeave;

    private final String VERIFIKATION_MASSAGE = " \nOne time code: %s";


    @Transactional
    public CommonResponse sendSms(String phoneNumber) {
        Optional<Otp> existingOtp = otpRepository.findById(phoneNumber);

        if (existingOtp.isPresent()) {
            Otp otp = existingOtp.orElseThrow(() -> new EntityNotFoundException("Can't send any verification code"));
                if (!otp.isVerified()) {
                    return reTry(existingOtp.get());
                }
                    throw new OtpException.OtpVerification(otp.getLastSentTime());
            }
        Otp otp = sendSmsInternal(phoneNumber);
        otpRepository.save(otp);

        return new CommonResponse("Sms send successfully", LocalDateTime.now(), HttpStatus.OK.value());
    }


    public CommonResponse checkCode(ValidateOtpCodeDto validateOtpCodeDto) {
        Optional<Otp> otpByCode = otpRepository.findById(validateOtpCodeDto.getPhoneNumber());

        if (otpByCode.isPresent()) {
            Otp otp = otpByCode.orElseThrow(() -> new EntityNotFoundException("we didn't send you any verification code"));

            if (otp.getCode() == validateOtpCodeDto.getCode() && !otp.isVerified()) {
                otp.setVerified(true);
                otpRepository.save(otp);
                return new CommonResponse("Otp was successfully verified", LocalDateTime.now(), HttpStatus.OK.value());
            }
            throw new OtpException.OtpVerification(otp.getLastSentTime());
        }
        return new CommonResponse("Otp was incorrect", LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }


    public CommonResponse reTry(Otp otp) {

        if (otp.getLastSentTime().plusSeconds(retryWaitTime).isAfter(LocalDateTime.now())) {
            long seconds = Duration.between(otp.getLastSentTime(), LocalDateTime.now()).getSeconds();
            throw new OtpException.OtpEarlyResentException(retryWaitTime - seconds);
        }

        if (otp.getSentCount() >= retryCount) {
            throw new OtpException.OtpLimitExitedException(otp.getSentCount(), otp.getCreatedTime().plusSeconds(timeToLeave));
        }

        Otp resentOtp = sendSmsInternal(otp);

        otpRepository.save(resentOtp);

        return new CommonResponse("Sms re-sent successfully", LocalDateTime.now(), 200);
    }


    private Otp sendSmsInternal(String phoneNumber) {

        int code = random.nextInt(100000, 999999);
        Otp otp = new Otp(phoneNumber, code, 1, LocalDateTime.now(), LocalDateTime.now(), false);

        smsNotificationService.sendNotification(phoneNumber, VERIFIKATION_MASSAGE.formatted(code));
        return otp;
    }


    private Otp sendSmsInternal(Otp otp) {

        int code = random.nextInt(100000, 999999);
        smsNotificationService.sendNotification(otp.getPhoneNumber(), VERIFIKATION_MASSAGE.formatted(code));
        otp.setCode(code);
        otp.setLastSentTime(LocalDateTime.now());
        otp.setSentCount(otp.getSentCount() + 1);
        return otp;
    }

}
