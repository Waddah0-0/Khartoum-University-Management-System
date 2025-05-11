public class ComputerScienceDepartment extends Department {
    public ComputerScienceDepartment(String departmentId, String name, String headOfDepartment) {
        super(departmentId, name, headOfDepartment);
        initializeDepartment();
    }

    @Override
    public void initializeDepartment() {
        setLocation("Engineering Building");
        setContactInfo("cs@university.edu");
    }

    @Override
    public boolean validateCourse(Course course) {
        return true;
    }

    @Override
    public void displayDepartmentSpecificInfo() {
        System.out.println("Computer Science Department");
    }
} 