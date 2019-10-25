package com.cxm.personal.wechat.mapper;

import com.cxm.personal.wechat.pojo.City;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author cxm
 * @description
 * @date 2019-10-23 11:35
 **/
public interface CityMapper {

    @Select("select * from city where city_name = #{cityName}")
    @Results({
            @Result(property = "cityName", column = "city_name"),
            @Result(property = "cityCode", column = "city_code"),
            @Result(property = "id", column = "id")
    })
    City selectByCityName(String cityName);

    @Insert("insert into city(city_name,city_code) value(#{cityName},#{cityCode})")
    Integer insert(City city);



}
