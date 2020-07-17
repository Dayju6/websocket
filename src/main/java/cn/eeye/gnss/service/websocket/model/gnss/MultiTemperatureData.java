package cn.eeye.gnss.service.websocket.model.gnss;

import java.util.List;

/**
 * 多路温度数据
 * Created by lishilong on 2017/7/12.
 */
public class MultiTemperatureData {
    private Boolean tempCapFault;
    private List<TemperatureData> temps;

    public Boolean getTempCapFault() {
        return tempCapFault;
    }

    public void setTempCapFault(Boolean tempCapFault) {
        this.tempCapFault = tempCapFault;
    }

    public List<TemperatureData> getTemps() {
        return temps;
    }

    public void setTemps(List<TemperatureData> temps) {
        this.temps = temps;
    }
}
