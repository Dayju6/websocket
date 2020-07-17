package cn.eeye.gnss.service.websocket.model;

import java.util.Set;

/**
 * @Description：客户端管理接口
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public interface IClientsManager {
    //获取客户端连接信息
    IWebSocketClient getClientInfo(String clikey);

    //添加客户端连接信息
    void addClientInfo(String cliKey, IWebSocketClient clientInfo);

    //删除客户端连接信息
    void removeClientInfo(String cliKey);

    //清理无效的客户端连接
    void clear();

    //获取cliKey set
    Set<String> getCliKeys();
}
