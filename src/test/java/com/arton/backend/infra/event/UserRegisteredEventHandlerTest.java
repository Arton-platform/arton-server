package com.arton.backend.infra.event;

import com.arton.backend.infra.shared.exception.CustomException;
import com.arton.backend.infra.shared.exception.ErrorCode;
import com.arton.backend.infra.utils.SuperClassReflectionUtils;
import com.arton.backend.user.application.port.out.UserRepositoryPort;
import com.arton.backend.user.domain.SignupType;
import com.arton.backend.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@SpringBootTest
class UserRegisteredEventHandlerTest {

    @Autowired
    UserRepositoryPort userRepository;


    @Test
    void getFieldNameTest() {
        Field[] declaredFields = User.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            System.out.println("name = " + name);
        }
    }

    @Test
    void getFieldNameBySuperClassReflectionUtils() {
        List<Field> allFields = SuperClassReflectionUtils.getAllFields(User.class);
        for (Field allField : allFields) {
            String name = allField.getName();
            System.out.println("allField = " + name);
            String method = "get "+name;
            String lowerCamelCase = toLowerCamelCase(method);
            System.out.println("lowerCamelCase = " + lowerCamelCase);

        }
    }

    @Test
    void getAllMethodTest() {
        List<Method> allMethods = SuperClassReflectionUtils.getOnlyClassMethods(User.class);
        for (Method allMethod : allMethods) {
            System.out.println("allMethod = " + allMethod.getName());
        }
    }

    @Transactional
    @Test
    void doMethodTest() {
        String content = "${nickname}님 안녕하세요. ${nickname}메일 보내드립니다.";
        User userEntity = User.builder().userStatus(true).email("j67310@gmail.com")
                .nickname("고구마")
                .signupType(SignupType.ARTON)
                .build();
        User user = userRepository.save(userEntity);
        content = replaceTemplateBody(content, user);
        System.out.println("content = " + content);
    }

    String toLowerCamelCase(String text) {
        String[] words = text.split("[\\W_]+");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (i == 0) {
                word = word.isEmpty() ? word : word.toLowerCase();
            } else {
                word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
            }
            builder.append(word);
        }
        return builder.toString();
    }

    private String replaceTemplateBody(String content, Object obj) {
        List<String> fields = SuperClassReflectionUtils.getAllFields(obj.getClass()).stream().map(Field::getName).collect(Collectors.toList());
        List<String> methods = SuperClassReflectionUtils.getOnlyClassMethods(obj.getClass()).stream().map(Method::getName).collect(Collectors.toList());
        Class<?> classObj = obj.getClass();
        for (String field : fields) {
            String old = "${" + field + "}";
            if (content.contains(old)) {
                for (String method : methods) {
                    if (method.toLowerCase(Locale.ROOT).equals(("get" + field).toLowerCase(Locale.ROOT))) {
                        try {
                            Method declaredMethod = classObj.getDeclaredMethod(method);
                            String value = (String)declaredMethod.invoke(obj);
                            content = content.replace(old, value);
                        } catch (Exception e) {
                            throw new CustomException(ErrorCode.AUTO_MAIL_ERROR.getMessage(), ErrorCode.AUTO_MAIL_ERROR);
                        }
                    }
                }
            }
        }
        return content;
    }


}