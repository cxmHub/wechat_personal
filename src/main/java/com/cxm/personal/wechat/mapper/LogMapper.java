package com.cxm.personal.wechat.mapper;

import com.cxm.personal.wechat.pojo.LogMessage;
import org.apache.ibatis.annotations.Insert;

/**
 * @author cxm
 * @description
 * @date 2019-11-11 11:26
 **/
public interface LogMapper {

    @Insert("insert into log(user_name, message, time) value(#{userName}, #{message}, #{time})")
    Integer insert(LogMessage log);

}
