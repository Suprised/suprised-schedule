package com.dascom.common.quartz;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SuiteQuartzMethod {

    SuiteQuartzMethodAttr[] muiltMethod();
}

@Deprecated
@interface MethodAttr {
    
    /**
     * 调度触发的corn表达式
     */
    String corn() default "";

    /**
     * 触发器的名称： 默认 job名称.方法名称
     */
    String name() default "";

    /**
     * 触发器分组
     */
    String group() default "DEFAULT";

    /**
     * 描述
     */
    String desc() default "";

    /**
     * 参数：目前只支持字符串形式的参数
     */
    String param() default "";
}
