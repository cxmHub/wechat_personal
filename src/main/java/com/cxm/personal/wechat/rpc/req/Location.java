package com.cxm.personal.wechat.rpc.req;

import lombok.Data;

/**
 * @author cxm
 * @description
 * @date 2019-11-07 10:25
 **/
@Data
public class Location {
    // 必填
    private String city;
    private String province;
    private String street;

}
