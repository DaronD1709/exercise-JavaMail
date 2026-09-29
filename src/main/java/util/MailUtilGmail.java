package util;

import java.nio.charset.StandardCharsets;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * JavaMail helper for Gmail SMTP.
 *
 * Configuration is read in this order:
 * 1) JVM system property (mail.username, mail.password, mail.from, mail.to)
 * 2) environment variable (MAIL_USERNAME, MAIL_PASSWORD, MAIL_FROM, MAIL_TO)
 * 3) built-in default only for MAIL_TO
 *
 * Do not put the Gmail password in source code.
 */
public final class MailUtilGmail {

    private static final String DEFAULT_TO = "danhhuunguyen1709@gmail.com";

    private MailUtilGmail() {
    }

    public static void sendMail(
            String to,
            String from,
            String subject,
            String body,
            boolean bodyIsHTML) throws MessagingException {

        final String username = requireConfig("mail.username", "MAIL_USERNAME",
                "Please configure mail.username / MAIL_USERNAME with the Gmail account used to send mail.");
        final String password = requireConfig("mail.password", "MAIL_PASSWORD",
                "Please configure mail.password / MAIL_PASSWORD with a Gmail App Password.");

        String smtpHost = getConfig("mail.smtp.host", "MAIL_SMTP_HOST", "smtp.gmail.com");
        String smtpPort = getConfig("mail.smtp.port", "MAIL_SMTP_PORT", "465");

        if (to == null || to.isBlank()) {
            to = getMailTo();
        }
        if (from == null || from.isBlank()) {
            from = getMailFrom();
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", smtpHost);
        props.put("mail.smtp.quitwait", "false");

        final String authUsername = username;
        final String authPassword = password.replaceAll("\\s+", "");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(authUsername, authPassword);
            }
        });

        // During local development, enable JavaMail protocol debugging with -Dmail.debug=true.
        boolean debug = Boolean.parseBoolean(getConfig("mail.debug", "MAIL_DEBUG", "false"));
        session.setDebug(debug);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject, StandardCharsets.UTF_8.name());

        if (bodyIsHTML) {
            message.setContent(body, "text/html; charset=UTF-8");
        } else {
            message.setText(body, StandardCharsets.UTF_8.name());
        }

        Transport.send(message);
    }

    public static String getMailTo() {
        return getConfig("mail.to", "MAIL_TO", DEFAULT_TO);
    }

    public static String getMailFrom() {
        String from = getConfig("mail.from", "MAIL_FROM", "");
        if (!from.isBlank()) {
            return from;
        }
        return requireConfig("mail.username", "MAIL_USERNAME",
                "Please configure mail.username / MAIL_USERNAME before sending mail.");
    }

    public static String getUsername() {
        return getConfig("mail.username", "MAIL_USERNAME", "");
    }

    private static String requireConfig(String systemProperty, String envVariable, String errorMessage) {
        String value = getConfig(systemProperty, envVariable, "");
        if (value.isBlank()) {
            throw new IllegalStateException(errorMessage);
        }
        return value;
    }

    private static String getConfig(String systemProperty, String envVariable, String defaultValue) {
        String value = System.getProperty(systemProperty);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }

        value = System.getenv(envVariable);
        if (value != null && !value.isBlank()) {
            return value.trim();
        }

        return defaultValue;
    }
}
