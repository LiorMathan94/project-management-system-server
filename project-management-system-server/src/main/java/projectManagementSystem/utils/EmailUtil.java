package projectManagementSystem.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailUtil {
    private static final String FROM = "startgooglproject@gmail.com";
    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends an email using JavaMailSender.
     *
     * @param recipient - String, email address of the recipient.
     * @param subject   - String, subject of the email.
     * @param body      - String, content of the email.
     * @return Optional<SimpleMailMessage> - contains SimpleMailMessage if recipient's email is valid and email subject and body are not null, else - Optional.empty().
     */
    public Optional<SimpleMailMessage> sendEmail(String recipient, String subject, String body) {
        Optional<SimpleMailMessage> mailMessage = prepareMailMessage(recipient, subject, body);
        if (!mailMessage.isPresent()) {
            return Optional.empty();
        }
        mailSender.send(mailMessage.get());
        return mailMessage;
    }

    /**
     * Creates a simple mail message (includes the data: from, to, subject and body text).
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
