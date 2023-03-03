package com.arton.backend.infra.event;

import com.arton.backend.infra.utils.SuperClassReflectionUtils;
import com.arton.backend.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Field;
import java.util.List;

@SpringBootTest
class UserRegisteredEventHandlerTest {

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
            System.out.println("allField = " + allField.getName());
        }
    }
}