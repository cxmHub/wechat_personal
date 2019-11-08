package com.cxm.personal.wechat.rpc.res;

import lombok.Data;

/**
 * @author cxm
 * @description
 * @date 2019-11-07 10:37
 **/
@Data
public class Results {
    // 文本(text);连接(url);音频(voice);视频(video);图片(image);图文(news)
    private String resultType;
    // 关联result type
    private Values values;
    private Integer groupType;
}
