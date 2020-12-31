package com.cxm.personal.wechat.rpc;

import com.cxm.personal.wechat.WechatApplicationTests;
import com.cxm.personal.wechat.pojo.AccessToken;
import com.cxm.personal.wechat.rpc.res.BaseResponse;
import com.cxm.personal.wechat.schedule.ScheduleTask;
import com.cxm.personal.wechat.service.CityService;
import com.cxm.personal.wechat.service.impl.WeChatServiceImpl;
import com.cxm.personal.wechat.utils.WeixinUtil;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author cxm
 * @description
 * @date 2019-11-08 14:55
 **/
public class RPCTest extends WechatApplicationTests {

    @Resource
    TuLingRPC tuLingRPC;
    @Resource
    QingYunKeRPC qingYunKeRPC;

    @Resource
    CityService cityService;
    @Resource
    WeChatServiceImpl weChatService;
    @Resource
    ScheduleTask scheduleTask;


    @Test
    public void test() {
        BaseResponse hello = tuLingRPC.chatWithRoot("每天可以请求几次");
        System.out.println(hello.toString());

    }


    @Test
    public void QingYunKeTest() {
        System.out.println(qingYunKeRPC.chatWithQingYunKeRoot("天气北京"));
    }


    @Test
    public void testReplace() throws Exception {
        AccessToken accessToken = WeixinUtil.getAccessToken();
        System.out.println(accessToken.getToken());
    }


    @Test
    public void test1() throws Exception {
//        weChatService.getMediaId();

    }

    @Test
    public void testApiICiBaTask() throws Exception {
//        System.out.println(weChatService.getMediaId("hR-tq819joXUx8EFhr9R84j9hLRZXExBus1WQ9drf-4"));

        scheduleTask.apiICiBaTask();

//        weChatService.getMediaList();

    }

}
