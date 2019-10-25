package com.cxm.personal.wechat.spider;

import com.cxm.personal.wechat.enums.WindLevelEnum;
import com.cxm.personal.wechat.mapper.WeatherMapper;
import com.cxm.personal.wechat.pojo.Weather;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cxm
 * @description
 * @date 2019-10-18 11:06
 **/
@Component
@Transactional
public class WeatherSpiderService implements PageProcessor {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private WeatherMapper weatherMapper;


    private Site site = Site.me()
            .setRetryTimes(3)
            .setSleepTime(1000)
            .setCharset("UTF-8")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");


    @Override
    public Site getSite() {
        return this.site;
    }


    @Override
    public void process(Page page) {


        String url = page.getUrl().get();
        // 日期白天 //*[@id="today"]/div[2]/ul/li[1]/h1
        String day = page.getHtml().xpath("//*[@id=\"today\"]/div[2]/ul/li[1]/h1/text()").get();
        // 天气 //*[@id="today"]/div[2]/ul/li[1]/p[1]
        String dayWeather = page.getHtml().xpath("//*[@id=\"today\"]/div[2]/ul/li[1]/p[1]/text()").get();
        // 温度 //*[@id="today"]/div[2]/ul/li[1]/p[2]/span
        String dayTemp = page.getHtml().xpath("//*[@id=\"today\"]/div[2]/ul/li[1]/p[2]/span/text()").get();
        // 风级 //*[@id="today"]/div[2]/ul/li[1]/p[3]/span
        String dayWind = page.getHtml().xpath("//*[@id=\"today\"]/div[2]/ul/li[1]/p[3]/span/text()").get();

        // 日期夜晚 //*[@id="today"]/div[2]/ul/li[2]/h1
//        String night = page.getHtml().xpath("//*[@id=\"today\"]/div[2]/ul/li[2]/h1/text()").get();
        // 天气 //*[@id="today"]/div[2]/ul/li[2]/p[1]
        String nightWeather = page.getHtml().xpath("//*[@id=\"today\"]/div[2]/ul/li[1]/p[1]/text()").get();
        // 温度  //*[@id="today"]/div[2]/ul/li[2]/p[2]/span
        String nightTemp = page.getHtml().xpath("//*[@id=\"today\"]/div[2]/ul/li[2]/p[2]/span/text()").get();
        // 风级 //*[@id="today"]/div[2]/ul/li[2]/p[3]/span <3级
        String nightWind = page.getHtml().xpath("//*[@id=\"today\"]/div[2]/ul/li[2]/p[3]/span/text()").get();

        //"北京今天白天晴,晚上晴,1-24度,烟示风向。"
        Weather weather = new Weather();
        weather.setCityCode(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
        weather.setDate(dateFormat.format(new Date()));
        weather.setDayWeather(dayWeather);
        weather.setNightWeather(nightWeather);
        weather.setDayTemp(dayTemp);
        weather.setNightTemp(nightTemp);
        String ji = dayWind.substring(dayWind.indexOf("<") + 1, dayWind.lastIndexOf("级"));
        // 有时候 风级 是 3-4
        int level = 0;
        if (ji.contains("-")) {
            level = Integer.parseInt(ji.substring(0, ji.lastIndexOf("-")));
        } else {
            level = Integer.parseInt(ji);
        }
        weather.setWindLevel(WindLevelEnum.getDescriptionByLevel(level));
        weatherMapper.insert(weather);
    }

//    public static void main(String[] args) {
//        String url = "http://www.weather.com.cn/weather1d/101010100.shtml";
//        Spider.create(new WeatherSpiderService())
//                .addUrl(url)
//                .run();
//    }
}
