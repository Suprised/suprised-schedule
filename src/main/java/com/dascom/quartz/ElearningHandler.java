package com.dascom.quartz;

import org.springframework.stereotype.Component;

import com.dascom.common.quartz.SuiteQuartz;
import com.dascom.common.quartz.SuiteQuartzMethod;
import com.dascom.common.quartz.SuiteQuartzMethodAttr;

@SuiteQuartz
@Component
public class ElearningHandler {

    /**
     * 资源xml文件生成任务
     */
    @SuiteQuartzMethod(muiltMethod = { @SuiteQuartzMethodAttr(corn = "0 */1 * * * ?", desc = "每个一分钟执行一次") })
    public void resourceXMLJob() {
        System.out.println("resourceXMLJob()....");
    }

    @SuiteQuartzMethod(muiltMethod = { @SuiteQuartzMethodAttr(corn = "0 */2 * * * ?", desc = "每隔两分钟执行 一次") })
    public void resourceCheckFileJob() {
        System.out.println("resourceCheckFileJob()....");
    }

    @SuiteQuartzMethod(muiltMethod = { @SuiteQuartzMethodAttr(corn = "0 */3 * * * ?", desc = "每隔三分钟执行一次") })
    public void resourceClickDownloadCount() {
        System.out.println("resourceClickDownloadCount()....");
    }

}
