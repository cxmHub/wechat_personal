package com.cxm.personal.wechat;


import com.cxm.personal.wechat.schedule.ScheduleTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WechatApplicationTests {


    @Resource
    ScheduleTask scheduleTask;


    @Test
    public void contextLoads() throws Exception{
        scheduleTask.apiICiBaTask();
    }

}
