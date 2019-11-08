package com.cxm.personal.wechat.rpc.res;

import lombok.Data;

import java.util.Map;

/**
 * @author cxm
 * @description
 * @date 2019-11-07 10:35
 **/
@Data
public class Intent {
    private int code;
    private String intentName;
    private String actionName;
    private Map parameters;

}
