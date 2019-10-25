package com.cxm.personal.wechat.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cxm
 * @description
 * @date 2019-10-18 15:04
 **/
@Data
public class Weather implements Serializable {

    private Long id;
    private String cityCode;
    private String cityName;
    private String date;
    // 早晚天气
    private String dayWeather;
    private String nightWeather;
    // 早晚温度
    private String dayTemp;
    private String nightTemp;
    // 风力
    private String windLevel;

}
