package cn.eeye.gnss.service.websocket.model.gnss;

/**
 * 温湿度数据
 * Created by lishilong on 2017/6/15.
 */
public class TemperatureData {

    //传感器ID
    private int sid;

    //是否出现温湿度传感器故障，默认：false
    private Boolean fault;

    //是否出现高温报警（设备产生），默认：false
    private Boolean dHAlm;

    //是否出现低温报警（设备产生），默认：false
    private Boolean dLAlm;

    //是否出现高温报警（平台产生），默认：false
    private Boolean pHAlm;

    //是否出现低温报警（平台产生），默认：false
    private Boolean pLAlm;

    //温度值：摄氏度
    private Float t;

    //湿度值：%
    private Float h;


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Boolean getFault() {
        return fault;
    }

    public void setFault(Boolean fault) {
        this.fault = fault;
    }

    public Boolean getdHAlm() {
        return dHAlm;
    }

    public void setdHAlm(Boolean dHAlm) {
        this.dHAlm = dHAlm;
    }

    public Boolean getdLAlm() {
        return dLAlm;
    }

    public void setdLAlm(Boolean dLAlm) {
        this.dLAlm = dLAlm;
    }

    public Boolean getpHAlm() {
        return pHAlm;
    }

    public void setpHAlm(Boolean pHAlm) {
        this.pHAlm = pHAlm;
    }

    public Boolean getpLAlm() {
        return pLAlm;
    }

    public void setpLAlm(Boolean pLAlm) {
        this.pLAlm = pLAlm;
    }

    public Float getT() {
        return t;
    }

    public void setT(Float t) {
        this.t = t;
    }

    public Float getH() {
        return h;
    }

    public void setH(Float h) {

        this.h = h;
    }
}
