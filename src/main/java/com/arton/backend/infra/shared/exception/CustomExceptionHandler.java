package com.arton.backend.infra.shared.exception;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class CustomExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        log.error("CUSTOM EXCEPTION {}", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeError(HttpMediaTypeNotSupportedException e) {
        log.error("HttpMediaTypeNotSupportedException {}", e.getMessage());
        return ResponseEntity.status(ErrorCode.UNSUPPORTED_MEDIA_ERROR.getStatus())
                .body(new ErrorResponse(ErrorCode.UNSUPPORTED_MEDIA_ERROR));
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleFileSizeLimitExceededException(FileSizeLimitExceededException e) {
        log.error("FileSizeLimitExceededException {}", e.getMessage());
        return ResponseEntity.status(ErrorCode.EXCEED_LIMITED_SIZE_ERROR.getStatus())
                .body(new ErrorResponse(ErrorCode.EXCEED_LIMITED_SIZE_ERROR));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("MaxUploadSizeExceededException {}", e.getMessage());
        return ResponseEntity.status(ErrorCode.EXCEED_LIMITED_SIZE_ERROR.getStatus())
                .body(new ErrorResponse(ErrorCode.EXCEED_LIMITED_SIZE_ERROR));
    }

    @ExceptionHandler(FirebaseMessagingException.class)
    public ResponseEntity<ErrorResponse> handleFirebaseMessagingException(FirebaseMessagingException e) {
        log.error("FirebaseMessagingException {}", e.getMessage());
        return ResponseEntity.status(ErrorCode.FCM_SEND_ERROR.getStatus())
                .body(new ErrorResponse(ErrorCode.FCM_SEND_ERROR));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> logException(Exception e) {
        log.error("UNDEFINED ERROR!! {}" , e);
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
