package cn.eeye.gnss.service.websocket.handler;

/**
 * @Description：客户端会话session信息
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public class Session {
    //客户端地址
    private String addr;

    //客户端连接时间
    private long connectTime;

    //客户端最后一次发送请求的时间
    private long latestRequest;

    //token

    public Session(String addr) {
        this.addr = addr;
        this.connectTime = System.currentTimeMillis();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public long getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(long connectTime) {
        this.connectTime = connectTime;
    }

    public long getLatestRequest() {
        return latestRequest;
    }

    public void setLatestRequest(long latestRequest) {
        this.latestRequest = latestRequest;
    }
}
