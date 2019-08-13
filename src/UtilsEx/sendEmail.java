/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilsEx;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author 
 */
public class sendEmail {

   public static void send_Email(String smtpServer, String to, String from, String psw,
            String subject, String body) throws Exception {

        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.starttls.enable", "true");
        final String login = from;
        final String pwd = psw;
        Authenticator pa = null;
        if (login != null && pwd != null) {
            props.put("mail.smtp.auth", "true");
            pa = new Authenticator() {

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(login, pwd);
                }
            };
        }//else: no authentication
        Session session = Session.getInstance(props, pa);
// — Create a new message –
        Message msg = new MimeMessage(session);
// — Set the FROM and TO fields –
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
                to, false));

// — Set the subject and body text –
        msg.setSubject(subject);
        msg.setText(body);//Để gởi nội dung dạng utf-8 các bạn dùng msg.setContent(body, "text/html; charset=UTF-8");
// — Set some other header information –
        msg.setHeader("X-Mailer", "LOTONtechEmail");
        msg.setSentDate(new Date());
        msg.saveChanges();
// — Send the message –
        Transport.send(msg);
        System.out.println("Mail da duoc gui");

    }
}
