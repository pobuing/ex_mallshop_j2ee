package cn.probuing.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailUtils {

    public static void sendMail(String email, String emailMsg)
            throws AddressException, MessagingException, IOException {
        // 1.创建一个程序与邮件服务器会话对象 Session
        ClassLoader classLoader = MailUtils.class.getClassLoader();
        InputStream asStream = classLoader.getResourceAsStream("mail.properties");
        final Properties propMail = new Properties();
        propMail.load(asStream);

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", propMail.getProperty("mail.transport.protocol"));
        props.setProperty("mail.host", propMail.getProperty("mail.host"));
        props.setProperty("mail.smtp.auth", "true");// 指定验证为true

        // 创建验证器
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(propMail.getProperty("mailaccount"), propMail.getProperty("mailpassword"));
            }
        };

        Session session = Session.getInstance(props, auth);

        // 2.创建一个Message，它相当于是邮件内容
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("wangxinproject@163.com")); // 设置发送者

        message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 设置发送方式与接收者

        message.setSubject("用户激活");
        // message.setText("这是一封激活邮件，请<a href='#'>点击</a>");

        message.setContent(emailMsg, "text/html;charset=utf-8");

        // 3.创建 Transport用于将邮件发送

        Transport.send(message);
    }
}
