package com.cxm.personal.wechat.controller;

import com.cxm.personal.wechat.pojo.Sentence;
import com.cxm.personal.wechat.rpc.QingYunKeRPC;
import com.cxm.personal.wechat.rpc.TuLingRPC;
import com.cxm.personal.wechat.rpc.res.QingYunKeResponse;
import com.cxm.personal.wechat.service.CityService;
import com.cxm.personal.wechat.service.LogService;
import com.cxm.personal.wechat.service.SentenceService;
import com.cxm.personal.wechat.service.WeatherService;
import com.cxm.personal.wechat.utils.CheckWeChatUtil;
import com.cxm.personal.wechat.utils.MessageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author cxm
 * @description
 * @date 2019-10-22 10:57
 **/
@Controller
public class WeChatController {

    @Resource
    private CityService cityService;
    @Resource
    private WeatherService weatherService;
    @Resource
    private SentenceService sentenceService;
    @Resource
    private TuLingRPC tuLingRPC;
    @Resource
    private QingYunKeRPC qingYunKeRPC;
    @Resource
    private LogService logService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    @ResponseBody
    public String connect(HttpServletRequest request, HttpServletResponse response) {

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        if (CheckWeChatUtil.checkWeChat(signature, timestamp, nonce)) {
            return echostr;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        //将微信请求xml转为map格式，获取所需的参数
        Map<String, String> map = MessageUtil.xmlToMap(request);
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");
        String event = map.get("Event");
        String message = "说点什么吧!";


        logService.insertLog(fromUserName, content);

        try {
            // 订阅
            if (event != null) {
                if (MessageUtil.MESSAGE_SUBSCRIBE.equals(event)) {
                    message = MessageUtil.subscribeMessage(fromUserName, toUserName);
                } else if (MessageUtil.MESSAGE_UNSUBSCRIBE.equals(event)) {
                    return;
                }
            } else {
                if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                    //用户发来其他消息处理

                    // 必须表明天气
//                if (content.contains("天气")) {
//                    // 爬城市信息
//                    cityService.insertCity(content);
//                    // 爬天气
//                    weatherService.insertWeather(content);
//                    Weather weather = weatherService.getWeatherByNameAndDate(content);
//                    if (weather != null){
//                        weather.setCityName(content.substring(0, content.indexOf("天气")));
//                        message = MessageUtil.weatherQuery(fromUserName, toUserName, weather);
//                    } else {
//                        message = MessageUtil.errorResult(fromUserName, toUserName, "请输入正确的地址！");
//                    }
//                }
                    if ("1".equals(content)) {
                        Sentence sentenceByDate = sentenceService.getSentenceByDate(dateFormat.format(new Date()));
                        message = MessageUtil.daySentence(fromUserName, toUserName, sentenceByDate);
                    } else {
//                    message = MessageUtil.chatWithMe(fromUserName, toUserName, content);
                        // :接入了图灵机器人，一亿聊天下架
//                        BaseResponse baseResponse = tuLingRPC.chatWithRoot(content);
//                        message = MessageUtil.TuLing(fromUserName, toUserName, baseResponse);
                        // 图灵api下架， 接入青云客api
                        QingYunKeResponse qingYunKeResponse = qingYunKeRPC.chatWithQingYunKeRoot(content);
                        message = MessageUtil.QingYunKe(fromUserName, toUserName, qingYunKeResponse);
                    }
                }
            }
        } catch (Exception e) {
            message = MessageUtil.errorResult(fromUserName, toUserName, "对不起，这个我真帮不了您！");
        }

        try {
            out = response.getWriter();
            out.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
