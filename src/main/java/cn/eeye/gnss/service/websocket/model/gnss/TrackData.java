package cn.eeye.gnss.service.websocket.model.gnss;

import java.util.List;

/**
 * Created by lishilong on 2017/7/10.
 */
public class TrackData {
    //轨迹D
    private String tid;

    //终端ID
    private String termId;

    //车辆ID
    private String vehId;

    //车牌号
    private String plate;

    //车牌颜色
    private String plateColorNum;

    //接收时间
    private long recvTime;

    //gnss时间
    private long termTime;

    //经度
    private Double lng;

    //纬度
    private Double lat;

    //速度（km/h）
    private Integer spd;

    //原车速度（km/h）
    private Integer vehSpd;

    //海拔（m）
    private Float alt;

    //方向，正北为0,0-359°
    private Integer head;

    //定位星数
    private Integer satl;

    //电量
    private Integer battery;

    //里程（m）
    private Long mile;

    //acc状态
    private Boolean accOn;

    //导航状态
    private Boolean nav;

    //基站信息
    private BaseStationInfo baseStInfo;

    //状态字信息集合
    private List<StatueWord> stWords;

    //gnss额外信息
    private List<AddtInfo> addtInfos;

    //多路温度信息
    private MultiTemperatureData tempInfo;


    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getVehId() {
        return vehId;
    }

    public void setVehId(String vehId) {
        this.vehId = vehId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getPlateColorNum() {
        return plateColorNum;
    }

    public void setPlateColorNum(String plateColorNum) {
        this.plateColorNum = plateColorNum;
    }

    public long getRecvTime() {
        return recvTime;
    }

    public void setRecvTime(long recvTime) {
        this.recvTime = recvTime;
    }

    public long getTermTime() {
        return termTime;
    }

    public void setTermTime(long termTime) {
        this.termTime = termTime;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getSpd() {
        return spd;
    }

    public void setSpd(Integer spd) {
        this.spd = spd;
    }

    public Integer getVehSpd() {
        return vehSpd;
    }

    public void setVehSpd(Integer vehSpd) {
        this.vehSpd = vehSpd;
    }

    public Float getAlt() {
        return alt;
    }

    public void setAlt(Float alt) {
        this.alt = alt;
    }

    public Integer getHead() {
        return head;
    }

    public void setHead(Integer head) {
        this.head = head;
    }

    public Integer getSatl() {
        return satl;
    }

    public void setSatl(Integer satl) {
        this.satl = satl;
    }

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public Long getMile() {
        return mile;
    }

    public void setMile(Long mile) {
        this.mile = mile;
    }

    public Boolean getAccOn() {
        return accOn;
    }

    public void setAccOn(Boolean accOn) {
        this.accOn = accOn;
    }

    public Boolean getNav() {
        return nav;
    }

    public void setNav(Boolean nav) {
        this.nav = nav;
    }

    public BaseStationInfo getBaseStInfo() {
        return baseStInfo;
    }

    public void setBaseStInfo(BaseStationInfo baseStInfo) {
        this.baseStInfo = baseStInfo;
    }

    public List<StatueWord> getStWords() {
        return stWords;
    }

    public void setStWords(List<StatueWord> stWords) {
        this.stWords = stWords;
    }

    public List<AddtInfo> getAddtInfos() {
        return addtInfos;
    }

    public void setAddtInfos(List<AddtInfo> addtInfos) {
        this.addtInfos = addtInfos;
    }

    public MultiTemperatureData getTempInfo() {
        return tempInfo;
    }

    public void setTempInfo(MultiTemperatureData tempInfo) {
        this.tempInfo = tempInfo;
    }

    @Override
    public String toString() {
        return "TrackData{" +
                "tid='" + tid + '\'' +
                ", termId='" + termId + '\'' +
                ", vehId='" + vehId + '\'' +
                ", plate='" + plate + '\'' +
                ", plateColorNum='" + plateColorNum + '\'' +
                ", recvTime='" + recvTime + '\'' +
                ", termTime='" + termTime + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", spd=" + spd +
                ", vehSpd=" + vehSpd +
                ", alt=" + alt +
                ", head=" + head +
                ", satl=" + satl +
                ", battery=" + battery +
                ", mile=" + mile +
                ", accOn=" + accOn +
                ", nav=" + nav +
                ", baseStInfo=" + baseStInfo +
                ", stWords=" + stWords +
                ", addtInfos=" + addtInfos +
                ", tempInfo=" + tempInfo +
                '}';
    }
}
