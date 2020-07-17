package cn.eeye.gnss.service.websocket.tracksource;


import cn.eeye.gnss.service.websocket.config.KkConfig;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Properties;


/**
 * Created by lishilong on 2017/7/20.
 */
public class KafkaConsumerIsch extends Thread implements IkafkaConsumer{

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerIsch.class);
    private final KkConfig kafkaConfig;
    private final IMsgProcessor msgProcessor;

    private final KafkaConsumer<String, Bytes> consumer;

    private boolean terminate;

    public KafkaConsumerIsch(KkConfig kafkaConfig, IMsgProcessor msgProcessor) {
        this.msgProcessor = msgProcessor;
        this.kafkaConfig = kafkaConfig;
        terminate = true;
        Properties props = new Properties();
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getServerInfo());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getGroupId());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 512);

        LOGGER.info(kafkaConfig.toString());

        if ( kafkaConfig.getAutoCommit().equals("true")) {
            props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, kafkaConfig.getAutoCommit());
            props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, kafkaConfig.getAutoCommitIntervalMs());
        }
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KafkaConsts.STRING_DESERIALIZER);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaConsts.BYTES_DESERIALIZER);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 512);

        consumer = new KafkaConsumer<>(props);
    }


    @Override
    public void run() {
        TopicPartition tp = new TopicPartition(kafkaConfig.getTopic(), 0);
        List<TopicPartition> tps = Collections.singletonList(tp);
        consumer.assign(tps);
        //consumer.seekToEnd(tps);
        consumer.seekToBeginning(tps);

//        Map<TopicPartition, Long> timestampsToSearch = new HashMap<>();
//        timestampsToSearch.put(tp, System.currentTimeMillis()-5*60*1000);
//        Map<TopicPartition, OffsetAndTimestamp> offsetInfo = consumer.offsetsForTimes(timestampsToSearch);
//        long offset = offsetInfo.get(tp).offset();
//        consumer.seek(tp, offset);

        while (!terminate) {
            ConsumerRecords<String, Bytes> records = consumer.poll(1000);
            if (records.count() == 0) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (ConsumerRecord<String, Bytes> record : records) {
                msgProcessor.msgParser(record);
            }
        }
    }

    @Override
    public void startup() {
        terminate = false;
        start();
    }

    @Override
    public void terminated() {
        terminate = true;
    }
}
