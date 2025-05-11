public class SeminarCourse extends Course {
    private String mainTopic;
    private int maxParticipants;
    private boolean requiresPresentation;

    public SeminarCourse(String courseId, String title, String description, int creditHours,
                        int availableSeats, String schedule, String location,
                        String mainTopic, int maxParticipants, boolean requiresPresentation) {
        super(courseId, title, description, creditHours, availableSeats, schedule, location);
        this.mainTopic = mainTopic;
        this.maxParticipants = maxParticipants;
        this.requiresPresentation = requiresPresentation;
        this.courseType = "Seminar";
    }

    @Override
    public void displayCourseInfo() {
        System.out.println("\nSeminar Course Information:");
        System.out.println("ID: " + courseId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Credit Hours: " + creditHours);
        System.out.println("Main Topic: " + mainTopic);
        System.out.println("Maximum Participants: " + maxParticipants);
        System.out.println("Presentation Required: " + (requiresPresentation ? "Yes" : "No"));
        System.out.println("Schedule: " + schedule);
        System.out.println("Location: " + location);
        System.out.println("Instructor: " + (instructor != null ? instructor.getName() : "Not assigned"));
        System.out.println("Enrolled Students: " + enrolledStudents.size() + "/" + availableSeats);
    }

    @Override
    public boolean addStudent(Student student) {
        if (enrolledStudents.size() < maxParticipants && isPrerequisiteSatisfied(student)) {
            enrolledStudents.add(student);
            return true;
        }
        return false;
    }

    @Override
    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    public String getMainTopic() {
        return mainTopic;
    }

    public void setMainTopic(String mainTopic) {
        this.mainTopic = mainTopic;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public boolean isRequiresPresentation() {
        return requiresPresentation;
    }

    public void setRequiresPresentation(boolean requiresPresentation) {
        this.requiresPresentation = requiresPresentation;
    }
} 