package com.arton.backend.administer.mail.application.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "메일 전송")
@ToString
public class AdminMailSendDto {
    private String title;
    private List<String> emails = new ArrayList<>();

    @Builder
    public AdminMailSendDto(String title, List<String> emails) {
        this.title = title;
        this.emails = emails;
    }
}
