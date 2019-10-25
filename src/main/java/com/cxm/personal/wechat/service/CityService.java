package com.cxm.personal.wechat.service;

import com.cxm.personal.wechat.pojo.City;

/**
 * @author cxm
 * @description
 * @date 2019-10-23 11:51
 **/
public interface CityService {

    void insertCity(String content);

    City getCityByCityName(String cityName);

}
