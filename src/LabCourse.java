public class LabCourse extends Course {
    private String equipmentRequired;
    private int labCapacity;

    public LabCourse(String courseId, String title, String description, int creditHours,
                    int availableSeats, String schedule, String location,
                    String equipmentRequired, int labCapacity) {
        super(courseId, title, description, creditHours, availableSeats, schedule, location);
        this.equipmentRequired = equipmentRequired;
        this.labCapacity = labCapacity;
        this.courseType = "Lab";
    }

    @Override
    public void displayCourseInfo() {
        System.out.println("\nLab Course Information:");
        System.out.println("ID: " + courseId);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Credit Hours: " + creditHours);
        System.out.println("Lab Capacity: " + labCapacity);
        System.out.println("Equipment Required: " + equipmentRequired);
        System.out.println("Schedule: " + schedule);
        System.out.println("Location: " + location);
        System.out.println("Instructor: " + (instructor != null ? instructor.getName() : "Not assigned"));
        System.out.println("Enrolled Students: " + enrolledStudents.size() + "/" + availableSeats);
    }

    @Override
    public boolean addStudent(Student student) {
        if (enrolledStudents.size() < labCapacity && isPrerequisiteSatisfied(student)) {
            enrolledStudents.add(student);
            return true;
        }
        return false;
    }

    @Override
    public void removeStudent(Student student) {
        enrolledStudents.remove(student);
    }

    // Getters and setters
    public String getEquipmentRequired() {
        return equipmentRequired;
    }

    public void setEquipmentRequired(String equipmentRequired) {
        this.equipmentRequired = equipmentRequired;
    }

    public int getLabCapacity() {
        return labCapacity;
    }

    public void setLabCapacity(int labCapacity) {
        this.labCapacity = labCapacity;
    }
} 