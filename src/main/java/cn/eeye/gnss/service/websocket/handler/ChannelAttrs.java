package cn.eeye.gnss.service.websocket.handler;

import io.netty.util.AttributeKey;

/**
 * @Description：全局AttributeKey
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public class ChannelAttrs {

    public static final AttributeKey<Session> SESSION = AttributeKey.valueOf("sessiono");

}
