package projectManagementSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import projectManagementSystem.controller.response.NotificationResponse;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.DTO.AuthorizedUserDTO;
import projectManagementSystem.entity.DTO.BoardDTO;
import projectManagementSystem.entity.notifications.NotificationPreference;
import projectManagementSystem.entity.notifications.NotificationVia;
import projectManagementSystem.repository.UserRepository;
import projectManagementSystem.utils.EmailUtil;

import java.util.Optional;

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
     * @param board
     * @param action
     */
    public void notifyAll(BoardDTO board, BoardAction action) {
        for (AuthorizedUserDTO user : board.getAuthorizedUsers()) {
            notify(user, board, action);
        }
    }

    /**
     * Notifies all users regarding an action the occurred in the board, according to their notifications preferences.
     * @param user
     * @param board
     * @param action
     */
    public void notify(AuthorizedUserDTO user, BoardDTO board, BoardAction action) {
        if (shouldNotifyUser(user, action)) {
            String message = buildNotificationMessage(board.getId(), action);

            if (shouldNotifyBy(user, NotificationVia.EMAIL)) {
                Optional<SimpleMailMessage> mailMessage = emailUtil.sendEmail(user.getEmail(), buildNotificationSubject(board.getId()), message);
                if (!mailMessage.isPresent()) {
                    throw new IllegalArgumentException("Error sending notification on action: " + action +
                            "at board id: " + board.getId() + ". Recipient email must be valid, email subject and body can't be null.");
                }
            }

            if (shouldNotifyBy(user, NotificationVia.POP_UP)) {
                board.addNotification(new NotificationResponse(user.getId(), message));
            }
        }
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

    private boolean shouldNotifyUser(AuthorizedUserDTO user, BoardAction action) {
        NotificationPreference preference = user.getNotificationPreference();
        return (preference != null && preference.getNotifyOnEvents().contains(action));
    }

    private boolean shouldNotifyBy(AuthorizedUserDTO user, NotificationVia via) {
        NotificationPreference preference = user.getNotificationPreference();
        return (preference != null && preference.getNotificationViaList().contains(via));
    }
}
