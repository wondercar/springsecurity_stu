package mail;

import com.sun.mail.util.MailSSLSocketFactory;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

/**
 * 邮件发送类
 *
 * @author: wondercar
 * @since: 2023-03-28
 */
@SpringBootTest
public class MailApplicationTest {
    public static String emailHost = "smtp.qq.com";       //发送邮件的主机
    public static String transportType = "smtp";           //邮件发送的协议
    public static String fromUser = "联想";           //发件人名称
    public static String fromEmail = "507160360@qq.com";  //发件人邮箱
    public static String authCode = "ozererdxyixwbggf";    //发件人邮箱授权码
    public static String toEmail = "804054864@qq.com";   //收件人邮箱
    public static String subject = "【联想】小新笔记本打卡活动奖励领取";           //主题信息
    public static String htmlContent = "\n亲爱的联想小新用户您好：\n\n" +
            "感谢您参加本次的小新笔记本打卡活动\n" +
            "您本次打卡审核的成果为：成功\n" +
            "打次成功次数为：1次\n\n" +
            "请查收下方京东百元E卡\n" +
            "卡号：JDV4380007302008094\n" +
            "密码：BCA2-CC91-806E-DD9D\n\n" +
            "绑定方式请参考附件\n" +
            "登录京东——我的京东——礼品卡\n" +
            "感谢！";

    /**
     * @Method: createMailWithResource
     * @Description: 创建一封包含附件的邮件
     */
    public static MimeMessage createMailWithResource(Session session)
            throws Exception {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(fromEmail));  
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        // 邮件的标题
        message.setSubject(subject);
        // 创建消息部分
        BodyPart messageBodyPart = new MimeBodyPart();

        // 消息
        messageBodyPart.setText(htmlContent);

        // 创建多重消息
        Multipart multipart = new MimeMultipart();

        // 设置文本消息部分
        multipart.addBodyPart(messageBodyPart);

        // 附件部分
        messageBodyPart = new MimeBodyPart();
        String filename = "京东E卡领取步骤.png";
        DataSource source = new FileDataSource(MailApplicationTest.class.getResource("/static/b.png").getPath());
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);

        // 发送完整消息
        message.setContent(multipart);
        return message;
    }

    @Test
    public void sendMessage() throws Exception {
        //创建Properties对象
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug","true");
        // 设置邮件服务器主机名为QQ邮箱的服务器主机名
        prop.setProperty("mail.host","smtp.qq.com");
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth","true");
        // 设置发送邮件协议名称为SMTP(Simple Mail Transfer Protocol)
        prop.setProperty("mail.transport.protocol","smtp");

        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable","true");
        prop.put("mail.smtp.ssl.socketFactory",sf);

        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
        ts.connect("smtp.qq.com","507160360","lyrclozfyscibjbh");
        // 创建邮件
        Message message1 = createMailWithResource(session);

        // 发送邮件
        ts.sendMessage(message1,message1.getAllRecipients());
        ts.close();
    }
}
