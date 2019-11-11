package com.cxm.personal.wechat.service.impl;

import com.cxm.personal.wechat.mapper.LogMapper;
import com.cxm.personal.wechat.pojo.LogMessage;
import com.cxm.personal.wechat.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author cxm
 * @description
 * @date 2019-11-11 11:31
 **/
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;

    @Override
    public void insertLog(String userName, String message) {
        LogMessage logMessage = new LogMessage();
        logMessage.setUserName(userName);
        logMessage.setMessage(message);
        logMessage.setTime(new Date());
        logMapper.insert(logMessage);
    }
}
