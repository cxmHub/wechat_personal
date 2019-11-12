package com.cxm.personal.wechat.pojo;

import lombok.Data;

/**
 * @author cxm
 * @description 文本消息
 * @date 2019-10-22 15:07
 **/
@Data
public class MessageText extends BaseMessage {

    private String Content;//文本消息内容

    private String MsgId;//消息id，64位整型

}
