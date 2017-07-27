package com.dascom.common.quartz;

/**
 * TODO : 加到suite中的话还缺少产品属性，也就是该调度只有在存在某些产品或者模块时才生效。
 */
public @interface SuiteQuartzMethodAttr {
    
    /**
     * 调度触发的corn表达式 
     */
    String corn() default "";
    
    /**
     * 触发器的名称,不能重复： 默认 job名称.方法名称
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
     * 参数：目前只支持字符串形式的参数, 是为了避免suite各个版本之间序列化与反序列化的版本问题。
     */
    String param() default "";
    
    /**
     * TODO:调度方法必须在购买指定产品下才会被启用(还未实现)
     */
    String product() default "";
}
