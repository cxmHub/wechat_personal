package com.cxm.personal.wechat.service.impl;

import com.cxm.personal.wechat.mapper.CityMapper;
import com.cxm.personal.wechat.pojo.City;
import com.cxm.personal.wechat.service.CityService;
import com.cxm.personal.wechat.spider.CitySpiderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;

/**
 * @author cxm
 * @description
 * @date 2019-10-23 11:27
 **/
@Service
public class CityServiceImpl implements CityService {

    @Value("${wechat.spider.city}")
    private String url;

    @Resource
    private CityMapper cityMapper;
    @Resource
    private CitySpiderService citySpiderService;

    @Override
    public void insertCity(String content) {

        String cityName = content.substring(0, content.indexOf("天气"));
        City city = cityMapper.selectByCityName(cityName);
        if (city == null) {
            Spider.create(citySpiderService)
                    .addUrl(url + cityName)
                    .run();
        }
    }

    @Override
    public City getCityByCityName(String content) {
        return cityMapper.selectByCityName(content.substring(0, content.indexOf("天气")));
    }

}
