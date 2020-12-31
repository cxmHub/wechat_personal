package com.cxm.personal.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.cxm.personal.wechat.pojo.*;
import com.cxm.personal.wechat.rpc.res.BaseResponse;
import com.cxm.personal.wechat.rpc.res.QingYunKeResponse;
import com.cxm.personal.wechat.rpc.res.Results;
import com.cxm.personal.wechat.rpc.res.Values;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cxm
 * @description
 * @date 2019-10-22 15:08
 **/
public class MessageUtil {

    public static String MESSAGE_TEXT = "text";
    public static String MESSAGE_IMAGE = "image";
    public static String MESSAGE_VOICE = "voice";
    public static String MESSAGE_LINK = "link";
    public static String MESSAGE_LOCATION = "location";
    public static String MESSAGE_EVENT = "event";
    public static String MESSAGE_SUBSCRIBE = "subscribe";
    public static String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static String MESSAGE_CLICK = "CLICK";
    public static String MESSAGE_VIEW = "VIEW";
    public static String MESSAGE_NEWS = "NEWS";

    // 修改成你服务器的地址 注意 最好是域名地址，不然访问的时候会警告
    private static String host = "http://47.94.174.237/";


    /**
     * xml 转map
     *
     * @param request
     * @return
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) {

        Map<String, String> map = new HashMap<>();
        SAXReader saxReader = new SAXReader();

        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            Document document = saxReader.read(inputStream);
            // 得到根节点
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();
            for (Element element : elements) {
                map.put(element.getName(), element.getText());
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * text message转 xml
     *
     * @return
     */
    public static String messageToXml(MessageText messageText) {
        XStream xstream = new XStream();
        xstream.alias("xml", messageText.getClass());
        return xstream.toXML(messageText);
    }

    /**
     * news message转 xml
     *
     * @return
     */
    public static String newsMessageToXml(MessageNews messageNews) {
        XStream xstream = new XStream();
        xstream.alias("xml", messageNews.getClass());
        xstream.alias("item", Item.class);
        return xstream.toXML(messageNews);
    }


    /**
     * 关注信息
     *
     * @param fromUserName 发送消息需要交换用户信息
     * @param toUserName   发送消息需要交换用户信息
     * @return
     */
    public static String subscribeMessage(String fromUserName, String toUserName) {
        MessageText messageText = new MessageText();
        messageText.setToUserName(fromUserName);
        messageText.setFromUserName(toUserName);
        messageText.setCreateTime(new Date().getTime());
        messageText.setContent("欢迎关注！\n" + "1、回复0，可查看往期内容\n" +
                "2、回复1，每日一句英语学习\n" +
                "3、回复其他内容可以在线与价值一亿的智能聊天机器人聊天");
        messageText.setMsgType(MESSAGE_TEXT);
        return messageToXml(messageText);
    }


    /**
     * 智能聊天
     *
     * @param fromUserName
     * @param toUserName
     * @param content
     * @return
     */
    public static String chatWithMe(String fromUserName, String toUserName, String content) {
        MessageText messageText = new MessageText();
        messageText.setToUserName(fromUserName);
        messageText.setFromUserName(toUserName);
        messageText.setCreateTime(new Date().getTime());

        content = content.replace("吗", "")
                .replace("谁", "大帅批")
                .replace("你", "我")
                .replace("？", "！")
                .replace("?", "！");

        messageText.setContent(content);
        messageText.setMsgType(MESSAGE_TEXT);
        return messageToXml(messageText);
    }


    public static String weatherQuery(String fromUserName, String toUserName, Weather weather) {
        MessageText messageText = new MessageText();
        messageText.setToUserName(fromUserName);
        messageText.setFromUserName(toUserName);
        messageText.setCreateTime(new Date().getTime());
        messageText.setContent(dealWithWeather(weather));
        messageText.setMsgType(MESSAGE_TEXT);
        return messageToXml(messageText);
    }

    /**
     * 每日一句
     *
     * @param fromUserName
     * @param toUserName
     * @param sentence
     * @return
     */
    public static String daySentence(String fromUserName, String toUserName, Sentence sentence) {
        MessageText messageText = new MessageText();
        messageText.setToUserName(fromUserName);
        messageText.setFromUserName(toUserName);
        messageText.setCreateTime(new Date().getTime());
        messageText.setContent(dealWithSentence(sentence));
        messageText.setMsgType(MESSAGE_TEXT);
        return messageToXml(messageText);
    }

    public static String daySentenceImage(String fromUserName, String toUserName, Sentence sentence) {
        MessageText messageText = new MessageText();
        messageText.setToUserName(fromUserName);
        messageText.setFromUserName(toUserName);
        messageText.setCreateTime(new Date().getTime());
        messageText.setContent(dealWithSentence(sentence));
        messageText.setMsgType(MESSAGE_IMAGE);
        return messageToXml(messageText);
    }


