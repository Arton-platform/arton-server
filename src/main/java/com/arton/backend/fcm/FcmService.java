package com.arton.backend.fcm;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class FcmService {
    private final FcmRepository fcmRepository;
    private final FirebaseMessaging firebaseMessaging;

    public void sendPush(FcmRequestDto requestDto) throws FirebaseMessagingException {
        FcmEntity fcmEntity = fcmRepository.findByUserId(requestDto.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND.getMessage(), ErrorCode.USER_NOT_FOUND));

        if (!StringUtils.hasText(fcmEntity.getToken())) {
            throw new CustomException(ErrorCode.FCM_TOKEN_ERROR.getMessage(), ErrorCode.FCM_TOKEN_ERROR);
        }
        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(fcmEntity.getToken())
                .setNotification(notification)
                .build();
        // send
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            // token delete
            fcmRepository.deleteById(fcmEntity.getId());
            throw e;
        }
    }
}
