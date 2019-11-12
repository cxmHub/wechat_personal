package com.cxm.personal.wechat.pojo;

import lombok.Data;

/**
 * @author cxm
 * @description 图文消息体
 * @date 2019-11-12 11:31
 **/
@Data
public class Item {

    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;
}
