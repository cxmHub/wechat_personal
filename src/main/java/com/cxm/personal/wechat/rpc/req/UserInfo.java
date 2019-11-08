package com.cxm.personal.wechat.rpc.req;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author cxm
 * @description
 * @date 2019-11-07 10:27
 **/
@Data
public class UserInfo {
    // 必填
//    @Value("${tuling.apikey}")
    private String apiKey;
    // 必填
    private String userId;
    private String groupId;
    private String userIdName;
}
