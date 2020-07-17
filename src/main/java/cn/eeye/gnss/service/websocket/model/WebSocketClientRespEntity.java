package cn.eeye.gnss.service.websocket.model;

/**
 * @Description：webSocket客户端应答实体
 * @author: amos-lsl
 * @date: 2018/6/5
 */

public class WebSocketClientRespEntity {
    //应答命令类型
    private String cmdType;

    //错误码
    private int errorCode;

    //应答消息
    private String msg;

    public String getCmdType() {
        return cmdType;
    }

    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
