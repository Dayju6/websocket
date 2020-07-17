package cn.eeye.gnss.service.websocket.model;

/**
 * @Description：简单消息包装
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public class Messages {
    //命令类型
    private int cmdType;

    //消息内容
    private String msg;

    public Messages(int cmdType, String msg) {
        this.cmdType = cmdType;
        this.msg = msg;
    }
}
