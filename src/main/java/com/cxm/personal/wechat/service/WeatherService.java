package com.cxm.personal.wechat.service;

import com.cxm.personal.wechat.pojo.Weather;

/**
 * @author cxm
 * @description
 * @date 2019-10-23 15:11
 **/
public interface WeatherService {

    void insertWeather(String content);

    Weather getWeatherByNameAndDate(String content);

}
