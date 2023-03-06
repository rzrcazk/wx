package top.juanshen.wx.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class WechatSessionDto  implements Serializable {

    // openId, sessionKey, unionId
    private String openId;

    private String sessionKey;

    private String unionId;
}
