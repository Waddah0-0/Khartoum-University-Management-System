import java.util.ArrayList;
import java.util.List;

public class University {
    private String name;
    private List<Department> departments;
    private List<User> users;
    private List<Course> courses;
    private String academicCalendar;

    public University(String name) {
        this.name = name;
        this.departments = new ArrayList<>();
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.academicCalendar = "Fall 2024";
    }

    public void registerStudent(Student student) {
        users.add(student);
        System.out.println("Student " + student.getName() + " has been registered successfully.");
    }

    public void hireFaculty(Faculty faculty) {
        users.add(faculty);
        System.out.println("Faculty member " + faculty.getName() + " has been hired successfully.");
    }

    public void createDepartment(Department department) {
        departments.add(department);
        System.out.println("Department " + department.getName() + " has been created successfully.");
    }

    public void offerCourse(Course course) {
        courses.add(course);
        System.out.println("Course " + course.getTitle() + " has been offered successfully.");
    }

    public void addUser(User user) {
        if (user instanceof Student) {
            registerStudent((Student) user);
        } else if (user instanceof Faculty) {
            hireFaculty((Faculty) user);
        } else {
            users.add(user);
            System.out.println("User " + user.getName() + " has been added successfully.");
        }
    }

    public void displayUniversityInfo() {
        System.out.println("\nUniversity Information:");
        System.out.println("Name: " + name);
        System.out.println("Academic Calendar: " + academicCalendar);
        System.out.println("Number of Departments: " + departments.size());
        System.out.println("Total Users: " + users.size());
        System.out.println("Total Courses: " + courses.size());
    }

    public User createUser(String userId, String username, String password, String name, 
                         String email, String contactInfo, String role, String... additionalInfo) {
        User user = null;
        switch (role.toUpperCase()) {
            case "STUDENT":
                user = new Student(userId, username, password, name, email, contactInfo, 
                                 additionalInfo[0], additionalInfo[1]);
                break;
            case "FACULTY":
                user = new Faculty(userId, username, password, name, email, contactInfo, 
                                 additionalInfo[0], additionalInfo[1], additionalInfo[2]);
                break;
            case "ADMIN_STAFF":
                user = new AdminStaff(userId, username, password, name, email, contactInfo, 
                                    additionalInfo[0], additionalInfo[1]);
                break;
        }
        if (user != null) {
            addUser(user);
            FileManager.saveUsers(users);
        }
        return user;
    }

    public Course createCourse(String courseId, String title, String description, int creditHours,
                             int availableSeats, String schedule, String location, String courseType,
                             String... additionalInfo) {
        Course course = null;
        switch (courseType.toUpperCase()) {
            case "LECTURE":
                course = new LectureCourse(courseId, title, description, creditHours, 
                    availableSeats, schedule, location, additionalInfo[0], 
                    Integer.parseInt(additionalInfo[1]));
                break;
            case "LAB":
                course = new LabCourse(courseId, title, description, creditHours, 
                    availableSeats, schedule, location, additionalInfo[0], 
                    Integer.parseInt(additionalInfo[1]));
                break;
            case "SEMINAR":
                course = new SeminarCourse(courseId, title, description, creditHours, 
                    availableSeats, schedule, location, additionalInfo[0], 
                    Integer.parseInt(additionalInfo[1]), 
                    Boolean.parseBoolean(additionalInfo[2]));
                break;
        }
        if (course != null) {
            offerCourse(course);
            FileManager.saveCourses(courses);
        }
        return course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public String getAcademicCalendar() {
        return academicCalendar;
    }

    public void setAcademicCalendar(String academicCalendar) {
        this.academicCalendar = academicCalendar;
    }
} 