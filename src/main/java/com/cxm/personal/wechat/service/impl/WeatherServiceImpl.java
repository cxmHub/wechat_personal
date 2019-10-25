package com.cxm.personal.wechat.service.impl;

import com.cxm.personal.wechat.mapper.CityMapper;
import com.cxm.personal.wechat.mapper.WeatherMapper;
import com.cxm.personal.wechat.pojo.City;
import com.cxm.personal.wechat.pojo.Weather;
import com.cxm.personal.wechat.service.WeatherService;
import com.cxm.personal.wechat.spider.WeatherSpiderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cxm
 * @description
 * @date 2019-10-23 15:12
 **/
@Service
public class WeatherServiceImpl implements WeatherService {

    @Resource
    private CityMapper cityMapper;
    @Resource
    private WeatherMapper weatherMapper;
    @Resource
    private WeatherSpiderService weatherSpiderService;

    @Value("${wechat.spider.weather}")
    private String url;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public void insertWeather(String content) {
        String cityName = content.substring(0, content.indexOf("天气"));
        City city = cityMapper.selectByCityName(cityName);

        Weather weather = weatherMapper.selectByDate(city.getCityCode(), dateFormat.format(new Date()));
        if (weather != null) {
            weatherMapper.delete(weather.getId());
        }
        Spider.create(weatherSpiderService)
                .addUrl(url + city.getCityCode() + ".shtml")
                .run();

    }

    @Override
    public Weather getWeatherByNameAndDate(String content) {
        String cityName = content.substring(0, content.indexOf("天气"));
        City city = cityMapper.selectByCityName(cityName);
        return weatherMapper.selectByDate(city.getCityCode(), dateFormat.format(new Date()));
    }


}
