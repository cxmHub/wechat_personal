package com.cxm.personal.wechat.pojo;

import lombok.Data;

import java.util.List;

/**
 * @author cxm
 * @description 图文消息
 * @date 2019-11-12 11:18
 **/
@Data
public class MessageNews extends BaseMessage {

    private Integer ArticleCount;
    private List<Item> Articles;

}
