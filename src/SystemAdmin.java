import java.util.ArrayList;
import java.util.List;

public class SystemAdmin extends User {
    private String adminId;
    private int accessLevel;
    private List<String> systemLogs;
    private University university;

    public SystemAdmin(String userId, String username, String password, String name, String email,
                      String contactInfo, String adminId, int accessLevel, University university) {
        super(userId, username, password, name, email, contactInfo);
        this.adminId = adminId;
        this.accessLevel = accessLevel;
        this.systemLogs = new ArrayList<>();
        this.university = university;
    }

    public String getAdminId() { return adminId; }
    public int getAccessLevel() { return accessLevel; }
    public List<String> getSystemLogs() { return systemLogs; }

    public void setAccessLevel(int accessLevel) { this.accessLevel = accessLevel; }
    public void createNewUser() {
        System.out.println("\nCreate New User");
        System.out.println("1. Create Student");
        System.out.println("2. Create Faculty");
        System.out.println("3. Create Admin Staff");
        System.out.println("4. Back to Main Menu");
    }

    public void modifySystemSettings(String setting, String value) {
        System.out.println("Modifying system setting: " + setting + " to value: " + value);
        logAction("Modified system setting: " + setting);
    }

    public void backupData() {
        System.out.println("Starting system backup...");
                String timestamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String backupDir = "backup_" + timestamp;
        java.io.File dir = new java.io.File(backupDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        try {
            backupUsers(backupDir);
            
            backupCourses(backupDir);
            
            backupDepartments(backupDir);
            
            backupEnrollments(backupDir);
            
            System.out.println("Backup completed successfully in directory: " + backupDir);
            logAction("System backup created: " + backupDir);
        } catch (Exception e) {
            System.out.println("Error during backup: " + e.getMessage());
            logAction("Backup failed: " + e.getMessage());
        }
    }

    private void backupUsers(String backupDir) throws java.io.IOException {
        java.io.FileWriter writer = new java.io.FileWriter(backupDir + "/users_backup.txt");
        for (User user : university.getUsers()) {
            writer.write(user.getUserId() + "," + user.getUsername() + "," + user.getPassword() + "," +
                        user.getName() + "," + user.getEmail() + "," + user.getContactInfo() + "\n");
        }
        writer.close();
    }

    private void backupCourses(String backupDir) throws java.io.IOException {
        java.io.FileWriter writer = new java.io.FileWriter(backupDir + "/courses_backup.txt");
        for (Course course : university.getCourses()) {
            writer.write(course.getCourseId() + "," + course.getTitle() + "," + course.getDescription() + "," +
                        course.getCreditHours() + "," + course.getSchedule() + "," + course.getLocation() + "\n");
        }
        writer.close();
    }

    private void backupDepartments(String backupDir) throws java.io.IOException {
        java.io.FileWriter writer = new java.io.FileWriter(backupDir + "/departments_backup.txt");
        for (Department dept : university.getDepartments()) {
            writer.write(dept.getDepartmentId() + "," + dept.getName() + "," + dept.getHeadOfDepartment() + "\n");
        }
        writer.close();
    }

    private void backupEnrollments(String backupDir) throws java.io.IOException {
        java.io.FileWriter writer = new java.io.FileWriter(backupDir + "/enrollments_backup.txt");
        for (Course course : university.getCourses()) {
            for (Student student : course.getEnrolledStudents()) {
                writer.write(course.getCourseId() + "," + student.getStudentId() + "\n");
            }
        }
        writer.close();
    }

    public void managePermissions() {
        System.out.println("\nManage User Permissions");
        System.out.println("1. View User Permissions");
        System.out.println("2. Modify User Permissions");
        System.out.println("3. Back to Main Menu");
    }

    private void logAction(String action) {
        String logEntry = java.time.LocalDateTime.now() + " - " + action;
        systemLogs.add(logEntry);
    }

    @Override
    public void displayMenu() {
        System.out.println("\nSystem Administrator Menu:");
        System.out.println("1. Create New User");
        System.out.println("2. Create New Course");
        System.out.println("3. Backup System Data");
        System.out.println("4. Manage User Permissions");
        System.out.println("5. Faculty Management");
        System.out.println("6. Update Profile");
        System.out.println("7. Logout");
    }

    @Override
    public void updateProfile() {
        System.out.println("\nUpdate Profile");
        System.out.println("1. Update Name");
        System.out.println("2. Update Email");
        System.out.println("3. Update Contact Info");
        System.out.println("4. Change Password");
        System.out.println("5. Back to Main Menu");
    }

    public void manageFaculty() {
        System.out.println("\nFaculty Management:");
        System.out.println("1. Assign Faculty to Course");
        System.out.println("2. View Faculty Performance");
        System.out.println("3. Manage Department Structure");
        System.out.println("4. Back to Main Menu");
    }

    public void createNewCourse() {
        System.out.println("\nCreate New Course");
        System.out.println("1. Create Lecture Course");
        System.out.println("2. Create Lab Course");
        System.out.println("3. Create Seminar Course");
        System.out.println("4. Back to Main Menu");
    }
} 