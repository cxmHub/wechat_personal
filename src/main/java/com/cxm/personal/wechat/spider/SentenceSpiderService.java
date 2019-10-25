package com.cxm.personal.wechat.spider;

import com.cxm.personal.wechat.mapper.SentenceMapper;
import com.cxm.personal.wechat.pojo.Sentence;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cxm
 * @description
 * @date 2019-10-18 11:48
 **/
@Component
@Transactional
public class SentenceSpiderService implements PageProcessor {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Resource
    private SentenceMapper sentenceMapper;

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
        String content = new JsonPathSelector("$.content").select(substring);
        String note = new JsonPathSelector("$.note").select(substring);
        String translation = new JsonPathSelector("$.translation").select(substring);

        Sentence sentence = new Sentence();
        sentence.setContent(content);
        sentence.setNote(note);
        sentence.setTranslation(translation);
        sentence.setDate(dateFormat.format(new Date()));
        sentenceMapper.insert(sentence);

    }

//    public static void main(String[] args) {
//
//        String url = "http://sentence.iciba.com/index.php?callback=jQuery19006980600652004427_1571377838833&c=dailysentence&m=getdetail&title=2019-10-18";
//
//        // String url = "http://news.iciba.com/views/dailysentence/daily.html#!/detail/title/2019-10-18";
//        Spider.create(new SentenceSpiderService())
//                .addUrl(url)
//                .run();
//
//    }


}
