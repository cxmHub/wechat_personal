package com.cxm.personal.wechat.rpc;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author cxm
 * @description
 * @date 2019-11-08 15:21
 **/
@Component
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

}
