package top.juanshen.wx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.juanshen.wx.dto.SubscribeMessageDto;
import top.juanshen.wx.dto.WechatSessionDto;
import top.juanshen.wx.service.MessageService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/wx")
public class MessageController {

    @Autowired
    private MessageService messageService;


    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String code) {
        WechatSessionDto sessionDto = messageService.login(code);
        return ResponseEntity.ok(sessionDto);
    }

    @PostMapping("/sendSubscribeMsg")
    public ResponseEntity<?> sendSubscribeMsg(@RequestBody SubscribeMessageDto message) {
        messageService.sendSubscribeMsg(message);
        return ResponseEntity.ok().build();
    }


}


