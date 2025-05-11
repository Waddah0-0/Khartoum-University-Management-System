# Khartoum University Management System

A comprehensive university management system that handles various aspects of university operations including student management, course management, faculty management, and administrative tasks.

## Project Structure

The project is organized into several key components:

### Core Classes

1. **Main.java**
   - Entry point of the application
   - Handles user authentication and menu navigation
   - Manages different user roles and their respective functionalities
   - Key methods:
     - `main()`: Application entry point
     - `showLoginMenu()`: Displays login options
     - `login()`: Handles user authentication
     - `handleUserChoice()`: Processes user menu selections
     - `handleStudentChoice()`: Manages student-specific operations
     - `handleFacultyChoice()`: Manages faculty-specific operations
     - `handleAdminChoice()`: Manages admin-specific operations
     - `handleStaffChoice()`: Manages staff-specific operations

2. **University.java**
   - Represents the university entity
   - Manages departments and overall university operations
   - Key methods:
     - `createDepartment()`: Adds new departments
     - `getDepartments()`: Retrieves department list
     - `getCourses()`: Retrieves all courses
     - `addCourse()`: Adds new courses
     - `removeCourse()`: Removes existing courses

3. **Department.java**
   - Represents academic departments
   - Manages courses and faculty within the department
   - Key methods:
     - `addCourse()`: Adds courses to department
     - `removeCourse()`: Removes courses from department
     - `getFaculty()`: Retrieves department faculty
     - `addFaculty()`: Adds faculty members
     - `removeFaculty()`: Removes faculty members

4. **User.java**
   - Base class for all users in the system
   - Handles authentication and basic user operations
   - Key methods:
     - `login()`: Authenticates users
     - `logout()`: Handles user logout
     - `getUsername()`: Retrieves username
     - `getPassword()`: Retrieves password
     - `displayMenu()`: Shows user-specific menu

### User Types

1. **Student.java**
   - Manages student-specific operations
   - Handles course registration, grades, and academic records
   - Key methods:
     - `registerForCourse()`: Enrolls in courses
     - `dropCourse()`: Removes course enrollment
     - `calculateGPA()`: Computes student's GPA
     - `getAcademicStatus()`: Retrieves academic standing
     - `viewGrades()`: Displays course grades
     - `markAttendance()`: Records attendance
     - `viewAttendance()`: Shows attendance records

2. **Faculty.java**
   - Manages faculty-specific operations
   - Handles course management and student evaluation
   - Key methods:
     - `assignGrade()`: Submits student grades
     - `viewStudents()`: Lists enrolled students
     - `manageCourse()`: Handles course administration
     - `updateProfile()`: Modifies faculty information
     - `viewTeachingSchedule()`: Shows course schedule

3. **SystemAdmin.java**
   - Handles system-wide administrative tasks
   - Key methods:
     - `createUser()`: Adds new users
     - `deleteUser()`: Removes users
     - `manageDepartments()`: Handles department operations
     - `configureSystem()`: Sets system parameters
     - `viewSystemLogs()`: Monitors system activity
     - `backupData()`: Creates system backups

4. **AdminStaff.java**
   - Handles administrative staff operations
   - Key methods:
     - `manageStudentRecords()`: Updates student information
     - `scheduleCourses()`: Arranges course timings
     - `processEnrollments()`: Handles course registration
     - `generateReports()`: Creates administrative reports
     - `manageAttendance()`: Tracks student attendance

### Course Management

1. **Course.java**
   - Base class for all course types
   - Manages course information and enrollment
   - Key methods:
     - `enrollStudent()`: Adds students to course
     - `removeStudent()`: Removes students from course
     - `getEnrolledStudents()`: Lists enrolled students
     - `setGrade()`: Assigns student grades
     - `getCourseInfo()`: Retrieves course details

2. **LectureCourse.java**
   - Represents lecture-based courses
   - Manages lecture-specific attributes
   - Key methods:
     - `scheduleLecture()`: Arranges lecture times
     - `assignLectureHall()`: Allocates lecture rooms
     - `manageLectureMaterials()`: Handles course materials

3. **LabCourse.java**
   - Represents laboratory-based courses
   - Manages lab-specific attributes
   - Key methods:
     - `scheduleLab()`: Arranges lab sessions
     - `assignLabRoom()`: Allocates lab facilities
     - `manageLabEquipment()`: Handles lab resources

4. **SeminarCourse.java**
   - Represents seminar-based courses
   - Manages seminar-specific attributes
   - Key methods:
     - `scheduleSeminar()`: Arranges seminar sessions
     - `assignSeminarRoom()`: Allocates seminar rooms
     - `managePresentations()`: Handles student presentations

### Data Management

1. **FileManager.java**
   - Handles all file operations
   - Manages data persistence
   - Key methods:
     - `loadAllData()`: Loads system data
     - `saveAllData()`: Saves system data
     - `loadUsers()`: Retrieves user information
     - `saveUsers()`: Stores user information
     - `loadCourses()`: Retrieves course data
     - `saveCourses()`: Stores course data
     - `loadEnrollments()`: Retrieves enrollment data
     - `saveEnrollments()`: Stores enrollment data
     - `createBackup()`: Generates system backup
     - `restoreBackup()`: Restores system from backup

## Data Files

The system uses several text files for data persistence:

- `users.txt`: Stores user information
  - Contains user credentials, roles, and personal information
  - Format: username,password,role,personal_info

- `courses.txt`: Stores course information
  - Contains course details, prerequisites, and capacity
  - Format: course_id,name,department,credits,capacity

- `departments.txt`: Stores department information
  - Contains department details and faculty assignments
  - Format: dept_id,name,head_of_department

- `students.txt`: Stores student-specific information
  - Contains academic records and enrollment status
  - Format: student_id,name,department,enrollment_status

- `enrollments.txt`: Stores course enrollment information
  - Contains student-course relationships and grades
  - Format: student_id,course_id,enrollment_date,grade

## Features

### Student Features
- Course registration and dropping
- Grade viewing and GPA calculation
- Academic status tracking
- Attendance management
- Profile management
- Course schedule viewing
- Academic progress tracking

### Faculty Features
- Course management
- Student evaluation
- Grade submission
- Profile management
- Course material management
- Student performance tracking
- Teaching schedule management

### Administrative Features
- User management
- Department management
- Course management
- System configuration
- Student record management
- Course scheduling
- Report generation
- System monitoring

### System Features
- User authentication
- Role-based access control
- Data persistence
- Backup and restore functionality
- Error logging
- System monitoring
- Data validation

## Getting Started

1. Ensure you have Java installed on your system
2. Compile all Java files
3. Run the Main class to start the application
4. Use the login menu to access the system

## Security

- The system implements role-based access control
- User authentication is required for all operations
- Sensitive data is stored securely
- Regular backups are maintained
- Password encryption
- Session management
- Access logging

## Data Management

The system maintains data integrity through:
- Regular data backups
- File-based persistence
- Data validation
- Error handling
- Transaction management
- Data consistency checks
- Automatic backup scheduling

## Contributing

To contribute to this project:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 