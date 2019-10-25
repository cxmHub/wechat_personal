package com.cxm.personal.wechat.pojo;

import lombok.Data;

/**
 * @author cxm
 * @description
 * @date 2019-10-22 15:07
 **/
@Data
public class BaseMessage {
    protected String ToUserName;
    protected String FromUserName;
    protected long CreateTime;
    protected String MsgType;

}
