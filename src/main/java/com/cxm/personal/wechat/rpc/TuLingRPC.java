package com.cxm.personal.wechat.rpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cxm.personal.wechat.rpc.req.BaseRequest;
import com.cxm.personal.wechat.rpc.req.InputText;
import com.cxm.personal.wechat.rpc.req.Perception;
import com.cxm.personal.wechat.rpc.req.UserInfo;
import com.cxm.personal.wechat.rpc.res.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cxm
 * @description
 * @date 2019-11-07 11:00
 **/
@Component
public class TuLingRPC {

    @Value("${tuling.apikey}")
    private String apikey;

    @Value("${tuling.url}")
    private String url;

    @Resource
    private RestTemplateConfig restTemplateConfig;

    public BaseResponse chatWithRoot(String request) {

        // 头
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        //body
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.setReqType(0);
        Perception perception = new Perception();
        perception.setInputText(new InputText(request));
        baseRequest.setPerception(perception);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("chenxinmao");
        userInfo.setApiKey(apikey);
        baseRequest.setUserInfo(userInfo);

//        Map<String, Object> params = new HashMap<>();
//        params.put("date", baseRequest);
        String jsonObject = JSONObject.toJSONString(baseRequest);

//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("date", jsonObject);
        HttpEntity<String> requestEntity =
                new HttpEntity<>(jsonObject, requestHeaders);
        //  执行HTTP请求
        ResponseEntity<String> response =
                restTemplateConfig.restTemplate().exchange(url, HttpMethod.POST, requestEntity, String.class);  //最后的参数需要用String.class  使用其他的会报错
        return JSON.parseObject(response.getBody(), BaseResponse.class);
    }


}
