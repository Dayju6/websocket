package cn.eeye.gnss.service.websocket.tracksource;

/**
 * Created by lishilong on 2017/7/20.
 */
public interface IkafkaConsumer {

    //启动kafka消费
    void startup();

    //停止kafka消费
    void terminated();
}
