package com.cxm.personal.wechat.controller;

import com.cxm.personal.wechat.pojo.Sentence;
import com.cxm.personal.wechat.rpc.QingYunKeRPC;
import com.cxm.personal.wechat.rpc.RestTemplateConfig;
import com.cxm.personal.wechat.rpc.res.QingYunKeResponse;
import com.cxm.personal.wechat.service.LogService;
import com.cxm.personal.wechat.service.SentenceService;
import com.cxm.personal.wechat.service.impl.WeChatServiceImpl;
import com.cxm.personal.wechat.utils.CheckWeChatUtil;
import com.cxm.personal.wechat.utils.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatController.class);


    @Value("${history.article.url}")
    private String historyArticleUrl;

    @Resource
    private SentenceService sentenceService;
    @Resource
    private QingYunKeRPC qingYunKeRPC;
    @Resource
    private LogService logService;
    @Resource
    private RestTemplateConfig restTemplateConfig;
    @Resource
    private WeChatServiceImpl weChatService;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/index";
    }


    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String historyArticleRedirect() {
        return "redirect:" + historyArticleUrl;
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
        PrintWriter out;
        //将微信请求xml转为map格式，获取所需的参数
        Map<String, String> map = MessageUtil.xmlToMap(request);
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");
        String event = map.get("Event");
        String message = MessageUtil.commonMessage(toUserName,fromUserName,"说点什么吧");
        LOGGER.info("request:" + content + "\nuser:" + fromUserName);
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
                    if ("1".equals(content)) {
                        // 发送图片消息
                        Sentence sentenceByDate = sentenceService.getSentenceByDate(dateFormat.format(new Date()));
//                        message = MessageUtil.daySentence(fromUserName, toUserName, sentenceByDate);
                        String mediaId = sentenceByDate.getMediaId();
                        if (StringUtils.isBlank(mediaId)) {
                            message = MessageUtil.commonMessage(fromUserName,toUserName,"今天没有");
                        } else message = MessageUtil.initImageMessage(toUserName, fromUserName, mediaId);

                    }
//                    else if ("2".equals(content)) {
//                        Sentence sentenceByDate = sentenceService.getSentenceByDate(dateFormat.format(new Date()));
//                        String fenxiangImg = sentenceByDate.getFenxiangImg();
//                        weChatService.connectHttpsByPost(fenxiangImg);
//                    }
                    else if ("0".equals(content)
                            || "历史消息".equals(content)
                            || "历史文章".equals(content)) {
                        message = MessageUtil.historyArticle(fromUserName, toUserName);
                    } else {
                        QingYunKeResponse qingYunKeResponse = qingYunKeRPC.chatWithQingYunKeRoot(content);
                        message = MessageUtil.QingYunKe(fromUserName, toUserName, qingYunKeResponse);
                    }
                } else {
                    // 发送图片消息
                    Sentence sentenceByDate = sentenceService.getSentenceByDate(dateFormat.format(new Date()));
//                        message = MessageUtil.daySentence(fromUserName, toUserName, sentenceByDate);
                    String mediaId = sentenceByDate.getMediaId();
                    if (StringUtils.isBlank(mediaId)) {
                        message = "今天没有";
                    } else message = MessageUtil.initImageMessage(toUserName, fromUserName, mediaId);

                }
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            message = MessageUtil.errorResult(fromUserName, toUserName, "对不起，这个我真帮不了您！");
        }

        try {
            out = response.getWriter();
            out.write(message);
            LOGGER.info("response:" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
