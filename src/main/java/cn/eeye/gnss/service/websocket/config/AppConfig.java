package cn.eeye.gnss.service.websocket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @Description：websocket配置信息
 * @author: amos-lsl
 * @date: 2018/6/5
 */

@Component
@ConfigurationProperties(prefix="app")
public class AppConfig {
    private boolean outputDebug;

    private int listenPort;

    public boolean isOutputDebug() {
        return outputDebug;
    }

    public void setOutputDebug(boolean outputDebug) {
        this.outputDebug = outputDebug;
    }

    public int getListenPort() {
        return listenPort;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }
}
