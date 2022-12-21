package projectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import projectManagementSystem.controller.response.NotificationResponse;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.User;
import projectManagementSystem.entity.notifications.NotificationPreference;
import projectManagementSystem.entity.notifications.NotificationVia;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.EmailUtil;

import java.util.Optional;

@Service
public class NotificationService {
    @Autowired
    JavaMailSender mailSender;
    private UserRepository userRepository;

    public NotificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public NotificationResponse notify(User user, long boardId, BoardAction action) {
        NotificationResponse notificationResponse = null;

        NotificationPreference preference = user.getNotificationPreferences();
        if (preference.getNotifyOnEvents().contains(action)) {
            String message = buildNotificationMessage(boardId, action);

            if (preference.getNotificationViaList().contains(NotificationVia.EMAIL)) {
                Optional<SimpleMailMessage> mailMessage = EmailUtil.prepareMessage(user.getEmail(), "Notification From the Best Project Management System in the World", buildNotificationMessage(boardId, action));
                if (!mailMessage.isPresent()) {
                    throw new IllegalArgumentException("Recipient email must be valid, email subject and body can't be null.");
                }
                mailSender.send(mailMessage.get());
            }

            if (preference.getNotificationViaList().contains(NotificationVia.POP_UP)) {
                notificationResponse = new NotificationResponse(user.getId(), message);
            }
        }

        return notificationResponse;
    }

    private String buildNotificationMessage(long boardId, BoardAction action) {
        return String.format("We would like to inform you that the following action has been taken on " +
                "board #%d: %s", boardId, action.getDescription());
    }
}
