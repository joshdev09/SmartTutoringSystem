package models;

public abstract class Staff extends User {

    private String staffId;
    private String department;

    public Staff(String userId, String username, String password, String fullName, String email, String staffId, String department) {
        super(userId, username, password, fullName, email);
        this.staffId = staffId;
        this.department = department;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
