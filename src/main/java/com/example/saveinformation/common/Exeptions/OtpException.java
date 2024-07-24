package com.example.saveinformation.common.Exeptions;

import com.example.saveinformation.common.response.ResponseConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OtpException extends RuntimeException {

    public OtpException(String message) {
        super(message);
    }


    public OtpException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class OtpEarlyResentException extends OtpException {

        private static final String MESSAGE = "Please try after 0:%d";


        public OtpEarlyResentException(long resentTime) {
            super(String.format(MESSAGE,  resentTime));
        }
    }


    public static class OtpLimitExitedException extends OtpException {

        private static final String MESSAGE = "Validation limit is reached: %s, please try after %s";


        public OtpLimitExitedException(int count, LocalDateTime reTryTime) {
            super(String.format(MESSAGE,count,DateTimeFormatter.ofPattern(ResponseConstants.RESPONSE_DATE_FORMAT).format(reTryTime)));
        }
    }


    public static class OtpVerification extends OtpException {

        private static final String MESSAGE = "Validation was successfully at %s!";


        public OtpVerification( LocalDateTime successfulTime) {
            super(String.format(MESSAGE,DateTimeFormatter.ofPattern(ResponseConstants.RESPONSE_DATE_FORMAT).format(successfulTime)));
        }
    }


    public static class PhoneNumberNotVerification extends OtpException {

        private static final String MESSAGE = "Phone number: %s is not verified!";


        public PhoneNumberNotVerification( String phoneNumber) {
            super(String.format(MESSAGE.formatted(phoneNumber)));
        }
    }


}
