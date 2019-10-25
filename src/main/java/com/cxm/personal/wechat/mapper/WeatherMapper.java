package com.cxm.personal.wechat.mapper;

import com.cxm.personal.wechat.pojo.Weather;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @author cxm
 * @description
 * @date 2019-10-18 16:00
 **/
public interface WeatherMapper {

    @Select("select * from weather where date = #{date} and cityCode=#{cityCode}")
    Weather selectByDate(String cityCode, String date);

    @Insert("insert into weather(cityCode,date,dayWeather,nightWeather,dayTemp,nightTemp,windLevel) value(#{cityCode},#{date},#{dayWeather},#{nightWeather},#{dayTemp},#{nightTemp},#{windLevel})")
    Integer insert(Weather weather);

    @Delete("delete from weather where id = #{id}")
    Integer delete(Long id);

}
