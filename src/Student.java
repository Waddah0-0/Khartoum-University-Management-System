import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private String studentId;
    private String admissionDate;
    private String academicStatus; 
    private String department;
    private double GPA;
    private List<Course> enrolledCourses;
    private List<Double> grades;
    private List<List<Boolean>> attendance; 
    private int totalCredits;
    private static final int MAX_CREDITS = 21; 

    public Student(String userId, String username, String password, String name, String email, 
                  String contactInfo, String studentId, String admissionDate) {
        super(userId, username, password, name, email, contactInfo);
        this.studentId = studentId;
        this.admissionDate = admissionDate;
        this.academicStatus = "Active";
        this.department = "Computer Science"; 
        this.enrolledCourses = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.attendance = new ArrayList<>();
        this.totalCredits = 0;
    }

    public boolean registerForCourse(Course course) {
        if (totalCredits + course.getCreditHours() > MAX_CREDITS) {
            System.out.println("Cannot register. Maximum credits (" + MAX_CREDITS + ") would be exceeded.");
            return false;
        }

        if (!course.isPrerequisiteSatisfied(this)) {
            System.out.println("Cannot register. Prerequisites not satisfied.");
            return false;
        }

        if (enrolledCourses.size() < 5) { 
            if (course.addStudent(this)) {
                enrolledCourses.add(course);
                grades.add(0.0); 
                attendance.add(new ArrayList<>()); 
                totalCredits += course.getCreditHours();
                System.out.println("Successfully registered for " + course.getTitle());
                return true;
            }
        }
        System.out.println("Cannot register for more courses. Maximum limit reached.");
        return false;
    }

    public void dropCourse(Course course) {
        int index = enrolledCourses.indexOf(course);
        if (index != -1) {
            course.removeStudent(this);
            enrolledCourses.remove(index);
            grades.remove(index);
            attendance.remove(index);
            totalCredits -= course.getCreditHours();
            System.out.println("Successfully dropped " + course.getTitle());
        } else {
            System.out.println("Course not found in your enrollment.");
        }
    }

    public void viewGrades() {
        System.out.println("\nYour Grades:");
        System.out.println("Course\t\tGrade");
        System.out.println("----------------------");
        for (int i = 0; i < enrolledCourses.size(); i++) {
            System.out.println(enrolledCourses.get(i).getTitle() + "\t\t" + grades.get(i));
        }
    }

    public double calculateGPA() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    public void markAttendance(Course course, boolean present) {
        int index = enrolledCourses.indexOf(course);
        if (index != -1) {
            attendance.get(index).add(present);
            System.out.println("Attendance marked for " + course.getTitle());
        } else {
            System.out.println("Not enrolled in this course.");
        }
    }

    public void viewAttendance(Course course) {
        int index = enrolledCourses.indexOf(course);
        if (index != -1) {
            List<Boolean> courseAttendance = attendance.get(index);
            System.out.println("\nAttendance for " + course.getTitle() + ":");
            System.out.println("Total Classes: " + courseAttendance.size());
            int present = 0;
            for (boolean p : courseAttendance) {
                if (p) present++;
            }
            System.out.println("Present: " + present);
            System.out.println("Absent: " + (courseAttendance.size() - present));
            System.out.println("Attendance Rate: " + 
                (courseAttendance.size() > 0 ? (present * 100.0 / courseAttendance.size()) : 0) + "%");
        } else {
            System.out.println("Not enrolled in this course.");
        }
    }

    public void updateAcademicStatus() {
        double gpa = calculateGPA();
        if (gpa < 2.0) {
            academicStatus = "On Probation";
        } else if (gpa >= 2.0) {
            academicStatus = "Active";
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("\nStudent Menu:");
        System.out.println("1. Course Management");
        System.out.println("2. Academic Records");
        System.out.println("3. Attendance");
        System.out.println("4. Profile Management");
        System.out.println("5. Logout");
    }

    @Override
    public void updateProfile() {
        System.out.println("\nUpdate Profile:");
        System.out.println("1. Update Name");
        System.out.println("2. Update Email");
        System.out.println("3. Update Contact Info");
        System.out.println("4. Change Password");
        System.out.println("5. Back to Menu");
    }

    public double Payroll() {
        return 0.0;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }
}