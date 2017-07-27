package com.dascom.common.quartz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明是一个调度类的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SuiteQuartz {

    /**
     * job名称 ：  默认为注解类的名称 
     */
    String jobName() default "";
    
    /**
     * job分组：默认为DEFAULT
     */
    String jobGroup() default "DEFAULT";
    
    /**
     * job描述
     */
    String desc() default "";
}
