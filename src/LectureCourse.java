public class LectureCourse extends Course {
    private String lectureFormat; 
    private int maxClassSize;

    public LectureCourse(String courseId, String title, String description, int creditHours,
                        int availableSeats, String schedule, String location,
                        String lectureFormat, int maxClassSize) {
        super(courseId, title, description, creditHours, availableSeats, schedule, location);
        this.lectureFormat = lectureFormat;
        this.maxClassSize = maxClassSize;
        this.courseType = "Lecture";
    }

    @Override
    public void displayCourseInfo() {
        System.out.println("\nLecture Course Information:");
        System.out.println("ID: " + courseId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Credit Hours: " + creditHours);
        System.out.println("Lecture Format: " + lectureFormat);
        System.out.println("Maximum Class Size: " + maxClassSize);
        System.out.println("Schedule: " + schedule);
        System.out.println("Location: " + location);
        System.out.println("Instructor: " + (instructor != null ? instructor.getName() : "Not assigned"));
        System.out.println("Enrolled Students: " + enrolledStudents.size() + "/" + availableSeats);
    }

    @Override
    public boolean addStudent(Student student) {
        if (enrolledStudents.size() < maxClassSize && isPrerequisiteSatisfied(student)) {
            enrolledStudents.add(student);
            return true;
        }
        return false;
    }

    @Override
    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public String getLectureFormat() {
        return lectureFormat;
    }

    public void setLectureFormat(String lectureFormat) {
        this.lectureFormat = lectureFormat;
    }

    public int getMaxClassSize() {
        return maxClassSize;
    }

    public void setMaxClassSize(int maxClassSize) {
        this.maxClassSize = maxClassSize;
    }
} 