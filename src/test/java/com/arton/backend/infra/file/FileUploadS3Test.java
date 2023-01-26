package com.arton.backend.infra.file;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import static org.junit.jupiter.api.Assertions.*;

@Profile("local")
@SpringBootTest
class FileUploadS3Test {

    @Autowired
    FileUploadUtils fileUploadUtils;

    @Test
    void getFileContentTest() {
        String fileContent = fileUploadUtils.getFileContent("arton/mail/passwordResetMail.html");
        System.out.println("fileContent = " + fileContent);
        // change password
        String temp = "temp323232";
        String replace = fileContent.replace("${password}", temp);
        System.out.println("replace = " + replace);
    }
}