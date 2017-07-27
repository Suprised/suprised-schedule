package com.dascom.quartz;

import org.springframework.stereotype.Component;

import com.dascom.common.quartz.SuiteQuartz;
import com.dascom.common.quartz.SuiteQuartzMethod;
import com.dascom.common.quartz.SuiteQuartzMethodAttr;

@SuiteQuartz
@Component
public class SuiteQuartzJob {

    @SuiteQuartzMethod(muiltMethod = {@SuiteQuartzMethodAttr(corn = "0 */1 * * * ?", desc = "每个一分钟执行一次")})
    public void job1() {
        System.out.println("job1()....");
    }

    @SuiteQuartzMethod(muiltMethod = {@SuiteQuartzMethodAttr(corn = "0 */2 * * * ?", desc = "每隔两分钟执行 一次")})
    public void job2() {
        System.out.println("job2()....");
    }
    
    @SuiteQuartzMethod(muiltMethod = {@SuiteQuartzMethodAttr(corn = "0 */3 * * * ?", desc = "每隔三分钟执行一次")})
    public void job3() {
        System.out.println("job3()....");
    }

    @SuiteQuartzMethod(muiltMethod = {@SuiteQuartzMethodAttr(corn = "0 */1 * * * ?", param = "参数问题", desc = "带参数的的执行，每隔一分钟执行一次")})
    public void jobByParams(String param) {
        System.out.println("jobByParams()....param: " + param);
    }
    
    @SuiteQuartzMethod(muiltMethod = {
            @SuiteQuartzMethodAttr(corn = "0 */1 * * * ?", param = "institutionDeclareStatistics", desc = "带参数的的执行，每隔一分钟执行一次"),
            @SuiteQuartzMethodAttr(corn = "0 */2 * * * ?", param = "institutionDeclareStatistics2", desc = "带参数的的执行，每隔一分钟执行一次"),
            @SuiteQuartzMethodAttr(corn = "0 */3 * * * ?", param = "institutionDeclareStatistics3", desc = "带参数的的执行，每隔一分钟执行一次"),
            @SuiteQuartzMethodAttr(corn = "0 */4 * * * ?", param = "institutionDeclareStatistics4", desc = "带参数的的执行，每隔一分钟执行一次"),
            @SuiteQuartzMethodAttr(corn = "0 */5 * * * ?", param = "institutionDeclareStatistics5", desc = "带参数的的执行，每隔一分钟执行一次")
    })
    public void jobParams(String stathreadId) {
        System.out.println("jobParams().... : " + stathreadId);
    }
    
}

