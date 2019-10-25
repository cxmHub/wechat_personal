package com.cxm.personal.wechat.service.impl;

import com.cxm.personal.wechat.mapper.SentenceMapper;
import com.cxm.personal.wechat.pojo.Sentence;
import com.cxm.personal.wechat.service.SentenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceProvider;

/**
 * @author cxm
 * @description
 * @date 2019-10-23 19:46
 **/
@Service
public class SentenceServiceImpl implements SentenceService {

    @Resource
    private SentenceMapper sentenceMapper;

    @Override
    public Sentence getSentenceByDate(String Date) {
        return sentenceMapper.selectByDate(Date);
    }
}
