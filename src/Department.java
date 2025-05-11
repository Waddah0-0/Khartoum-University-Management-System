import java.util.ArrayList;
import java.util.List;

public abstract class Department {
    protected String departmentId;
    protected String name;
    protected String headOfDepartment;
    protected List<Faculty> faculty;
    protected List<Course> offeredCourses;
    protected String location;
    protected String contactInfo;

    public Department(String departmentId, String name, String headOfDepartment) {
        this.departmentId = departmentId;
        this.name = name;
        this.headOfDepartment = headOfDepartment;
        this.faculty = new ArrayList<>();
        this.offeredCourses = new ArrayList<>();
    }

    public abstract void initializeDepartment();
    public abstract boolean validateCourse(Course course);
    public abstract void displayDepartmentSpecificInfo();

    public void addFaculty(Faculty member) {
        if (!faculty.contains(member)) {
            faculty.add(member);
            member.setDepartment(name);
        }
    }

    public void removeFaculty(Faculty member) {
        faculty.remove(member);
    }

    public void addCourse(Course course) {
        if (validateCourse(course) && !offeredCourses.contains(course)) {
            offeredCourses.add(course);
        }
    }

    public void removeCourse(Course course) {
        offeredCourses.remove(course);
    }

    public List<Faculty> getAvailableFacultyForCourse(Course course) {
        List<Faculty> availableFaculty = new ArrayList<>();
        for (Faculty member : faculty) {
            if (member.canTakeMoreCourses() && !member.hasSchedulingConflict(course)) {
                availableFaculty.add(member);
            }
        }
        return availableFaculty;
    }

    public void displayDepartmentInfo() {
        System.out.println("\nDepartment Information");
        System.out.println("Name: " + name);
        System.out.println("ID: " + departmentId);
        System.out.println("Head of Department: " + headOfDepartment);
        System.out.println("Location: " + location);
        System.out.println("Contact: " + contactInfo);
        
        System.out.println("\nFaculty Members: " + faculty.size());
        for (Faculty member : faculty) {
            System.out.println("\nName: " + member.getName());
            System.out.println("Specialization: " + member.getSpecialization());
            System.out.println("Courses Teaching: " + member.getCoursesTeaching().size());
        }
        
        System.out.println("\nOffered Courses: " + offeredCourses.size());
        for (Course course : offeredCourses) {
            System.out.println("\nCourse: " + course.getTitle());
            System.out.println("ID: " + course.getCourseId());
            System.out.println("Credit Hours: " + course.getCreditHours());
        }

        displayDepartmentSpecificInfo();
    }

    public void assignFacultyToCourse(Faculty faculty, Course course) {
        if (faculty.assignCourse(course)) {
            System.out.println("Successfully assigned " + faculty.getName() + " to " + course.getTitle());
        } else {
            System.out.println("Failed to assign faculty to course. Please check workload and scheduling conflicts.");
        }
    }

    public List<Course> getCourses() {
        return offeredCourses;
    }

    public String getDepartmentId() { return departmentId; }
    public String getName() { return name; }
    public String getHeadOfDepartment() { return headOfDepartment; }
    public List<Faculty> getFaculty() { return faculty; }
    public List<Course> getOfferedCourses() { return offeredCourses; }
    public String getLocation() { return location; }
    public String getContactInfo() { return contactInfo; }

    public void setName(String name) { this.name = name; }
    public void setHeadOfDepartment(String headOfDepartment) { this.headOfDepartment = headOfDepartment; }
    public void setLocation(String location) { this.location = location; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }
} 