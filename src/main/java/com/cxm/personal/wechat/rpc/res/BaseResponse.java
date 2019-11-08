package com.cxm.personal.wechat.rpc.res;

import lombok.Data;

import java.util.List;

/**
 * @author cxm
 * @description
 * @date 2019-11-08 18:31
 **/
@Data
public class BaseResponse {
    private Intent intent;
    private List<Results> results;

}
