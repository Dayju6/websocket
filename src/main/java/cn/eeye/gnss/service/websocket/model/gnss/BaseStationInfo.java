package cn.eeye.gnss.service.websocket.model.gnss;

/**
 * Created by lishilong on 2017/7/12.
 */
public class BaseStationInfo {
    //基站解释的误差范围(单位：米)
    Integer accy;

    //基站信息(单位:dBm)
    Integer dBm;

    //网络信号强度(单位：dBm)
    Integer rssi;

    public BaseStationInfo(Integer accy, Integer dBm, Integer rssi) {
        this.accy = accy;
        this.dBm = dBm;
        this.rssi = rssi;
    }
}
