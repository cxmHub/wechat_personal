package com.cxm.personal.wechat.pojo;


import lombok.Data;

import java.util.Date;

/**
 * @author cxm
 * @description
 * @date 2019-11-11 11:24
 **/
@Data
public class LogMessage {
    private Long id;
    private String userName;
    private String message;
    private Date time;

}
