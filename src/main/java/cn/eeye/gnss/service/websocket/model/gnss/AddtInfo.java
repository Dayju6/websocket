package cn.eeye.gnss.service.websocket.model.gnss;

/**
 * gnss额外信息
 * Created by lishilong on 2017/7/12.
 */
public class AddtInfo {
    //额外信息id
    String k;

    //额外信息valueֵ
    String v;

    public AddtInfo(String k, String v) {
        this.k = k;
        this.v = v;
    }
}
