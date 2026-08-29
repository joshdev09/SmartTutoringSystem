package models;

public abstract class User {

    private String userId;
    private String username;
    private String password;
    private String fullName;
    private String email;

    public User(String userId, String username, String password, String fullName, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean checkPassword(String attempt) {
        return password.equals(attempt);
    }

    // POLYMORPHISM: Student and Teacher will each return their own role text.
    public abstract String getRole();

    public String toString() {
        return "[" + getRole() + "] " + fullName + " (username: " + username + ")";
    }
}
