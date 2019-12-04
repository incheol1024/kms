package com.devworker.kms.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BeanUtilComponent {

    @Autowired
    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> object) {
        return applicationContext.getBean(object);
    }


}
