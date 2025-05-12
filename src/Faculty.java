import java.util.ArrayList;
import java.util.List;

public class Faculty extends User {
    private String facultyId;
    private String department;
    private String specialization;
    private List<Course> coursesTeaching;
    private String officeHours;
    private int maxCourses = 4;

    public Faculty(String userId, String username, String password, String name, String email,
                  String contactInfo, String facultyId, String department, String specialization) {
        super(userId, username, password, name, email, contactInfo);
        this.facultyId = facultyId;
        this.department = department;
        this.specialization = specialization;
        this.coursesTeaching = new ArrayList<>();
    }

    public void addCourse(Course course) {
        if (coursesTeaching.size() < maxCourses) {
            coursesTeaching.add(course);
        }
    }

    public void removeCourse(Course course) {
        coursesTeaching.remove(course);
    }

    public boolean canTakeMoreCourses() {
        return coursesTeaching.size() < maxCourses;
    }

    public boolean hasSchedulingConflict(Course newCourse) {
        return coursesTeaching.stream()
            .anyMatch(course -> course.getSchedule().equals(newCourse.getSchedule()));
    }

    public void manageCourse(Course course) {
        System.out.println("\nManaging Course: " + course.getTitle());
        System.out.println("1. View Enrolled Students");
        System.out.println("2. Update Course Information");
        System.out.println("3. Back to Menu");
    }

    @Override
    public void displayMenu() {
        System.out.println("\nFaculty Menu");
        System.out.println("1. View Teaching Schedule");
        System.out.println("2. Manage Courses");
        System.out.println("3. Update Office Hours");
        System.out.println("4. Update Profile");
        System.out.println("5. Logout");
    }

    @Override
    public void updateProfile() {
        System.out.println("\nUpdate Profile");
        System.out.println("1. Update Name");
        System.out.println("2. Update Email");
        System.out.println("3. Update Contact Info");
        System.out.println("4. Update Office Hours");
        System.out.println("5. Back to Main Menu");
    }

    public boolean assignCourse(Course course) {
        if (!canTakeMoreCourses()) {
            return false;
        }
        if (hasSchedulingConflict(course)) {
            return false;
        }
        if (!coursesTeaching.contains(course)) {
            coursesTeaching.add(course);
            return true;
        }
        return false;
    }

    @Override
    public double Payroll() {
        return 5000;
    }

    public String getFacultyId() { return facultyId; }
    public String getDepartment() { return department; }
    public String getSpecialization() { return specialization; }
    public List<Course> getCoursesTeaching() { return coursesTeaching; }
    public String getOfficeHours() { return officeHours; }

    public void setDepartment(String department) { this.department = department; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }
    public void setOfficeHours(String officeHours) { this.officeHours = officeHours; }
} 