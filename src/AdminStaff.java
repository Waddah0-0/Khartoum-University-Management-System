import java.util.ArrayList;
import java.util.List;

public class AdminStaff extends User {
    private String staffId;
    private String department;
    private String role; // e.g., "Registrar", "Admissions", "Student Affairs"
    private List<String> responsibilities;
    private String officeLocation;
    private String officeHours;

    public AdminStaff(String userId, String username, String password, String name, String email,
                     String contactInfo, String staffId, String role) {
        super(userId, username, password, name, email, contactInfo);
        this.staffId = staffId;
        this.department = "Administration"; // Default department
        this.role = role;
        this.responsibilities = new ArrayList<>();
        this.officeLocation = "";
        this.officeHours = "";
    }

    // Admin staff-specific methods
    public void addResponsibility(String responsibility) {
        responsibilities.add(responsibility);
        System.out.println("Responsibility added: " + responsibility);
    }

    public void removeResponsibility(String responsibility) {
        if (responsibilities.remove(responsibility)) {
            System.out.println("Responsibility removed: " + responsibility);
        } else {
            System.out.println("Responsibility not found.");
        }
    }

    public void processStudentRegistration(Student student) {
        System.out.println("Processing registration for student: " + student.getName());
    }

    public void processCourseRegistration(Student student, Course course) {
        System.out.println("Processing course registration for student: " + student.getName() + 
                         " in course: " + course.getTitle());
    }

    public void generateReport(String reportType) {
        System.out.println("Generating " + reportType + " report...");
    }

    @Override
    public void displayMenu() {
        System.out.println("\nAdministrative Staff Menu:");
        System.out.println("1. Manage Responsibilities");
        System.out.println("2. Process Student Registration");
        System.out.println("3. Process Course Registration");
        System.out.println("4. Generate Reports");
        System.out.println("5. Update Profile");
        System.out.println("6. Logout");
    }

    @Override
    public void updateProfile() {
        System.out.println("\nUpdate Profile:");
        System.out.println("1. Update Name");
        System.out.println("2. Update Email");
        System.out.println("3. Update Contact Info");
        System.out.println("4. Change Password");
        System.out.println("5. Update Office Hours");
        System.out.println("6. Update Office Location");
        System.out.println("7. Back to Menu");
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public String getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }
} 