package cn.eeye.gnss.service.websocket.service;

import cn.eeye.gnss.service.websocket.consts.MsgConsts;
import cn.eeye.gnss.service.websocket.model.*;
import cn.eeye.gnss.service.websocket.model.gnss.TrackData;
import cn.eeye.gnss.service.websocket.tracksource.ITrackCache;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * @Description：推送任务
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public class PushTask extends Thread implements IPushTask{

    private static final Logger LOGGER = LoggerFactory.getLogger(PushTask.class);
    private static final Gson GSON = new Gson();

    private static final int TRACK_QUEUE_SIZE = 1024*100;
    private static final int BATCHNUM = 1024;

    private IClientsManager clientsManager;

    private boolean terminate;

    private final ITrackCache trackCache;
    public PushTask(ITrackCache trackCache) {
        super("pushTask");
        this.trackCache = trackCache;

        clientsManager = WebSocketClientsManager.getInstance();
        terminate = true;
    }

    @Override
    public void run() {
        LOGGER.info("push Task thread start");
        List<TrackData> list = new ArrayList<>(BATCHNUM);

        while (!terminate) {
            list.clear();

            // TODO: 2018/6/5 test
            trackCache.getMsgs(list, BATCHNUM);

            if (!list.isEmpty()) {
                doSendTest(list);
            } else {
                try {
                    Thread.sleep(10*1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(3*1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void startUp() {
        terminate = false;
        start();
    }

    @Override
    public void terminate() {
        terminate = true;
    }


    void doSendTest(List<TrackData> tracks) {
        Set<String> cliKeys = clientsManager.getCliKeys();
        Iterator<String> it = cliKeys.iterator();
        String content = GSON.toJson(tracks);
        while (it.hasNext()) {
            Messages msg = new Messages(MsgConsts.WS_CMD_SUBSCRIBE_TRACKS, content);
            Channel chnnel = clientsManager.getClientInfo(it.next()).getConnectChannel();
            chnnel.writeAndFlush(new TextWebSocketFrame(GSON.toJson(msg)));
        }
    }
}
