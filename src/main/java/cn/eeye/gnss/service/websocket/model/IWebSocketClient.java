package cn.eeye.gnss.service.websocket.model;

import io.netty.channel.Channel;
import java.util.List;

/**
 * @Description：客户端信息接口
 * @author: amos-lsl
 * @date: 2018/6/5
 */

public interface IWebSocketClient {
    //获取channel
    Channel getConnectChannel();
    //订阅单个车辆轨迹信息
    void subscribeVehTrack(String vehId);
    //订阅多个车辆轨迹信息
    void subscribeVehsTrack(List<String> vehIds);
    //取消订阅单个车辆信息
    void cancleSubscribeVehTrack(String vehId);
    //取消订阅多个车辆信息
    void cancleSubscribeVehsTrack(List<String> vehIds);
}
