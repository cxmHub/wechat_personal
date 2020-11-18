package com.cxm.personal.wechat.schedule;

import com.cxm.personal.wechat.mapper.SentenceMapper;
import com.cxm.personal.wechat.pojo.Sentence;
import com.cxm.personal.wechat.rpc.ICiBaRPC;
import com.cxm.personal.wechat.rpc.res.MeiRiYiJu;
import com.cxm.personal.wechat.spider.SentenceSpiderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cxm
 * @description
 * @date 2019-10-18 14:56
 **/
@Component
public class ScheduleTask {

    @Resource
    private SentenceSpiderService sentenceSpiderService;
    @Resource
    private SentenceMapper sentenceMapper;

    @Resource
    private ICiBaRPC iCiBaRPC;

    @Value("${wechat.spider.sentence}")
    private String urlSentence;

//    @Value("${wechat.spider.weather}")
//    private String urlWeather;

   private SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd ");


    @Scheduled(cron = "1 0 0 * * ?") // 每天凌晨0：0:1爬取
    public void apiICiBaTask(){
        String time = format.format(new Date());
        Sentence sentence = sentenceMapper.selectByDate(time);
        if (sentence != null){
            sentenceMapper.delete(sentence.getId());
        }

        MeiRiYiJu meiRiYiJu = iCiBaRPC.getMeiRiYiJu();
        Sentence insertSentence = new Sentence();
        insertSentence.setFenxiangImg(meiRiYiJu.getFenxiang_img());
        insertSentence.setContent(meiRiYiJu.getContent());
        insertSentence.setNote(meiRiYiJu.getNote());
        insertSentence.setTranslation(meiRiYiJu.getTranslation());
        insertSentence.setDate(meiRiYiJu.getDateline());
        sentenceMapper.insert(insertSentence);
    }

    /**
     * 定时爬取
     */
    @Scheduled(cron = "1 0 0 * * ?") // 每天凌晨0：0:1爬取
//    @Scheduled(fixedDelay = 100000)
    private void spiderTask() {
        String time = format.format(new Date());
        Sentence sentence = sentenceMapper.selectByDate(time);
        if (sentence != null){
            sentenceMapper.delete(sentence.getId());
        }

        // 爬句子
        Spider.create(sentenceSpiderService)
                .addUrl(urlSentence + time)
                .run();

//        // 爬天气
//        Spider.create(weatherSpiderService)
//                .addUrl(urlWeather)
//                .run();
    }


//    @Scheduled(cron = "0 5 7 * * ?") // 每天七点过五分
//    @Scheduled(cron = "1/5 * * * * ?")
//    private void messageTask() {
//
//        Sentence sentence = sentenceMapper.selectByDate(time);
//        Weather weather = weatherMapper.selectByDate(time);
//
//        if (sentence != null && weather != null) {
//            String weatherSentence = dealWithWeather(weather);
//            String content = sentence.getContent();
//            String note = sentence.getNote();
//            String result = weatherSentence + content + note;
//            System.out.println(result);
//        }
//
//    }

//    // 处理message内容
//    private String dealWithWeather(Weather weather) {
//        //"白天晴,晚上晴,1-24度,烟示风向。"
//        String dayWeather = weather.getDayWeather();
//        String nightWeather = weather.getNightWeather();
//        String nightTemp = weather.getNightTemp();
//        String dayTemp = weather.getDayTemp();
//        String windLevel = weather.getWindLevel();
//        return String.format("今天白天%s,晚上%s,%s-%s,%s。",
//                dayWeather, nightWeather, nightTemp, dayTemp, windLevel);
//    }


}
