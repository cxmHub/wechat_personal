package com.cxm.personal.wechat.spider;

import com.cxm.personal.wechat.mapper.CityMapper;
import com.cxm.personal.wechat.pojo.City;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import javax.annotation.Resource;

/**
 * @author cxm
 * @description
 * @date 2019-10-23 10:52
 **/
@Component
@Transactional
public class CitySpiderService implements PageProcessor {

    @Resource
    private CityMapper cityMapper;

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
        String rawText = page.getRawText();
        String substring = rawText.substring(rawText.indexOf("(") + 1, rawText.lastIndexOf(")"));
        String content = new JsonPathSelector("$[0].ref").select(substring);
        String[] split = content.split("~");

        String code = split[0];
        String name = split[2];

        City city = new City();
        city.setCityCode(code);
        city.setCityName(name);

        cityMapper.insert(city);

    }

//    public static void main(String[] args) {
//        String url = "http://toy1.weather.com.cn/search?cityname="+"北京";
//
//        Spider.create(new CitySpiderService())
//                .addUrl(url)
//                .run();
//
//    }


}
