package com.cxm.personal.wechat.rpc;

import com.alibaba.fastjson.JSON;
import com.cxm.personal.wechat.rpc.res.MeiRiYiJu;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author cxm
 */
@Component
public class ICiBaRPC {

    @Value("${iciba.meiriyiju}")
    private String url;

    @Resource
    private RestTemplateConfig restTemplateConfig;


    public MeiRiYiJu getMeiRiYiJu() {
        //  执行HTTP请求
        ResponseEntity<String> response =
                restTemplateConfig.restTemplate().getForEntity(url, String.class);
        return JSON.parseObject(response.getBody(), MeiRiYiJu.class);
    }

}
