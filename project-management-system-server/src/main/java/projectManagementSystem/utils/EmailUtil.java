package projectManagementSystem.utils;

import org.springframework.mail.SimpleMailMessage;

public class EmailUtil {
    private static final String FROM = "startgooglproject@gmail.com";

    /**
     * @param recipient - String, email address of the recipient.
     * @param subject   - String, subject of the email.
     * @param body      - String, content of the email.
     * @return SimpleMailMessage object - includes the data: from email address, to email address, email subject, and email body text.
     */
    public static SimpleMailMessage prepareMessage(String recipient, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);
        return message;
    }

}
