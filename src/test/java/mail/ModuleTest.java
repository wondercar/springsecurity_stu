package mail;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 密码编码测试类
 *
 * @author: wondercar
 * @since: 2023-03-20
 */
@SpringBootTest
public class ModuleTest {
    /**
     * BCryptPasswordEncoder密码编码
     */
    @Test
    public void encoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodeStr = encoder.encode("chen123");
        System.out.println("编码后的密码：" + encodeStr);
        boolean result = encoder.matches("chen123", encodeStr);
        System.out.println("对比结果：" + result);
    }

    /**
     * 时间计算方法
     */
    public static int getMinuteDiff(Date startDate, Date endDate) {
        long dayM = 1000 * 24 * 60 * 60;// 一天总秒数
        long hourM = 1000 * 60 * 60;// 一小时总秒数
        long minuteM = 1000 * 60;// 一分钟总秒数
        long diff = startDate.getTime() - endDate.getTime();
        if (diff > 0) {
            throw new RuntimeException("起始时间必须在结束时间之前");
        }
        long minute =
                // 总时间与小时总秒数取余 再与分钟总秒数整除得到剩余分钟数
                diff % hourM / minuteM
                // 总时间与分钟总秒数整除得到已舍弃分钟数
                // 剩余分钟数 + 舍弃分钟数 = 分钟时间差
                + (diff / minuteM);
        return Integer.parseInt(String.valueOf(minute));
    }
    @Test
    public static void newMethod(){
        System.out.println("这是一个合并后的新方法");
    }

    public static void main(String[] args) throws ParseException {
//        encoder();
        /**
         * yyyy:年
         * MM:月
         * dd:日
         * HH:24小时制 hh:12小时制
         * mm:分
         * ss:秒
         */
//        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date startDate = sd.parse("2023-03-22 00:00:00");
//        Date endDate = sd.parse("2023-03-24 00:00:00");
//        int diff = getMinuteDiff(startDate, endDate);
//        System.out.println(diff);
    }
}
