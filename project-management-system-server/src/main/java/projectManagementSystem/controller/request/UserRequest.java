package projectManagementSystem.controller.request;

import projectManagementSystem.entity.LoginMethod;

public class UserRequest {
    private String email;
    private String password;
    private LoginMethod loginMethod;

    public UserRequest() {
    }

    public UserRequest(String email, String password, LoginMethod loginMethod) {
        this.email = email;
        this.password = password;
        this.loginMethod = loginMethod;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LoginMethod getLoginMethod() {
        return loginMethod;
    }
}
