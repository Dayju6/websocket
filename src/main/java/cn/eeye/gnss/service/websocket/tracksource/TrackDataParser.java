package cn.eeye.gnss.service.websocket.tracksource;

import cn.eeye.gnss.common.GnssDeviceCmdConsts;
import cn.eeye.gnss.common.model.IGnssKeyValue;
import cn.eeye.gnss.common.model.IGnssKeyValues;
import cn.eeye.gnss.common.model.IGnssStateWord;
import cn.eeye.gnss.common.model.IGnssStateWords;
import cn.eeye.gnss.common.model.track.IGnssTrack;

import cn.eeye.gnss.service.websocket.common.TemperatureUploadData;
import cn.eeye.gnss.service.websocket.common.TemperatureUploadList;
import cn.eeye.gnss.service.websocket.model.gnss.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 轨迹消息
 * Created by lishilong on 2017/7/12.
 */
public class TrackDataParser {

    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static Logger LOGGER = LoggerFactory.getLogger(TrackDataParser.class);

    /**
     * 解析轨迹数据
     * @param termId
     * @param tracks
     * @return
     */
    public static List<TrackData> parseTrackMsgData(String termId, List<IGnssTrack> tracks) {

        //1.获取termId, vehId, plateNum, plateColorCode
//        String termId = trackMsgData.getTermId();
//        String vehId = trackMsgData.getVehId();
//        String plateNum = trackMsgData.getPlateNum();
//        String plateColorCode = trackMsgData.getPlateColorCode();

        //2.解析gnss数据
        List<TrackData> trackDataList = new LinkedList<>();
        for (IGnssTrack track : tracks) {
            long tid = track.getTrackId();
            long recvTime = track.getRecvTime();
            long termTime = track.getGnssTime();
            Double lng = track.getOriginalLon();
            Double lat = track.getOriginalLat();
            if (lng == null || lat == null) {
                continue;
            }
            Float spd = track.getSpeed();
            Float vehSpd = track.getVdrSpeed();
            Integer mile = track.getVdrMileage();
            int head = track.getHeading();

            //构造gnss数据结构
            TrackData trackData = new TrackData();

            //初始化gnss数据结构
            trackData.setTid(String.valueOf(tid));
            trackData.setTermId(termId);
            trackData.setRecvTime(recvTime);
            trackData.setTermTime(termTime);
            trackData.setLng(lng);
            trackData.setLat(lat);
            if (vehSpd != null)
                trackData.setVehSpd(vehSpd.intValue());
            if (spd != null)
                trackData.setSpd(spd.intValue());
            if (mile != null)
                trackData.setMile(mile.longValue());
            trackData.setHead(head);

            //解析状态字信息
            IGnssStateWords stateWords = track.getStateWords();
            if (stateWords != null)
                parseTrackMsgDataStWords(trackData, stateWords);
            
            //解析额外信息
            IGnssKeyValues addinfos = track.getAdditionalInfos();
            if (addinfos != null)
                parseTrackMsgDataStAddinfos(trackData, addinfos);

            trackDataList.add(trackData);

            //LOGGER.debug(trackData.toString());
        }


        return trackDataList;
    }

    /**
     * 解析轨迹消息状态字信息
     * @param trackData
     * @param stateWords
     */
    private static void parseTrackMsgDataStWords(TrackData trackData, IGnssStateWords stateWords) {

        List<IGnssStateWord> stWordList = stateWords.stateWords();
        if (stateWords.size() == 0)
            return;

        //获取状态字集合
        List<StatueWord> gnssStatueWordList = new ArrayList<>();
        for (IGnssStateWord word : stWordList) {
            //acc状态
            if (word.getWordId() == GnssDeviceCmdConsts.StateBits.ACC_ON.getWordId()){
                boolean accOn = word.bitTest(GnssDeviceCmdConsts.StateBits.ACC_ON.getBitIndex());
                if (accOn)
                    trackData.setAccOn(accOn);
            }
            //导航状态
            if (word.getWordId() == GnssDeviceCmdConsts.StateBits.NAVIGATING.getWordId()){
                boolean nav = word.bitTest(GnssDeviceCmdConsts.StateBits.NAVIGATING.getBitIndex());
                if (nav)
                    trackData.setNav(nav);
            }
            //温湿度采集器状态
            if(word.getWordId() == GnssDeviceCmdConsts.StateBits.TEMP_CATPURE_FAULT.getWordId()) {
                boolean tempCapFault = word.bitTest(GnssDeviceCmdConsts.StateBits.TEMP_CATPURE_FAULT.getBitIndex());
                if (tempCapFault) {
                    MultiTemperatureData multiTemperatureData = new MultiTemperatureData();
                    multiTemperatureData.setTempCapFault(tempCapFault);
                    trackData.setTempInfo(multiTemperatureData);
                }

            }

            //添加状态字信息
            gnssStatueWordList.add( new StatueWord(word.getWordId(), word.getStateBits()));
        }
        trackData.setStWords(gnssStatueWordList);
    }

