import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {
    private static final String USERS_FILE = "users.txt";
    private static final String STUDENTS_FILE = "students.txt";
    private static final String COURSES_FILE = "courses.txt";
    private static final String ENROLLMENTS_FILE = "enrollments.txt";
    private static final String DEPARTMENTS_FILE = "departments.txt";

    private static String getFilePath(String filename) {
        return filename; 
    }

    public static void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {  
            for (User user : users) {
                StringBuilder line = new StringBuilder();
                line.append(user.getUserId()).append(",")
                    .append(user.getUsername()).append(",")
                    .append(user.getPassword()).append(",")
                    .append(user.getName()).append(",")
                    .append(user.getEmail()).append(",")
                    .append(user.getContactInfo()).append(",");

                if (user instanceof SystemAdmin) {
                    SystemAdmin admin = (SystemAdmin) user;
                    line.append("SYSTEM_ADMIN,")
                        .append(admin.getAdminId()).append(",")
                        .append(admin.getAccessLevel());
                } else if (user instanceof Student) {
                    Student student = (Student) user;
                    line.append("STUDENT,")
                        .append(student.getStudentId()).append(",")
                        .append(student.getDepartment());
                } else if (user instanceof Faculty) {
                    Faculty faculty = (Faculty) user;
                    line.append("FACULTY,")
                        .append(faculty.getFacultyId()).append(",")
                        .append(faculty.getDepartment()).append(",")
                        .append(faculty.getSpecialization());
                } else if (user instanceof AdminStaff) {
                    AdminStaff staff = (AdminStaff) user;
                    line.append("ADMIN_STAFF,")
                        .append(staff.getStaffId()).append(",")
                        .append(staff.getRole());
                }

                writer.println(line.toString());
            }
            System.out.println("Users saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    public static void saveStudents(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath(STUDENTS_FILE), true))) {
            for (Student student : students) {
                writer.println(student.getStudentId() + "," + 
                             student.getName() + "," + 
                             student.getAdmissionDate() + "," + 
                             student.getAcademicStatus() + "," + 
                             student.getDepartment() + "," + 
                             student.calculateGPA());
            }
            System.out.println("Students saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

    public static void saveCourses(List<Course> courses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COURSES_FILE))) {
            for (Course course : courses) {
                StringBuilder line = new StringBuilder();
                line.append(course.getCourseId()).append(",")
                    .append(course.getTitle()).append(",")
                    .append(course.getDescription()).append(",")
                    .append(course.getCreditHours()).append(",")
                    .append(course.getAvailableSeats()).append(",")
                    .append(course.getSchedule()).append(",")
                    .append(course.getLocation()).append(",")
                    .append(course.getCourseType());

                if (course instanceof LectureCourse) {
                    LectureCourse lc = (LectureCourse) course;
                    line.append(",").append(lc.getLectureFormat())
                        .append(",").append(lc.getMaxClassSize());
                } else if (course instanceof LabCourse) {
                    LabCourse lc = (LabCourse) course;
                    line.append(",").append(lc.getEquipmentRequired())
                        .append(",").append(lc.getLabCapacity());
                } else if (course instanceof SeminarCourse) {
                    SeminarCourse sc = (SeminarCourse) course;
                    line.append(",").append(sc.getMainTopic())
                        .append(",").append(sc.getMaxParticipants())
                        .append(",").append(sc.isRequiresPresentation());
                }

                writer.println(line.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
        }
    }

    public static void saveEnrollments(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath(ENROLLMENTS_FILE)))) {
            for (Student student : students) {
                for (int i = 0; i < student.getEnrolledCourses().size(); i++) {
                    Course course = student.getEnrolledCourses().get(i);
                    double grade = student.getGrades().get(i);
                    writer.println(student.getStudentId() + "," + 
                                 course.getCourseId() + "," + 
                                 "2024-01-15" + "," + 
                                 grade);
                }
            }
            System.out.println("Enrollments saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving enrollments: " + e.getMessage());
        }
    }

    public static void saveEnrollmentsToFile(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ENROLLMENTS_FILE, true))) {
            for (Student student : students) {
                for (int i = 0; i < student.getEnrolledCourses().size(); i++) {
                    Course course = student.getEnrolledCourses().get(i);
                    double grade = student.getGrades().get(i);
                    writer.println(student.getStudentId() + "," + 
                                 course.getCourseId() + "," + 
                                 "2024-01-15" + "," + 
                                 grade);
                }
            }
            System.out.println("Enrollments saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving enrollments: " + e.getMessage());
        }
    }

    public static void saveDepartments(List<Department> departments) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(getFilePath(DEPARTMENTS_FILE)))) {
            for (Department dept : departments) {
                writer.println(dept.getDepartmentId() + "," + 
                             dept.getName() + "," + 
                             dept.getHeadOfDepartment() + "," + 
                             dept.getFaculty().size() + "," + 
                             dept.getOfferedCourses().size());
            }
            System.out.println("Departments saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving departments: " + e.getMessage());
        }
    }

    public static List<String[]> loadUsers() {
        List<String[]> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }

    public static List<String[]> loadStudents() {
        List<String[]> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(STUDENTS_FILE)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                students.add(line.split(","));
            }
            System.out.println("Students loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
        return students;
    }

    public static List<String[]> loadCourses() {
        List<String[]> courses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                courses.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error loading courses: " + e.getMessage());
        }
        return courses;
    }

    public static List<String[]> loadEnrollments() {
        List<String[]> enrollments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(ENROLLMENTS_FILE)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                enrollments.add(line.split(","));
            }
            System.out.println("Enrollments loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading enrollments: " + e.getMessage());
        }
        return enrollments;
    }

    public static List<String[]> loadDepartments() {
        List<String[]> departments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(DEPARTMENTS_FILE)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                departments.add(line.split(","));
            }
            System.out.println("Departments loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading departments: " + e.getMessage());
        }
        return departments;
    }

    public static List<String[]> loadEnrollmentsFromFile() {
        List<String[]> enrollments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ENROLLMENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                enrollments.add(line.split(","));
            }
            System.out.println("Enrollments loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading enrollments: " + e.getMessage());
        }
        return enrollments;
    }

    public static List<User> loadAllData(University university) {
        List<User> users = new ArrayList<>();
        
        List<String[]> userData = loadUsers();
        for (String[] data : userData) {
            try {
                String userId = data[0];
                String username = data[1];
                String password = data[2];
                String name = data[3];
                String email = data[4];
                String contactInfo = data[5];

                User user = createUser(userId, username, password, name, email, contactInfo, university);
                if (user != null) {
                    users.add(user);
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Error: User data is incomplete. Skipping invalid entry.");
            }
        }

        List<String[]> courseData = loadCourses();
        for (String[] data : courseData) {
            try {
                if (data.length < 8) {
                    System.out.println("Error: Course data is incomplete. Skipping line.");
                    continue;
                }
                String courseId = data[0];
                String title = data[1];
                String description = data[2];
                int creditHours = Integer.parseInt(data[3].trim());
                int availableSeats = Integer.parseInt(data[4].trim());
                String schedule = data[5];
                String location = data[6];
                String courseType = data[7];

                Course course = null;
                if (courseType.equalsIgnoreCase("LECTURE")) {
                    if (data.length < 10) {
                        System.out.println("Error: Lecture course data is incomplete. Skipping line.");
                        continue;
                    }
                    String lectureFormat = data[8];
                    int maxClassSize = Integer.parseInt(data[9].trim());
                    course = new LectureCourse(courseId, title, description, creditHours, 
                        availableSeats, schedule, location, lectureFormat, maxClassSize);
                } else if (courseType.equalsIgnoreCase("LAB")) {
                    if (data.length < 10) {
                        System.out.println("Error: Lab course data is incomplete. Skipping line.");
                        continue;
                    }
                    String equipmentRequired = data[8];
                    int labCapacity = Integer.parseInt(data[9].trim());
                    course = new LabCourse(courseId, title, description, creditHours, 
                        availableSeats, schedule, location, equipmentRequired, labCapacity);
                } else if (courseType.equalsIgnoreCase("SEMINAR")) {
                    if (data.length < 11) {
                        System.out.println("Error: Seminar course data is incomplete. Skipping line.");
                        continue;
                    }
                    String mainTopic = data[8];
                    int maxParticipants = Integer.parseInt(data[9].trim());
                    boolean requiresPresentation = Boolean.parseBoolean(data[10]);
                    course = new SeminarCourse(courseId, title, description, creditHours, 
                        availableSeats, schedule, location, mainTopic, maxParticipants, requiresPresentation);
                } else {
                    System.out.println("Unknown course type: " + courseType + ". Skipping course.");
                    continue;
                }
                university.offerCourse(course);
            } catch (Exception e) {
                System.out.println("Error loading course: " + e.getMessage());
            }
        }

        return users;
    }

    private static User createUser(String userId, String username, String password, String name, String email, String contactInfo, University university) {
        String[] data = loadUsers().stream()
            .filter(userData -> userData[0].equals(userId))
            .findFirst()
            .orElse(null);
            
        if (data == null) return null;
        
        String userType = data[6];
        switch (userType) {
            case "SYSTEM_ADMIN":
                return new SystemAdmin(userId, username, password, name, email, contactInfo, data[7], Integer.parseInt(data[8]), university);
            case "STUDENT":
                return new Student(userId, username, password, name, email, contactInfo, data[7], "2024-01-01");
            case "FACULTY":
                return new Faculty(userId, username, password, name, email, contactInfo, data[7], data[8], data[9]);
            case "ADMIN_STAFF":
                return new AdminStaff(userId, username, password, name, email, contactInfo, data[7], data[8]);
            default:
                return null;
        }
    }

    public static void displayMenu(String[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
    }

    public static int getMenuChoice(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice > 0) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static boolean getBooleanInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes") || input.equals("y")) {
                return true;
            } else if (input.equals("no") || input.equals("n")) {
                return false;
            }
            System.out.println("Please enter 'yes' or 'no'.");
        }
    }

    public static void displayAvailableCourses(List<Course> courses) {
        System.out.println("\nAvailable Courses:");
        System.out.println("ID\tTitle\t\t\tCredits\tAvailable Seats");
        System.out.println("------------------------------------------------");
        for (Course course : courses) {
            System.out.printf("%s\t%-20s\t%d\t%d\n",
                course.getCourseId(),
                course.getTitle(),
                course.getCreditHours(),
                course.getAvailableSeats());
        }
    }

    public static void displayEnrolledCourses(Student student) {
        System.out.println("\nEnrolled Courses:");
        System.out.println("ID\tTitle\t\t\tCredits\tGrade");
        System.out.println("------------------------------------------------");
        List<Course> courses = student.getEnrolledCourses();
        List<Double> grades = student.getGrades();
        for (int i = 0; i < courses.size(); i++) {
            System.out.printf("%s\t%-20s\t%d\t%.1f\n",
                courses.get(i).getCourseId(),
                courses.get(i).getTitle(),
                courses.get(i).getCreditHours(),
                grades.get(i));
        }
    }

    public static void displayAvailableStudents(List<User> users) {
        System.out.println("\nAvailable Students:");
        System.out.println("ID\tName\t\t\tDepartment");
        System.out.println("------------------------------------------------");
        users.stream()
            .filter(user -> user instanceof Student)
            .map(user -> (Student) user)
            .distinct()  
            .forEach(student -> System.out.printf("%s\t%-20s\t%s\n",
                student.getStudentId(),
                student.getName(),
                student.getDepartment()));
    }

    public static void displayFacultyCourses(Faculty faculty) {
        System.out.println("\nCourses Teaching:");
        System.out.println("ID\tTitle\t\t\tSchedule\t\tLocation");
        System.out.println("------------------------------------------------");
        for (Course course : faculty.getCoursesTeaching()) {
            System.out.printf("%s\t%-20s\t%s\t%s\n",
                course.getCourseId(),
                course.getTitle(),
                course.getSchedule(),
                course.getLocation());
        }
    }
} 