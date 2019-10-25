package com.cxm.personal.wechat.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cxm
 * @description
 * @date 2019-10-23 11:21
 **/
@Data
public class City implements Serializable {

    private Long id;
    private String cityName;
    private String cityCode;

}
