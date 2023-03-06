package top.juanshen.wx.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubscribeMessageDto implements Serializable {

    private String toUser;

    private String template_id;

    private String miniprogram_state;

    private String lang;

    private String data;
}
