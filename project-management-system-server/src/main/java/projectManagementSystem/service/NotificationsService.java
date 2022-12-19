package projectManagementSystem.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationsService {

    public void sendEmailNotification(String userEmail, String action, String boardTitle){
        //if the notification is that the board was deleted I can't find board title by boardId
        //replace action String with the Permissions Enum


    }
}
