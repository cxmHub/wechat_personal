package com.cxm.personal.wechat.utils;

import com.cxm.personal.wechat.pojo.MessageText;
import com.cxm.personal.wechat.pojo.Sentence;
import com.cxm.personal.wechat.pojo.Weather;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
     * message转 xml
     *
     * @return
     */
    public static String messageToXml(MessageText messageText) {

        XStream xstream = new XStream();
        xstream.alias("xml", messageText.getClass());
        return xstream.toXML(messageText);

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
        messageText.setContent("欢迎关注！\n"+"1、回复1，每日一句英语学习\n" +
                "2、回复天气可以查询天气，例如：北京天气\n" +
                "3、输入其他内容，可以在线与价值一亿的智能聊天机器人聊天");
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

    /**
     * 错误消息
     * @param fromUserName
     * @param toUserName
     * @param result
     * @return
     */
    public static String errorResult(String fromUserName, String toUserName, String result){
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
        String translation = sentence.getTranslation();

        StringBuffer sb = new StringBuffer();
        sb.append(content)
                .append("\n")
                .append(note)
                .append("\n")
                .append(translation);
        return sb.toString();
    }
}
