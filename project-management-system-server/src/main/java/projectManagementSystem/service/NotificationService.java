package projectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import projectManagementSystem.controller.response.NotificationResponse;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.User;
import projectManagementSystem.entity.notifications.NotificationPreference;
import projectManagementSystem.entity.notifications.NotificationVia;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.EmailUtil;

import java.util.Optional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private EmailUtil emailUtil;
    private UserRepository userRepository;

    /**
     * Constructor for NotificationService.
     * @param userRepository
     */
    public NotificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Notifies all users regarding an action the occurred in the board, according to their notifications preferences.
     * @param users
     * @param boardId
     * @param action
     * @return NotificationResponse
     */
    public List<NotificationResponse> notifyAll(List<User> users, long boardId, BoardAction action) {
        List<NotificationResponse> responses = new ArrayList<>();

        for (User user : users) {
            responses.add(notify(user, boardId, action));
        }

        return responses;
    }

    /**
     * Notifies all users regarding an action the occurred in the board, according to their notifications preferences.
     * @param user
     * @param boardId
     * @param action
     * @return NotificationResponse
     */
    public NotificationResponse notify(User user, long boardId, BoardAction action) {
        NotificationResponse notificationResponse = null;

        NotificationPreference preference = user.getNotificationPreferences();
        if (preference != null && preference.getNotifyOnEvents().contains(action)) {
            String message = buildNotificationMessage(boardId, action);

            if (preference.getNotificationViaList().contains(NotificationVia.EMAIL)) {
                Optional<SimpleMailMessage> mailMessage = emailUtil.sendEmail(user.getEmail(),
                        buildNotificationSubject(boardId), message);
                if (!mailMessage.isPresent()) {
                    throw new IllegalArgumentException("Error sending notification on action: " + action +
                            "at board id: " + boardId + ". Recipient email must be valid, email subject and body can't be null.");
                }
            }

            if (preference.getNotificationViaList().contains(NotificationVia.POP_UP)) {
                notificationResponse = new NotificationResponse(user.getId(), message);
            }
        }

        return notificationResponse;
    }

    /**
     * @param boardId
     * @param action
     * @return the notification message
     */
    private String buildNotificationMessage(long boardId, BoardAction action) {
        return String.format("We would like to inform you that the following action has been taken on " +
                "board #%d: %s", boardId, action.getDescription());
    }

    /**
     * @param boardId
     * @return the notification subject
     */
    private String buildNotificationSubject(long boardId) {
        return "Notification From the Best Project Management System in the World: Update on board" + boardId;
    }
}
