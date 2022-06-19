package com.example.demo.service.ServiceImp;

import com.example.demo.model.User;
import com.example.demo.service.MailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    @Autowired
    private HttpServletRequest request;

    private final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);


    @Value("${spring.mail.properties.mail.smtp.from}")
    private String FROM;

    @Value("${spring.mail.host}")
    private String HOST;

    @Value("${spring.mail.port}")
    private String PORT;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean SMTP_AUTH;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean STARTTLS;

    @Value("${spring.mail.properties.mail.smtp.ssl.enable}")
    private boolean SMTP_SSL;

    @Value("${spring.mail.username}")
    private String USERNAME;

    @Value("${spring.mail.password}")
    private String PASSWORD;

    @Value("${app.name}")
    private String APP_NAME;
    @Override
    public void sendPasswordAndECodeMail (User receiver,String password, Integer e_code) {
        try {
            String title = "Bienvenue chez "+APP_NAME;
            String body = "Merci de votre inscription chez "+APP_NAME+".<br> Votre "+APP_NAME+" E-CODE est : <br> <strong>"+ e_code+"</strong><br><br> Vouz trouvez ci-dessous votre mot de passe , merci de ne pas les partager avec aucune autre :";
            String htmlMsg =  getHtmlMsg(title,body,password,receiver);
            MimeMessage message = getMimeMessage(
                    receiver.getEmail(),
                    "Bienvenue chez "+APP_NAME,
                    htmlMsg
            );
            // Send message
            Transport.send(message);
            logger.info("Message sent successfully.");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void sendPasswordMail (User receiver,String password) {
        try {
            String title = "Bienvenue chez "+APP_NAME;
            String body = "Merci de votre inscription chez "+APP_NAME+". Vouz trouvez ci-dessous votre mot de passe, merci de ne pas le partager avec aucune autre :";
            String htmlMsg =  getHtmlMsg(title,body,password,receiver);
            MimeMessage message = getMimeMessage(
                    receiver.getEmail(),
                    "Bienvenue chez "+APP_NAME,
                    htmlMsg
            );
            // Send message
            Transport.send(message);
            logger.info("Message sent successfully.");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    private Session getMailSession() {
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", HOST);
        properties.put("mail.smtp.port", PORT);
        properties.put("mail.smtp.starttls.enable", STARTTLS);
        properties.put("mail.smtp.auth", SMTP_AUTH);
        //properties.put("mail.smtp.ssl.enable", SMTP_SSL);
        properties.put("mail.mime.charset", "UTF-8");

        // Get the Session object
        Session session = Session.getInstance(properties, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }

        });
        // Used to debug SMTP issues
        session.setDebug(true);
        return session;
    }

    private MimeMessage getMimeMessage(String to, String subject, String htmlMsg) throws MessagingException {
        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(getMailSession());

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(FROM));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: header field
        message.setSubject(subject);

        // Now set the actual message;
        message.setContent(htmlMsg, "text/html");
        //message.setText(text);
        return message;
    }

    private String getHtmlMsg(String title,String body,String data,User receiver){
        String htmlMsg = "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">"+title+"</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Salut "+ receiver.getUsername() +",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> "+body+" </p><strong>"+data+"</strong><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"></p></blockquote><p>À bientôt</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
        return htmlMsg;
    }
}
