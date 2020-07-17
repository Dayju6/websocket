package cn.eeye.gnss.service.websocket.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix="kafka")
public class KkConfig {
    //服务器信息
    private String serverInfo;

    //主题()
    private String topic;

    //组id
    private String groupId;

    //是否自动提交
    private String autoCommit;

    //自动提交时间间隔（ms）
    private int autoCommitIntervalMs;

    public String getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(String autoCommit) {
        this.autoCommit = autoCommit;
    }

    public int getAutoCommitIntervalMs() {
        return autoCommitIntervalMs;
    }

    public void setAutoCommitIntervalMs(int autoCommitIntervalMs) {
        this.autoCommitIntervalMs = autoCommitIntervalMs;
    }

    @Override
    public String toString() {
        return "KafkaConfig{" +
                "serverInfo='" + serverInfo + '\'' +
                ", topic='" + topic + '\'' +
                ", groupId='" + groupId + '\'' +
                ", autoCommit='" + autoCommit + '\'' +
                ", autoCommitIntervalMs='" + autoCommitIntervalMs + '\'' +
                '}';
    }
}
