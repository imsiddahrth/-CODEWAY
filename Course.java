import java.util.ArrayList;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private int maxCapacity;
    private String schedule;

    public Course(String code, String title, String description, int maxCapacity, String schedule) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.capacity = maxCapacity;
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public void decrementCapacity() {
        if (capacity > 0) {
            capacity--;
        }
    }

    public void incrementCapacity() {
        if (capacity < maxCapacity) {
            capacity++;
        }
    }
}

class Student {
    private int studentId;
    private String name;
    private ArrayList<Course> registeredCourses;

    public Student(int studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Course> getRegisteredCourses() {
        return new ArrayList<>(registeredCourses); // Return a copy to avoid direct manipulation
    }

    public void registerForCourse(Course course) {
        if (course.getCapacity() > 0) {
            registeredCourses.add(course);
            course.decrementCapacity();
            System.out.println("Registration successful for course: " + course.getTitle());
        } else {
            System.out.println("Sorry, the course is full. Cannot register.");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            registeredCourses.remove(course);
            course.incrementCapacity();
            System.out.println("Course dropped: " + course.getTitle());
        } else {
            System.out.println("You are not registered for this course.");
        }
    }
}

class CourseRegistrationSystem {
    private ArrayList<Course> courses;
    private ArrayList<Student> students;

    public CourseRegistrationSystem() {
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void displayCourses() {
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println("Course Code: " + course.getCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Capacity: " + course.getCapacity() + "/" + course.getMaxCapacity());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println("-------------------------------");
        }
    }

    public void displayStudents() {
        System.out.println("\nRegistered Students:");
        for (Student student : students) {
            System.out.println("Student ID: " + student.getStudentId());
            System.out.println("Name: " + student.getName());
            System.out.println("Registered Courses: " + student.getRegisteredCourses().size());
            System.out.println("-------------------------------");
        }
    }

    public static void main(String[] args) {
        CourseRegistrationSystem system = new CourseRegistrationSystem();

        Course course1 = new Course("CSE101", "Introduction to Computer Science", "Basic concepts of programming", 50, "Mon-Wed-Fri 10:00 AM");
        Course course2 = new Course("MAT201", "Calculus", "Limits, derivatives, and integrals", 40, "Tue-Thu 2:00 PM");
        Course course3 = new Course("ENG101", "English Composition", "Writing and communication skills", 30, "Mon-Wed 1:00 PM");

        Student student1 = new Student(1001, "John Doe");
        Student student2 = new Student(1002, "Jane Smith");

        system.addCourse(course1);
        system.addCourse(course2);
        system.addCourse(course3);

        system.addStudent(student1);
        system.addStudent(student2);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Display Courses");
            System.out.println("2. Display Students");
            System.out.println("3. Register for Course");
            System.out.println("4. Drop Course");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    system.displayCourses();
                    break;
                case 2:
                    system.displayStudents();
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    int studentId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter course code to register: ");
                    String courseCode = scanner.nextLine();
                    Student student = system.getStudentById(studentId);
                    Course course = system.getCourseByCode(courseCode);
                    if (student != null && course != null) {
                        student.registerForCourse(course);
                    } else {
                        System.out.println("Invalid student ID or course code.");
                    }
                    break;
                case 4:
                    System.out.print("Enter student ID: ");
                    int studentIdDrop = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    System.out.print("Enter course code to drop: ");
                    String courseCodeDrop = scanner.nextLine();
                    Student studentDrop = system.getStudentById(studentIdDrop);
                    Course courseDrop = system.getCourseByCode(courseCodeDrop);
                    if (studentDrop != null && courseDrop != null) {
                        studentDrop.dropCourse(courseDrop);
                    } else {
                        System.out.println("Invalid student ID or course code.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting the system. Thank you!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private Student getStudentById(int studentId) {
        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null;
    }

    private Course getCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
}
