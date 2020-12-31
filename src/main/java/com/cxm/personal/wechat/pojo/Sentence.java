package com.cxm.personal.wechat.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author cxm
 * @description
 * @date 2019-10-18 14:59
 **/
@Data
public class Sentence implements Serializable {

        private Long id;
        private String content;
        private String note;
        private String translation;
        private String date;
        private String fenxiangImg;
        private String mediaId;

}
