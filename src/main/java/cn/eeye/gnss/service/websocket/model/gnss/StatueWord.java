package cn.eeye.gnss.service.websocket.model.gnss;

/**
 * gnss状态字
 * Created by lishilong on 2017/7/12.
 */
public class StatueWord {
    //key
    int id;

    //value
    long bits;

    public StatueWord(int id, long bits) {
        this.id = id;
        this.bits = bits;
    }

    public int getId() {
        return id;
    }

    public long getBits() {
        return bits;
    }
}
