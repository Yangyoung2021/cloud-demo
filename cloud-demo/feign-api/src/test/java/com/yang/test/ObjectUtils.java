package com.yang.test;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class ObjectUtils {

    public boolean allFieldsAreNotNull(Object obj) {
        Class<?> cl = obj.getClass();
        Method[] declaredMethods = cl.getDeclaredMethods();


/*        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.getName().startsWith("get")) {
                Object invoke = declaredMethod.invoke(obj);
                if (invoke == null || (invoke instanceof String && !StringUtils.isNotBlank((String) invoke))) {
                    return false;
                }
            }
        }*/

        Arrays.stream(declaredMethods).filter(method -> method.getName().startsWith("get") || method.getName().startsWith("is")).forEach(method -> {
            Object resultValue;
            try {
                resultValue = method.invoke(obj);
                if (resultValue == null || (resultValue instanceof String && !StringUtils.isNotBlank((String) resultValue))) {
                    // 获取空属性列名
                    String columnNameOfNull = getColumnNameOfNull(cl, method);

                    throw  new IllegalArgumentException(columnNameOfNull + "列不能含有空值");
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });


        return true;
    }

    private String getColumnNameOfNull(Class<?> cl, Method method) {
        AtomicReference<String> columnName = new AtomicReference<>();
        // 获取属性对应的列名
        String fieldOfNull = method.getName().substring(3).toLowerCase();
        Arrays.stream(cl.getDeclaredFields()).filter(field -> fieldOfNull.equals(field.getName())).forEach(field -> {
            // 通过空属性对象获取其上面的注解
            Value annotation = field.getAnnotation(Value.class);
            Method[] methods = annotation.getClass().getDeclaredMethods();
            try {
                for (Method method1 : methods) {
                    if ("value".equals(method1.getName())) {
                        String annotationFieldValue = (String) method1.invoke(annotation);
                        columnName.set(annotationFieldValue);
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return columnName.get();
    }


    @Test
    public void test() throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        ArrayList<UserFo> userFos = new ArrayList<>();



        for (int i = 0; i < 10; i++) {
            UserFo userFo = new UserFo();
            userFo.setAge(i + 30);
            userFo.setName(UUID.randomUUID().toString().substring(3, 19));
            userFo.setAddress(UUID.randomUUID().toString().substring(3, 19));
            userFo.setIsTall(UUID.randomUUID().toString().substring(3, 19));
            userFos.add(userFo);
        }

        List<User> users = new ListPropertiesCopy<UserFo, User>().copyProperties(userFos, User.class);

        for (User user : users) {
            System.out.println(user);
        }


    }


    @Data
    static class Test1 {
        @Value(value = "姓名")
        private String name;
        @Value(value = "年龄")
        private Integer age;
    }
}
