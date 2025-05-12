import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static University university;
    private static Scanner scanner;
    private static User currentUser;
    private static List<User> users;
    private static List<Student> students;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        university = new University("Khartoum University");
                Department csDepartment = new ComputerScienceDepartment("CS", "Computer Science", "Dr. Ahmed");
        university.createDepartment(csDepartment);
        
        users = FileManager.loadAllData(university);
        students = users.stream()
            .filter(user -> user instanceof Student)
            .map(user -> (Student) user)
            .collect(java.util.stream.Collectors.toList());
        
        while (true) {
            try {
                if (currentUser == null) {
                    showLoginMenu();
                } else {
                    currentUser.displayMenu();
                    handleUserChoice();
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
                scanner.nextLine(); // Clear the input buffer
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\nWelcome to Khartoum University Management System");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("Thank you for using the system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.login(username, password)) {
                currentUser = user;
                System.out.println("Login successful!");
                return;
            }
        }
        System.out.println("Invalid username or password. Please try again.");
    }

    private static void handleUserChoice() {
        try {
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine().trim());

            if (currentUser instanceof Student) {
                handleStudentChoice((Student) currentUser, choice);
            } else if (currentUser instanceof Faculty) {
                handleFacultyChoice((Faculty) currentUser, choice);
            } else if (currentUser instanceof SystemAdmin) {
                handleAdminChoice((SystemAdmin) currentUser, choice);
            } else if (currentUser instanceof AdminStaff) {
                handleStaffChoice((AdminStaff) currentUser, choice);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void handleStudentChoice(Student student, int choice) {
        String[] options = {
            "Course Management",
            "Academic Records",
            "Attendance",
            "Profile Management",
            "Logout"
        };
        
        switch (choice) {
            case 1: handleCourseManagement(student); break;
            case 2: handleAcademicRecords(student); break;
            case 3: handleAttendance(student); break;
            case 4: handleProfileManagement(student); break;
            case 5: 
                currentUser.logout();
                currentUser = null;
                break;
        }
    }

    private static void handleCourseManagement(Student student) {
        String[] options = {
            "Register for Course",
            "Drop Course",
            "View Enrolled Courses",
            "View All Enrollments",
            "Back to Main Menu"
        };
        
        FileManager.displayMenu(options);
        int choice = FileManager.getMenuChoice("Enter your choice: ");
        
        switch (choice) {
            case 1:
                FileManager.displayAvailableCourses(university.getCourses());
                String courseId = FileManager.getStringInput("\nEnter course ID to register (or 'back' to return): ");
                if (!courseId.equalsIgnoreCase("back")) {
                    Course selectedCourse = findCourseById(courseId);
                    if (selectedCourse != null) {
                        student.registerForCourse(selectedCourse);
                        student.getGrades().add(0.0);
                        FileManager.saveEnrollmentsToFile(students);
                        System.out.println("Course registered successfully.");
                    } else {
                        System.out.println("Course not found.");
                    }
                }
                break;
            case 2:
                FileManager.displayEnrolledCourses(student);
                String dropCourseId = FileManager.getStringInput("\nEnter course ID to drop (or 'back' to return): ");
                if (!dropCourseId.equalsIgnoreCase("back")) {
                    Course dropCourse = findCourseById(dropCourseId);
                    if (dropCourse != null) {
                        student.dropCourse(dropCourse);
                        FileManager.saveEnrollmentsToFile(students);
                        System.out.println("Course dropped successfully.");
                    } else {
                        System.out.println("Course not found.");
                    }
                }
                break;
            case 3:
                FileManager.displayEnrolledCourses(student);
                break;
            case 4:
                List<String[]> enrollments = FileManager.loadEnrollmentsFromFile();
                System.out.println("\nAll Enrollments:");
                enrollments.forEach(enrollment -> 
                    System.out.println("Student ID: " + enrollment[0] + 
                                     ", Course ID: " + enrollment[1] + 
                                     ", Date: " + enrollment[2] + 
                                     ", Grade: " + enrollment[3]));
                break;
            case 5:
                return;
        }
    }

    private static void handleAcademicRecords(Student student) {
        String[] options = {
            "View Grades",
            "View GPA",
            "View Academic Status",
            "Back to Main Menu"
        };
        
        FileManager.displayMenu(options);
        int choice = FileManager.getMenuChoice("Enter your choice: ");
        
        switch (choice) {
            case 1: student.viewGrades(); break;
            case 2: System.out.println("Your GPA: " + student.calculateGPA()); break;
            case 3: System.out.println("Your Academic Status: " + student.getAcademicStatus()); break;
            case 4: return;
        }
    }

    private static void handleAttendance(Student student) {
        String[] options = {
            "Mark Attendance",
            "View Attendance",
            "Back to Main Menu"
        };
        
        FileManager.displayMenu(options);
        int choice = FileManager.getMenuChoice("Enter your choice: ");
        
        switch (choice) {
            case 1:
                FileManager.displayEnrolledCourses(student);
                String markCourseId = FileManager.getStringInput("\nEnter course ID to mark attendance (or 'back' to return): ");
                if (!markCourseId.equalsIgnoreCase("back")) {
                    Course markCourse = findCourseById(markCourseId);
                    if (markCourse != null) {
                        boolean present = FileManager.getBooleanInput("Present? (yes/no): ");
                        student.markAttendance(markCourse, present);
                        students = users.stream()
                            .filter(user -> user instanceof Student)
                            .map(user -> (Student) user)
                            .collect(java.util.stream.Collectors.toList());
                        FileManager.saveEnrollments(students);
                    } else {
                        System.out.println("Course not found.");
                    }
                }
                break;
            case 2:
                FileManager.displayEnrolledCourses(student);
                String viewCourseId = FileManager.getStringInput("\nEnter course ID to view attendance (or 'back' to return): ");
                if (!viewCourseId.equalsIgnoreCase("back")) {
                    Course viewCourse = findCourseById(viewCourseId);
                    if (viewCourse != null) {
                        student.viewAttendance(viewCourse);
                    } else {
                        System.out.println("Course not found.");
                    }
                }
                break;
            case 3:
                return;
        }
    }

    private static void handleProfileManagement(Student student) {
        String[] options = {
            "Update Name",
            "Update Email",
            "Update Contact Info",
            "Update Password",
            "Back to Main Menu"
        };
        
        FileManager.displayMenu(options);
        int choice = FileManager.getMenuChoice("Enter your choice: ");
        
        switch (choice) {
            case 1:
                String newName = FileManager.getStringInput("Enter new name: ");
                student.setName(newName);
                users = users.stream()
                    .map(u -> u.getUserId().equals(student.getUserId()) ? student : u)
                    .collect(java.util.stream.Collectors.toList());
                FileManager.saveUsers(users);
                System.out.println("Name updated successfully.");
                break;
            case 2:
                String newEmail = FileManager.getStringInput("Enter new email: ");
                student.setEmail(newEmail);
                users = users.stream()
                    .map(u -> u.getUserId().equals(student.getUserId()) ? student : u)
                    .collect(java.util.stream.Collectors.toList());
                FileManager.saveUsers(users);
                System.out.println("Email updated successfully.");
                break;
            case 3:
                String newContact = FileManager.getStringInput("Enter new contact info: ");
                student.setContactInfo(newContact);
                users = users.stream()
                    .map(u -> u.getUserId().equals(student.getUserId()) ? student : u)
                    .collect(java.util.stream.Collectors.toList());
                FileManager.saveUsers(users);
                System.out.println("Contact info updated successfully.");
                break;
            case 4:
                String newPassword = FileManager.getStringInput("Enter new password: ");
                student.setPassword(newPassword);
                users = users.stream()
                    .map(u -> u.getUserId().equals(student.getUserId()) ? student : u)
                    .collect(java.util.stream.Collectors.toList());
                FileManager.saveUsers(users);
                System.out.println("Password updated successfully.");
                break;
            case 5:
                return;
        }
    }

    private static void handleFacultyChoice(Faculty faculty, int choice) {
        String[] options = {
            "View Teaching Schedule",
            "Manage Course",
            "Update Office Hours",
            "Update Profile",
            "Logout"
        };
        
        switch (choice) {
            case 1:
                System.out.println("\nTeaching Schedule:");
                faculty.getCoursesTeaching().forEach(course -> {
                    System.out.println("\nCourse: " + course.getTitle());
                    System.out.println("Schedule: " + course.getSchedule());
                    System.out.println("Location: " + course.getLocation());
                    System.out.println("Enrolled Students: " + course.getEnrolledStudents().size());
                });
                break;
            case 2:
                if (!faculty.getCoursesTeaching().isEmpty()) {
                    FileManager.displayFacultyCourses(faculty);
                    int courseNum = FileManager.getMenuChoice("Enter course number: ");
                    if (courseNum > 0 && courseNum <= faculty.getCoursesTeaching().size()) {
                        faculty.manageCourse(faculty.getCoursesTeaching().get(courseNum - 1));
                    } else {
                        System.out.println("Invalid course number.");
                    }
                } else {
                    System.out.println("No courses assigned.");
                }
                break;
            case 3:
                String newOfficeHours = FileManager.getStringInput("Enter new office hours: ");
                faculty.setOfficeHours(newOfficeHours);
                updateUserInList(faculty);
                System.out.println("Office hours updated successfully.");
                break;
            case 4:
                handleFacultyProfileManagement(faculty);
                break;
            case 5:
                currentUser.logout();
                currentUser = null;
                break;
        }
    }

    private static void handleFacultyProfileManagement(Faculty faculty) {
        String[] options = {
            "Update Name",
            "Update Email",
            "Update Contact Info",
            "Update Office Hours",
            "Back to Main Menu"
        };
        
        FileManager.displayMenu(options);
        int choice = FileManager.getMenuChoice("Enter your choice: ");
        
        switch (choice) {
            case 1:
                String newName = FileManager.getStringInput("Enter new name: ");
                faculty.setName(newName);
                updateUserInList(faculty);
                System.out.println("Name updated successfully.");
                break;
            case 2:
                String newEmail = FileManager.getStringInput("Enter new email: ");
                faculty.setEmail(newEmail);
                updateUserInList(faculty);
                System.out.println("Email updated successfully.");
                break;
            case 3:
                String newContact = FileManager.getStringInput("Enter new contact info: ");
                faculty.setContactInfo(newContact);
                updateUserInList(faculty);
                System.out.println("Contact info updated successfully.");
                break;
            case 4:
                String newOfficeHours = FileManager.getStringInput("Enter new office hours: ");
                faculty.setOfficeHours(newOfficeHours);
                updateUserInList(faculty);
                System.out.println("Office hours updated successfully.");
                break;
            case 5:
                return;
        }
    }

    private static void handleAdminChoice(SystemAdmin admin, int choice) {
        switch (choice) {
            case 1: createNewUser(); break;
            case 2: createNewCourse(); break;
            case 3: admin.backupData(); break;
            case 4: admin.managePermissions(); break;
            case 5: handleFacultyManagement(admin); break;
            case 6: handleAdminProfileManagement(admin); break;
            case 7:
                currentUser.logout();
                currentUser = null;
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void handleFacultyManagement(SystemAdmin admin) {
        String[] options = {
            "Assign Faculty to Course",
            "View Faculty Performance",
            "Manage Department Structure",
            "Back to Main Menu"
        };
        
        FileManager.displayMenu(options);
        int choice = FileManager.getMenuChoice("Enter your choice: ");
        
        switch (choice) {
            case 1:
                System.out.println("\nAvailable Faculty:");
                users.stream()
                    .filter(user -> user instanceof Faculty)
                    .map(user -> (Faculty) user)
                    .forEach(faculty -> System.out.println(faculty.getFacultyId() + " - " + faculty.getName()));
                
                FileManager.displayAvailableCourses(university.getCourses());
                
                String facultyId = FileManager.getStringInput("\nEnter faculty ID: ");
                String courseId = FileManager.getStringInput("Enter course ID: ");
                
                Faculty selectedFaculty = (Faculty) users.stream()
                    .filter(user -> user instanceof Faculty && ((Faculty)user).getFacultyId().equals(facultyId))
                    .findFirst()
                    .orElse(null);
                    
                Course selectedCourse = findCourseById(courseId);
                
                if (selectedFaculty != null && selectedCourse != null) {
                    selectedFaculty.assignCourse(selectedCourse);
                    updateUserInList(selectedFaculty);
                    System.out.println("Faculty assigned to course successfully.");
                } else {
                    System.out.println("Faculty or course not found.");
                }
                break;
                
            case 2:
                System.out.println("\nFaculty Performance:");
                users.stream()
                    .filter(user -> user instanceof Faculty)
                    .map(user -> (Faculty) user)
                    .forEach(faculty -> {
                        System.out.println("\nFaculty: " + faculty.getName());
                        System.out.println("Courses Teaching: " + faculty.getCoursesTeaching().size());
                        System.out.println("Department: " + faculty.getDepartment());
                        System.out.println("Specialization: " + faculty.getSpecialization());
                    });
                break;
                
            case 3:
                System.out.println("\nDepartment Structure:");
                university.getDepartments().forEach(dept -> {
                    System.out.println("\nDepartment: " + dept.getName());
                    System.out.println("Head: " + dept.getHeadOfDepartment());
                    System.out.println("Faculty Count: " + dept.getFaculty().size());
                    System.out.println("Courses Offered: " + dept.getOfferedCourses().size());
                });
                break;
                
            case 4:
                return;
        }
    }

    private static void handleAdminProfileManagement(SystemAdmin admin) {
        String[] options = {
            "Update Name",
            "Update Email",
            "Update Contact Info",
            "Update Access Level",
            "Back to Main Menu"
        };
        
        FileManager.displayMenu(options);
        int choice = FileManager.getMenuChoice("Enter your choice: ");
        
        switch (choice) {
            case 1:
                String newName = FileManager.getStringInput("Enter new name: ");
                admin.setName(newName);
                updateUserInList(admin);
                System.out.println("Name updated successfully.");
                break;
            case 2:
                String newEmail = FileManager.getStringInput("Enter new email: ");
                admin.setEmail(newEmail);
                updateUserInList(admin);
                System.out.println("Email updated successfully.");
                break;
            case 3:
                String newContact = FileManager.getStringInput("Enter new contact info: ");
                admin.setContactInfo(newContact);
                updateUserInList(admin);
                System.out.println("Contact info updated successfully.");
                break;
            case 4:
                int newLevel = FileManager.getMenuChoice("Enter new access level (1-3): ");
                if (newLevel >= 1 && newLevel <= 3) {
                    admin.setAccessLevel(newLevel);
                    updateUserInList(admin);
                    System.out.println("Access level updated successfully.");
                } else {
                    System.out.println("Invalid access level. Must be between 1 and 3.");
                }
                break;
            case 5:
                return;
        }
    }

    private static void handleStaffChoice(AdminStaff staff, int choice) {
        String[] options = {
            "Manage Responsibilities",
            "Process Student Registration",
            "Process Course Registration",
            "Generate Reports",
            "Update Profile",
            "Logout"
        };
        
        switch (choice) {
            case 1:
                System.out.println("\nYour Responsibilities:");
                staff.getResponsibilities().forEach(resp -> System.out.println("- " + resp));
                if (FileManager.getBooleanInput("\nAdd new responsibility? (yes/no)")) {
                    staff.addResponsibility(FileManager.getStringInput("Enter new responsibility: "));
                }
                break;
            case 2:
                FileManager.displayAvailableStudents(users);
                String studentId = FileManager.getStringInput("\nEnter student ID to process registration (or 'back' to return): ");
                if (!studentId.equalsIgnoreCase("back")) {
                    Student selectedStudent = findStudentById(studentId);
                    if (selectedStudent != null) {
                        staff.processStudentRegistration(selectedStudent);
                    } else {
                        System.out.println("Student not found.");
                    }
                }
                break;
            case 3:
                FileManager.displayAvailableCourses(university.getCourses());
                String courseId = FileManager.getStringInput("\nEnter course ID to process registration (or 'back' to return): ");
                if (!courseId.equalsIgnoreCase("back")) {
                    Course selectedCourse = findCourseById(courseId);
                    if (selectedCourse != null) {
                        Student regStudent = findStudentById(FileManager.getStringInput("Enter student ID: "));
                        if (regStudent != null) {
                            staff.processCourseRegistration(regStudent, selectedCourse);
                        } else {
                            System.out.println("Student not found.");
                        }
                    } else {
                        System.out.println("Course not found.");
                    }
                }
                break;
            case 4:
                String[] reportOptions = {
                    "Student Registration Report",
                    "Course Enrollment Report",
                    "Department Statistics"
                };
                FileManager.displayMenu(reportOptions);
                int reportChoice = FileManager.getMenuChoice("Enter your choice: ");
                staff.generateReport(String.valueOf(reportChoice));
                break;
            case 5:
                staff.updateProfile();
                break;
            case 6:
                currentUser.logout();
                currentUser = null;
                break;
        }
    }

    private static Course findCourseById(String courseId) {
        return university.getCourses().stream()
                .filter(course -> course.getCourseId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    private static Student findStudentById(String studentId) {
        return users.stream()
                .filter(user -> user instanceof Student)
                .map(user -> (Student) user)
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    private static void createNewUser() {
        String[] userOptions = {
            "Create Student",
            "Create Faculty",
            "Create Admin Staff",
            "Back to Main Menu"
        };
        FileManager.displayMenu(userOptions);
        int choice = FileManager.getMenuChoice("Enter your choice: ");
        
        if (choice == 4) return;

        String userId = FileManager.getStringInput("Enter user ID: ");
        String username = FileManager.getStringInput("Enter username: ");
        String password = FileManager.getStringInput("Enter password: ");
        String name = FileManager.getStringInput("Enter name: ");
        String email = FileManager.getStringInput("Enter email: ");
        String contactInfo = FileManager.getStringInput("Enter contact info: ");

        User newUser = null;
        switch (choice) {
            case 1: 
                String studentId = FileManager.getStringInput("Enter student ID: ");
                String major = FileManager.getStringInput("Enter major: ");
                newUser = new Student(userId, username, password, name, email, contactInfo, studentId, "2024-01-01");
                ((Student)newUser).setDepartment(major);
                List<Student> students = new ArrayList<>();
                students.add((Student)newUser);
                FileManager.saveStudents(students);
                break;
            case 2: 
                String facultyId = FileManager.getStringInput("Enter faculty ID: ");
                String department = FileManager.getStringInput("Enter department: ");
                String specialization = FileManager.getStringInput("Enter specialization: ");
                newUser = new Faculty(userId, username, password, name, email, contactInfo, facultyId, department, specialization);
                break;
            case 3: 
                String staffId = FileManager.getStringInput("Enter staff ID: ");
                String role = FileManager.getStringInput("Enter role: ");
                newUser = new AdminStaff(userId, username, password, name, email, contactInfo, staffId, role);
                break;
        }

        if (newUser != null) {
            university.getUsers().add(newUser);
            FileManager.saveUsers(university.getUsers());
            System.out.println("User created successfully.");
        }
    }

    private static void createNewCourse() {
        String[] courseOptions = {
            "Create Lecture Course",
            "Create Lab Course",
            "Create Seminar Course",
            "Back to Main Menu"
        };
        FileManager.displayMenu(courseOptions);
        int choice = FileManager.getMenuChoice("Enter your choice: ");
        
        if (choice == 4) return;

        String courseId = FileManager.getStringInput("Enter course ID: ");
        String title = FileManager.getStringInput("Enter course title: ");
        String description = FileManager.getStringInput("Enter course description: ");
        int creditHours = FileManager.getMenuChoice("Enter credit hours: ");
        int availableSeats = FileManager.getMenuChoice("Enter available seats: ");
        String schedule = FileManager.getStringInput("Enter schedule: ");
        String location = FileManager.getStringInput("Enter location: ");

        Course course = null;
        switch (choice) {
            case 1: 
                String lectureFormat = FileManager.getStringInput("Enter lecture format (e.g., Traditional, Interactive, Flipped): ");
                int maxClassSize = FileManager.getMenuChoice("Enter maximum class size: ");
                course = new LectureCourse(courseId, title, description, creditHours, 
                                        availableSeats, schedule, location, lectureFormat, maxClassSize);
                break;
            case 2: 
                String equipment = FileManager.getStringInput("Enter equipment required: ");
                int labCapacity = FileManager.getMenuChoice("Enter lab capacity: ");
                course = new LabCourse(courseId, title, description, creditHours, 
                                     availableSeats, schedule, location, equipment, labCapacity);
                break;
            case 3: 
                String mainTopic = FileManager.getStringInput("Enter main topic: ");
                int maxParticipants = FileManager.getMenuChoice("Enter maximum participants: ");
                boolean requiresPresentation = FileManager.getBooleanInput("Does this course require presentation? (yes/no): ");
                course = new SeminarCourse(courseId, title, description, creditHours, 
                                         availableSeats, schedule, location, mainTopic, 
                                         maxParticipants, requiresPresentation);
                break;
        }

        if (course != null) {
            university.offerCourse(course);
            FileManager.saveCourses(university.getCourses());
            System.out.println("Course created successfully.");
        }
    }

    private static void updateUserInList(User updatedUser) {
        users = users.stream()
            .map(u -> u.getUserId().equals(updatedUser.getUserId()) ? updatedUser : u)
            .collect(java.util.stream.Collectors.toList());
        FileManager.saveUsers(users);
    }
}