package com.cxm.personal.wechat.rpc;

import com.alibaba.fastjson.JSONObject;
import com.cxm.personal.wechat.WechatApplicationTests;
import com.cxm.personal.wechat.rpc.res.BaseResponse;
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


    @Test
    public void test() {
        BaseResponse hello = tuLingRPC.chatWithRoot("每天可以请求几次");
        System.out.println(hello.toString());

    }


    @Test
    public void QingYunKeTest(){
        System.out.println(qingYunKeRPC.chatWithQingYunKeRoot("天气北京"));
    }


    @Test
    public void testReplace(){
        String a = "https://mp.weixin.qq.com/mp/profile_ext?action=home&amp;__biz=MzAxNTI2ODk0NA==#wechat_redirect";
        System.out.println(a.replace("&amp;", "&"));
    }

}
