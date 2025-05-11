import java.util.ArrayList;
import java.util.List;

public abstract class Course {
    protected String courseId;
    protected String title;
    protected String description;
    protected int creditHours;
    protected int availableSeats;
    protected String schedule;
    protected String location;
    protected Faculty instructor;
    protected List<Student> enrolledStudents;
    protected List<Course> prerequisites;
    protected String courseType;

    public Course(String courseId, String title, String description, int creditHours, 
                 int availableSeats, String schedule, String location) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.creditHours = creditHours;
        this.availableSeats = availableSeats;
        this.schedule = schedule;
        this.location = location;
        this.enrolledStudents = new ArrayList<>();
        this.prerequisites = new ArrayList<>();
    }

    public abstract void displayCourseInfo();
    public abstract boolean addStudent(Student student);
    public abstract void removeStudent(Student student);

    public void addPrerequisite(Course course) {
        if (!prerequisites.contains(course)) {
            prerequisites.add(course);
            System.out.println("Prerequisite " + course.getTitle() + " added successfully.");
        }
    }

    public void removePrerequisite(Course course) {
        prerequisites.remove(course);
        System.out.println("Prerequisite " + course.getTitle() + " removed successfully.");
    }

    public boolean isPrerequisiteSatisfied(Student student) {
        if (prerequisites.isEmpty()) {
            return true;
        }

        for (Course prerequisite : prerequisites) {
            boolean hasPassed = false;
            for (Course enrolledCourse : student.getEnrolledCourses()) {
                if (enrolledCourse.getCourseId().equals(prerequisite.getCourseId())) {
                    int index = student.getEnrolledCourses().indexOf(enrolledCourse);
                    if (index != -1 && student.getGrades().get(index) >= 60.0) {
                        hasPassed = true;
                        break;
                    }
                }
            }
            if (!hasPassed) {
                System.out.println("Prerequisite course " + prerequisite.getTitle() + " not satisfied.");
                return false;
            }
        }
        return true;
    }

    public int getAvailableSeats() {
        return availableSeats - enrolledStudents.size();
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Faculty getInstructor() {
        return instructor;
    }

    public void setInstructor(Faculty instructor) {
        this.instructor = instructor;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public String getCourseType() {
        return courseType;
    }
} 