package cn.eeye.gnss.service.websocket.model;

import java.util.List;

/**
 * @Description：推送任务接口
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public interface IPushTask {
    //启动推送任务
    void startUp();

    //结束任务
    void terminate();

}