    /**
     * 解析轨迹数据额外信息
     * @param trackData
     * @param addinfos
     */
    private static void parseTrackMsgDataStAddinfos(TrackData trackData, IGnssKeyValues addinfos) {

        List<IGnssKeyValue> addinfosList = addinfos.keyValues();
        if (addinfosList.size() == 0) {
            return;
        }

        //海拔
        Integer alt = addinfos.asInteger(GnssDeviceCmdConsts.LocationReportParams.ALTITUDE);
        //电量
        Integer battery = addinfos.asInteger(GnssDeviceCmdConsts.LocationReportParams.BATTERY);
        //定位星数
        Integer satl = addinfos.asInteger(GnssDeviceCmdConsts.LocationReportParams.SATELLITES);
        if (alt != null)
            trackData.setAlt(alt.floatValue());
        trackData.setBattery(battery);
        trackData.setSatl(satl);

        //基站解释的误差范围
        Integer accy = addinfos.asInteger(GnssDeviceCmdConsts.LocationReportParams.BASE_STATION_LOC_ACCURACY);
        //基站信息
        Integer dbm = addinfos.asInteger(GnssDeviceCmdConsts.LocationReportParams.BASE_STATION_INFO);
        //网络信号强度信
        Integer rssi = addinfos.asInteger(GnssDeviceCmdConsts.LocationReportParams.SIGNAL_INSENSITY);

        //设置基站信息
        trackData.setBaseStInfo( new BaseStationInfo(accy, dbm, rssi));

        //解析额外信息
        List<AddtInfo> addtInfoList = new ArrayList<>();
        //List<IGnssKeyValue> addinfosList = addinfos.keyValues();
        for (IGnssKeyValue addinfo : addinfosList) {

            int key = Integer.valueOf(addinfo.getKey(), 16);

            //过滤已解析过的额外信息
            if (key == GnssDeviceCmdConsts.LocationReportParams.ALTITUDE ||
                    key == GnssDeviceCmdConsts.LocationReportParams.BATTERY ||
                    key == GnssDeviceCmdConsts.LocationReportParams.SATELLITES ||
                    key == GnssDeviceCmdConsts.LocationReportParams.BASE_STATION_INFO ||
                    key == GnssDeviceCmdConsts.LocationReportParams.SIGNAL_INSENSITY ||
                    key == GnssDeviceCmdConsts.LocationReportParams.BASE_STATION_LOC_ACCURACY )
            {
                continue;
            }

            //多路温度数据
            if (key == GnssDeviceCmdConsts.LocationReportParams.MULTI_TEMP_DATA) {
                String multiTempurate = addinfo.getValue();
                if (multiTempurate != null && multiTempurate.length() > 0) {
                    parseTrackMsgDataTemptureInfo(trackData, multiTempurate);
                }
            }

            addtInfoList.add( new AddtInfo(addinfo.getKey(), addinfo.getValue()));
        }
        trackData.setAddtInfos(addtInfoList);
    }

    /**
     * 解析多路温度数据
     * @param trackData
     * @param multiTempurate
     */
    private static void parseTrackMsgDataTemptureInfo(TrackData trackData, String multiTempurate) {
        TemperatureUploadList temperatureUploadList = new TemperatureUploadList();
        if ( temperatureUploadList.parseFromString(multiTempurate) &&
                     temperatureUploadList.hasAnyNormalTempData() )
        {
            List<TemperatureData> temperatureDatalist = new ArrayList<TemperatureData>();
            List<TemperatureUploadData> list = temperatureUploadList.getList();
            if (list.size() == 0) {
                return;
            }

            for (TemperatureUploadData temp : list) {
                TemperatureData data = new TemperatureData();
                data.setSid(temp.getSensorId());
                if (temp.isSensorFault()) //false时默认不推送
                    data.setFault(temp.isSensorFault());
                if (temp.isDeviceHighTempAlarm())
                    data.setdHAlm(temp.isDeviceHighTempAlarm());
                if (temp.isDeviceLowTempAlarm())
                    data.setdLAlm(temp.isDeviceLowTempAlarm());
                if (temp.isPlatformHighTempAlarm())
                    data.setpHAlm(temp.isPlatformHighTempAlarm());
                if (temp.isPlatformLowTempAlarm())
                    data.setpLAlm(temp.isPlatformLowTempAlarm());
                if (temp.getTempValue() != null)
                    data.setT(temp.getTempValue());
                if (temp.getHumidity() != null)
                    data.setH(temp.getHumidity());
                temperatureDatalist.add(data);
            }

            if (trackData.getTempInfo() == null) {
                MultiTemperatureData multiTemperatureData = new MultiTemperatureData();
                trackData.setTempInfo(multiTemperatureData);
            }
            trackData.getTempInfo().setTemps(temperatureDatalist);
        }
    }

}
