package com.cxm.personal.wechat.rpc;

import com.alibaba.fastjson.JSON;
import com.cxm.personal.wechat.rpc.res.BaseResponse;
import com.cxm.personal.wechat.rpc.res.QingYunKeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author cxm
 * @description 青云客
 * @date 2019-11-11 10:18
 **/
@Component
public class QingYunKeRPC {

    @Value("${qingyunke.url}")
    private String url;

    @Resource
    private RestTemplateConfig restTemplateConfig;


    public QingYunKeResponse chatWithQingYunKeRoot(String content) {

        //  执行HTTP请求
        ResponseEntity<String> response =
                restTemplateConfig.restTemplate().getForEntity(url, String.class, content);
        return JSON.parseObject(response.getBody(), QingYunKeResponse.class);
    }


}
