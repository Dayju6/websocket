package cn.eeye.gnss.service.websocket.model;

import io.netty.channel.Channel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description：websocket客户端信息
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public class WebSocketClientInfo implements IWebSocketClient{

    //连接channel
    private Channel channel;

    //订阅车辆实时轨迹的车辆集合
    private Set<String> subscribeTrackVehids;

    //订阅报警信息类型

    //请求

    public WebSocketClientInfo(Channel channel) {
        this.channel = channel;
        subscribeTrackVehids = new HashSet<>();
    }

    @Override
    public Channel getConnectChannel() {
        return channel;
    }

    @Override
    public void subscribeVehTrack(String vehId) {
        subscribeTrackVehids.add(vehId);
    }

    @Override
    public void subscribeVehsTrack(List<String> vehIds) {
        subscribeTrackVehids.addAll(vehIds);
    }

    @Override
    public void cancleSubscribeVehTrack(String vehId) {
        subscribeTrackVehids.remove(vehId);
    }

    @Override
    public void cancleSubscribeVehsTrack(List<String> vehIds) {
        subscribeTrackVehids.removeAll(vehIds);
    }

    public Set<String> getSubscribeTrackVehids() {
        return subscribeTrackVehids;
    }
}
