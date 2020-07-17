package cn.eeye.gnss.service.websocket.model;

/**
 * @Description：websocke客户端请求消息实体
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public class WebSocketClientReqEntity {
    //请求命令类型
    private String cmdType;

    //请求命令的消息实体
    private String msg;

    public String getCmdType() {
        return cmdType;
    }

    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
