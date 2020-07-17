package cn.eeye.gnss.service.websocket.tracksource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.utils.Bytes;

/**
 * kafka消息解析接口
 * Created by lishilong on 2017/7/20.
 */
public interface IMsgProcessor {
    void msgParser(ConsumerRecord<String, Bytes> recordMsg);
    void msgParserString(ConsumerRecord<String, String> recordMsg);
}
