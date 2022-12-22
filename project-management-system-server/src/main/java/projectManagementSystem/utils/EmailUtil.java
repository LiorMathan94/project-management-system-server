package projectManagementSystem.utils;

import org.springframework.mail.SimpleMailMessage;

import java.util.Optional;

public class EmailUtil {
    private static final String FROM = "startgooglproject@gmail.com";

    /**
     * Creates and returns a simple mail message (includes data: from, to, subject and body text) if recipient's email is valid and email subject and body are not null.
     *
     * @param recipient - String, email address of the recipient.
     * @param subject   - String, subject of the email.
     * @param body      - String, content of the email.
     * @return Optional<SimpleMailMessage> - contains SimpleMailMessage if recipient's email is valid and email subject and body are not null, else - Optional.empty().
     */
    public static Optional<SimpleMailMessage> prepareMailMessage(String recipient, String subject, String body) {
        if (!InputValidation.isValidEmail(recipient) || subject == null || body == null) {
            return Optional.empty();
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM);
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(body);
        return Optional.of(message);
    }

}
