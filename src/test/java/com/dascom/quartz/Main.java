package com.dascom.quartz;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 按注解加载调度信息的入口：所有相关类都在com.dascom包下
 */
public class Main {

    
    /**
     * 执行方式：
     * 1，创建一个数据库：找到对应的sql创建数据库表（src/main/resources/tables_*.sql）
     * 2，修改src/main/resources/spring/application.properties下的数据库连接信息
     * 3，执行下面main方法
     */
    public static void main(String[] args) throws Exception {
        
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        // ApplicationContextHolder.setApplicationContext(context);
        System.in.read(); // 按任意键退出    
    }
    
    /**
     * 新添加一个调度的方法：
     * 
     * 在com.dascom.quartz下创建一个类：具体参考ElearningHandler类，然后再执行main方法查看数据库中的效果。
     */
    
}
