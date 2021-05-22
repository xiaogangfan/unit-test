//package org.xiaogang.util;
//
//
//import com.sun.mail.util.MailSSLSocketFactory;
//
//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.security.GeneralSecurityException;
//import java.util.Properties;
//
//public class SendEamil {
//    public static void main(String[] args) throws MessagingException, GeneralSecurityException {
//        //创建一个配置文件并保存
//        Properties properties = new Properties();
//
//        properties.setProperty("mail.host","smtp.qq.com");
//
//        properties.setProperty("mail.transport.protocol","smtp");
//
//        properties.setProperty("mail.smtp.auth","true");
//
//
//        //QQ存在一个特性设置SSL加密
//        MailSSLSocketFactory sf = new MailSSLSocketFactory();
//        sf.setTrustAllHosts(true);
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.ssl.socketFactory", sf);
//
//        //创建一个session对象
//        Session session = Session.getDefaultInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("619046217@qq.com","16位授权码");
//            }
//        });
//
//        //开启debug模式
//        session.setDebug(true);
//
//        //获取连接对象
//        Transport transport = session.getTransport();
//
//        //连接服务器
//        transport.connect("smtp.qq.com","619046217@qq.com","16位授权码");
//
//        //创建邮件对象
//        MimeMessage mimeMessage = new MimeMessage(session);
//
//        //邮件发送人
//        mimeMessage.setFrom(new InternetAddress("619046217@qq.com"));
//
//        //邮件接收人
//        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress("875203654@qq.com"));
//
//        //邮件标题
//        mimeMessage.setSubject("Hello Mail");
//
//        //邮件内容
//        mimeMessage.setContent("我的想法是把代码放进一个循环里","text/html;charset=UTF-8");
//
//        //发送邮件
//        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
//
//        //关闭连接
//        transport.close();
//    }
//}
