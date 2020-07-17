package cn.eeye.gnss.service.websocket.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description：websocket客户端连接管理
 * @author: amos-lsl
 * @date: 2018/6/5
 */

public class WebSocketClientsManager implements IClientsManager{

    //客户端连接管理
    private Map<String, IWebSocketClient> clientInfoMap;

    private WebSocketClientsManager() {
        clientInfoMap = new HashMap<>();
    }

    private static class Holder{
        private static final WebSocketClientsManager INSTANCE = new WebSocketClientsManager();
    }

    public static IClientsManager getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public IWebSocketClient getClientInfo(String clikey) {
        return clientInfoMap.get(clikey);
    }

    @Override
    public void addClientInfo(String cliKey, IWebSocketClient clientInfo) {
        if (cliKey != null && clientInfo != null) {
            clientInfoMap.put(cliKey, clientInfo);
        }
    }

    @Override
    public void removeClientInfo(String cliKey) {
        clientInfoMap.remove(cliKey);
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> getCliKeys() {
        return clientInfoMap.keySet();
    }
}
