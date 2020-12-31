package com.cxm.personal.wechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cxm.personal.wechat.utils.MessageUtil;
import com.cxm.personal.wechat.utils.WeixinUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author cxm
 */
@Service
public class WeChatServiceImpl {
    @Value("${APPID}")
    private String APPID = "wxdb0988e54fe9db00";
    @Value("${APPSECRET}")
    private String APPSECRET = "70f758dc8f4a74ea129bff98d2fec1ab";
    @Value("${access_token_url}")
    private String ACCESS_TOKEN_URL;
    @Value("${MEDIA_URL}")
    private String media_url;
    @Value("${MEDIA_URL_LIST}")
    private String media_url_list;


    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    public String connectHttpsByPost(String imgUrl) throws Exception {
        String accessToken = WeixinUtil.getAccessToken().getToken();
        String path = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=" + accessToken + "&type=" + MessageUtil.MESSAGE_IMAGE;
        URL url = new URL(path);
        HttpURLConnection con = (HttpURLConnection) (url.openConnection());
        String result = "";
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false); // post方式不能使用缓存
        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type",
                "multipart/form-data; boundary="
                        + BOUNDARY);


        byte[] b = downUrlImag(imgUrl);
        // 请求正文信息
        // 第一部分：
        String sb = "--" + // 必须多两道线
                BOUNDARY +
                "\r\n" +
                "Content-Disposition: form-data;name=\"media\";filelength=\"" + b.length + "\";filename=\"" + dateFormat.format(new Date()) + ".png" + "\"\r\n" +
                "Content-Type:application/octet-stream\r\n\r\n";
        byte[] head = sb.getBytes(StandardCharsets.UTF_8);
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 表体
        out.write(b);

        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(StandardCharsets.UTF_8);// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuilder buffer = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            // 定义BufferedReader输入流来读取URL的响应
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            result = buffer.toString();
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw new IOException("数据读取异常");
        }
        return result;
    }


    public byte[] downUrlImag(String imagUrl) throws Exception {
        //new一个URL对象
        URL url = new URL(imagUrl);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        return readInputStream(inStream);
    }

    private byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }


    public String getMedia(String mediaId) throws Exception {
        String accessToken = WeixinUtil.getAccessToken().getToken();
        String format_url = String.format(media_url, accessToken);
        String param = "{\"media_id\":\"%s\"}";
        String format = String.format(param, mediaId);
        JSONObject jsonObject = WeixinUtil.doPostStr(format_url, format);
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }


    public void getMediaList() throws Exception{
        String param = "{ \"type\":\"image\", \"offset\":0, \"count\":20 }";

        String accessToken = WeixinUtil.getAccessToken().getToken();
        String format_url = String.format(media_url_list, accessToken);
//        String param = "{\"media_id\":\"%s\"}";
//        String format = String.format(param, mediaId);
        JSONObject jsonObject = WeixinUtil.doPostStr(format_url, param);
        System.out.println(jsonObject.toString());

    }


}
