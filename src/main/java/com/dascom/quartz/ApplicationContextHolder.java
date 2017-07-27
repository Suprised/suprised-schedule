package com.dascom.quartz;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }
    
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}
