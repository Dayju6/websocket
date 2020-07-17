package cn.eeye.gnss.service.websocket.tracksource;

import cn.eeye.gnss.service.websocket.model.gnss.TrackData;

import java.util.List;


public interface ITrackCache {
    void addMsg(TrackData data);

    boolean addMsgs(List<TrackData> data);

    void getMsgs(List<TrackData> data, int batchNum);

    int  getMsgCount();
}
