package com.cxm.personal.wechat.rpc.req;

import lombok.Data;

/**
 * @author cxm
 * @description
 * @date 2019-11-07 10:26
 **/
@Data
public class BaseRequest {
    private Integer reqType;
    private Perception perception;
    private UserInfo userInfo;
}
