package top.juanshen.wx.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.juanshen.wx.dto.SubscribeMessageDto;
import top.juanshen.wx.dto.WechatSessionDto;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WechatService {

    @Autowired
    private AccessTokenService accessTokenService;

    private static final String ACCESS_TOKEN = "access_token";


    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.secret}")
    private String appSecret;


    /**
     * 发送订阅消息
     *
     * @param openId      用户的OpenID
     * @param templateId  订阅消息的模板ID
     * @param messageData 订阅消息的数据
     * @return 发送结果，true表示发送成功，false表示发送失败
     */
    public boolean sendMessage(String openId, String templateId, Map<String, String> messageData) {
        // 获取access_token
        String accessToken = accessTokenService.getAccessToken(ACCESS_TOKEN);
        if (accessToken == null) {
            accessToken = getAccessToken();
            accessTokenService.saveAccessToken(ACCESS_TOKEN,accessToken,7200);
        }

        // 发送订阅消息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", openId);
        jsonObject.put("template_id", templateId);
        jsonObject.put("data", messageData);
        jsonObject.put("miniprogram_state", "trial");
        jsonObject.put("lang", "zh_CN");

        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
        String result = HttpUtil.post(url, JSONUtil.toJsonStr(jsonObject));
        JSONObject resultObject = JSONUtil.parseObj(result);
        int errCode = resultObject.getInt("errcode");
        return errCode == 0;
    }

    /**
     * 获取access_token
     *
     * @return access_token
     */
    private String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        url = url.replace("APPID", appId)
                .replace("APPSECRET", appSecret);
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String accessToken = jsonObject.getStr("access_token");
        return accessToken;
    }



    public WechatSessionDto login(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
        log.info("login url:{}",url);
        String response = HttpUtil.get(url);
        log.info("login response :{}",response );
        WechatSessionDto sessionDto = JSONUtil.toBean(response, WechatSessionDto.class);
        return sessionDto;
    }

    public void sendSubscribeMsg(SubscribeMessageDto message) {
        String accessToken = getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
        HttpUtil.post(url,JSONUtil.toJsonStr(message));
    }

}
