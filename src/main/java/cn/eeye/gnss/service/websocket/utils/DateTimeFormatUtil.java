package cn.eeye.gnss.service.websocket.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description：日期时间格式化
 * @author: amos-lsl
 * @date: 2018/6/5
 */
public class DateTimeFormatUtil {

    private static final DateTimeFormatter eeyeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //获取当前时间
    public static String now() {
        LocalDateTime now =  LocalDateTime.now();
        return now.format(eeyeFormatter);
    }
}
