package cn.eeye.gnss.service.websocket.model;

import cn.eeye.gnss.service.websocket.utils.DateTimeFormatUtil;

/**
 * @Description：车辆轨迹
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public class VehTrack {
    //终端id
    private String terminalId;

    //gps时间(yyyy-MM-dd HH:mm:ss)
    private String gpsTime;

    //acc状态
    private boolean acc;

    //导航状态
    private boolean nav;

    //纬度
    private float lat;

    //经度
    private float lng;

    //速度(km/h)
    private float speed;

    //里程(km)
    private int mileage;

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public boolean isAcc() {
        return acc;
    }

    public void setAcc(boolean acc) {
        this.acc = acc;
    }

    public boolean isNav() {
        return nav;
    }

    public void setNav(boolean nav) {
        this.nav = nav;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void initVehTrack(String terminalId) {
        this.terminalId = terminalId;
        this.acc = true;
        this.nav = true;
        this.gpsTime = DateTimeFormatUtil.now();
        this.lat = 22.123456f;
        this.lng = 116.123456f;
        this.mileage = 999;
        this.speed = 85.6f;
    }

}
