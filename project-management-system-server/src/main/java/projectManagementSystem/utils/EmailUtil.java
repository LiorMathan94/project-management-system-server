package projectManagementSystem.utils;

import org.springframework.mail.SimpleMailMessage;

public class EmailUtil {
    private static final String FROM = "startgooglproject@gmail.com";

    public static SimpleMailMessage prepareMessage(String recipient, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);
        return message;
    }

}
