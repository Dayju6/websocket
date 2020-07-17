package cn.eeye.gnss.service.websocket.tracksource;

import cn.eeye.gnss.common.GnssDeviceCmdConsts;
import cn.eeye.gnss.common.dataexch.DeviceUpload;
import cn.eeye.gnss.common.dataexch.DeviceUploadMsgUtils;
import cn.eeye.gnss.common.dataexch.Track;
import cn.eeye.gnss.common.dataexch.Tracks;
import cn.eeye.gnss.common.model.track.GnssTrackUtils;
import cn.eeye.gnss.common.model.track.IGnssTrack;
import cn.eeye.gnss.service.websocket.model.gnss.TrackData;
import com.google.protobuf.BytesValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.utils.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * 处理从新企业平台的kafka数据，轨迹数据（TrackMsgData）的生产者
 * Created by lishilong on 2017/7/11.
 */
public class MsgProcessor implements IMsgProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MsgProcessor.class);

    private final ITrackCache trackCache;
    private HashSet<String> deviceIds;

    public MsgProcessor(ITrackCache trackCache) {
        super();

        this.trackCache = trackCache;
    }

    public void addTerminals(String termId) {
        deviceIds.add(termId);
    }

    public void addTerminals(List<String> termIds) {
        deviceIds.addAll(termIds);
    }

    @Override
    public void msgParser(ConsumerRecord<String, Bytes> recordMsg) {
        try {
            String termId = recordMsg.key();

            long offset = recordMsg.offset();

            byte[] loadMsg = recordMsg.value().get();

            DeviceUpload upload = DeviceUploadMsgUtils.bytesToDeviceUploadMsg(loadMsg);
            Int32Value msgId = upload.getPlatformMsgId();
            if (msgId == null || msgId.getValue() != GnssDeviceCmdConsts.LOCATION_REPORT)
                return;

            StringValue devId = upload.getDeviceId();
            if (devId == null)
                return;

            if (termId == null)
                termId = devId.getValue();
            if (termId == null)
                return;


            BytesValue bytes = upload.getPayload();
            if (bytes == null || bytes.getValue() == null)
                return;


            //判断版本
            Int32Value ver = upload.getVersion();
            int version = ver.getValue();

            List<IGnssTrack> gnssTrackList = new ArrayList<>();

            //取轨迹数据(判断版本)
            if (version == 4) {
                List<Track> trackList = Tracks.parseFrom(bytes.getValue()).getTrackList();
                for (Track track : trackList) {
                    IGnssTrack t = GnssTrackUtils.protoMsgToGnssTrack(track);
                    gnssTrackList.add(t);
                }
            } else {
                Track track = Track.parseFrom(bytes.getValue());
                IGnssTrack t = GnssTrackUtils.protoMsgToGnssTrack(track);
                gnssTrackList.add(t);
            }

            //解析轨迹
            List<TrackData> tracks = TrackDataParser.parseTrackMsgData(termId, gnssTrackList);
            trackCache.addMsgs(tracks);

        } catch (/*InvalidProtocolBufferException*/Exception e) {
            LOGGER.error("Error on parse device upload message.", e);
        }
    }

    @Override
    public void msgParserString(ConsumerRecord<String, String> recordMsg) {
        LOGGER.debug("key:{},  value:{}", recordMsg.key(), recordMsg.value());
    }
}
