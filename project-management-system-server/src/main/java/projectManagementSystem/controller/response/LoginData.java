package projectManagementSystem.controller.response;

public class LoginData {
    private long userId;
    private String token;

    public LoginData(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}
