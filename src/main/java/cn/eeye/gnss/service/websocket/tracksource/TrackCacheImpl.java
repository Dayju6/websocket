package cn.eeye.gnss.service.websocket.tracksource;


import cn.eeye.gnss.service.websocket.model.gnss.TrackData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * BlockQueue实现消息共享接口
 * Created by lishilong on 2017/7/11.
 */
public class TrackCacheImpl implements ITrackCache {

    private final Logger LOGGER = LoggerFactory.getLogger(TrackCacheImpl.class);

    private final int QUEUE_SIZE = 16 * 1024;

    private final BlockingQueue<TrackData> queue = new LinkedBlockingQueue<>(QUEUE_SIZE);



    public BlockingQueue<TrackData> getQueue() {
        return queue;
    }

    @Override
    public void addMsg(TrackData track) {
        try {
            queue.put(track);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.info("Share BlockingQueue is full");
        }
    }

    @Override
    public boolean addMsgs(List<TrackData> tracks) {
        return queue.addAll(tracks);
    }

    @Override
    public int getMsgCount() {
        return queue.size();
    }

    @Override
    public void getMsgs(List<TrackData> list, int batchNum) {
        queue.drainTo(list, batchNum);
    }
}
