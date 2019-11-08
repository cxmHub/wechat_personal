package com.cxm.personal.wechat.rpc.req;

import lombok.Data;

/**
 * @author cxm
 * @description
 * @date 2019-11-07 10:19
 **/
@Data
public class Perception {

    private InputText inputText;
    private InputImage inputImage;
    private InputMedia inputMedia;
    private Location selfInfo;

}
