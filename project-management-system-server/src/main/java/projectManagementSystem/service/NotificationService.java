package projectManagementSystem.service;

import org.springframework.stereotype.Service;
import projectManagementSystem.controller.response.NotificationResponse;
import projectManagementSystem.entity.BoardAction;
import projectManagementSystem.entity.User;
import projectManagementSystem.entity.notifications.NotificationPreference;
import projectManagementSystem.entity.notifications.NotificationVia;
import projectManagementSystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    private UserRepository userRepository;

    public NotificationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<NotificationResponse> notifyAll(List<User> users, long boardId, BoardAction action) {
        List<NotificationResponse> responses = new ArrayList<>();

        for (User user : users) {
            responses.add(notify(user, boardId, action));
        }

        return responses;
    }

    public NotificationResponse notify(User user, long boardId, BoardAction action) {
        NotificationResponse notificationResponse = null;

        NotificationPreference preference = user.getNotificationPreferences();
        if (preference.getNotifyOnEvents().contains(action)) {
            String message = buildNotificationMessage(boardId, action);

            if (preference.getNotificationViaList().contains(NotificationVia.EMAIL)) {
                String email = user.getEmail();
                // TODO: send mail
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