    /**
     * 错误消息
     *
     * @param fromUserName
     * @param toUserName
     * @param result
     * @return
     */
    public static String errorResult(String fromUserName, String toUserName, String result) {
        MessageText messageText = new MessageText();
        messageText.setToUserName(fromUserName);
        messageText.setFromUserName(toUserName);
        messageText.setCreateTime(new Date().getTime());
        messageText.setContent(result);
        messageText.setMsgType(MESSAGE_TEXT);
        return messageToXml(messageText);
    }

    // 处理message内容
    private static String dealWithWeather(Weather weather) {
        //"白天晴,晚上晴,1-24度,烟示风向。"
        String cityName = weather.getCityName();
        String dayWeather = weather.getDayWeather();
        String nightWeather = weather.getNightWeather();
        String nightTemp = weather.getNightTemp();
        String dayTemp = weather.getDayTemp();
        String windLevel = weather.getWindLevel();
        return String.format("%s今天白天%s，晚上%s，%s-%s度，%s。", cityName,
                dayWeather, nightWeather, nightTemp, dayTemp, windLevel);
    }

    // 处理sentence内容
    private static String dealWithSentence(Sentence sentence) {
        String content = sentence.getContent();
        String note = sentence.getNote();
//        String translation = sentence.getTranslation();
        String fenxiangImg = sentence.getFenxiangImg();

        StringBuilder sb = new StringBuilder();
        sb.append(content)
                .append("\n")
                .append(note)
                .append("\n")
                .append(fenxiangImg);
        return sb.toString();
    }


    public static String TuLing(String fromUserName, String toUserName, BaseResponse baseResponse) {
        MessageText messageText = new MessageText();
        messageText.setToUserName(fromUserName);
        messageText.setFromUserName(toUserName);
        messageText.setCreateTime(new Date().getTime());

        List<Results> results = baseResponse.getResults();
        List<Values> collect = results.stream().map(Results::getValues).collect(Collectors.toList());
//        List<String> texts = collect.stream().map(Values::getText).collect(Collectors.toList());
        String result = collect.stream().map(Values::getText).collect(Collectors.joining("\n"));
        messageText.setContent(result);
        messageText.setMsgType(MESSAGE_TEXT);
        return messageToXml(messageText);
    }


    // 处理QingYunKe内容
    public static String QingYunKe(String fromUserName, String toUserName, QingYunKeResponse response) {
        MessageText messageText = new MessageText();
        messageText.setToUserName(fromUserName);
        messageText.setFromUserName(toUserName);
        messageText.setCreateTime(new Date().getTime());
        messageText.setContent(response.getContent().replace("{br}", "\n"));
        messageText.setMsgType(MESSAGE_TEXT);
        return messageToXml(messageText);
    }

    // 处理QingYunKe内容
    public static String commonMessage(String fromUserName, String toUserName, String message) {
        MessageText messageText = new MessageText();
        messageText.setToUserName(fromUserName);
        messageText.setFromUserName(toUserName);
        messageText.setCreateTime(new Date().getTime());
        messageText.setContent(message);
        messageText.setMsgType(MESSAGE_TEXT);
        return messageToXml(messageText);
    }


    // 历史文章列表 图文消息
    public static String historyArticle(String fromUserName, String toUserName) {
        MessageNews messageNews = new MessageNews();
        List<Item> itemList = new ArrayList<>();
        Item item = new Item();
        item.setTitle("历史文章");
        item.setPicUrl(host + "le.jpg");
        item.setDescription("点我获取历史文章列表");
//        item.setUrl("https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzAxNTI2ODk0NA==#wechat_redirect");
        // 由于微信不允许连接到文章列表， 所以这里做一次重定向 机智如我
        item.setUrl(host + "redirect");
        itemList.add(item);

        messageNews.setToUserName(fromUserName);
        messageNews.setFromUserName(toUserName);
        messageNews.setCreateTime(new Date().getTime());
        messageNews.setMsgType(MESSAGE_NEWS);
        messageNews.setArticles(itemList);
        messageNews.setArticleCount(itemList.size());
        return newsMessageToXml(messageNews).replace("&amp;", "&");
    }



    /**
     * 图片消息转为xml
     * @param imageMessage 图片消息
     */
    public static String imageMessageToXml(ImageMessage imageMessage){
        XStream xstream = new XStream();
        xstream.alias("xml", imageMessage.getClass());
        return xstream.toXML(imageMessage);
    }


    /**
     * 组装图片消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initImageMessage(String toUserName,String fromUserName, String mediaId){
        String message = null;
        Image image = new Image();
        image.setMediaId(mediaId);
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);
        message = imageMessageToXml(imageMessage);
        System.out.println(JSON.toJSONString(message));
        return message;
    }




}
