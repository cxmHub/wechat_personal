package com.cxm.personal.wechat.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author cxm
 * @description
 * @date 2019-10-22 11:05
 **/
public class CheckWeChatUtil {

    private static String token = "chenxinmaochenxinmaochenxinmao";

    public static boolean checkWeChat(String signature, String timestamp, String nonce) {
        // 排序
        String[] arr = {token, timestamp, nonce};
        Arrays.sort(arr);
        // 拼接字符串
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        //加密
        String sha1 = getSha1(sb.toString());
        return signature.equals(sha1);
    }


    /**
     * sha1加密
     * @param
     * @return
     */
    public static String getSha1(String str){
        if(str == null || str.length()==0){
            return null;
        }
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for(int i=0;i<j;i++){
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

}
