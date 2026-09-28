import java.io.*;
import java.util.*;

class Student {
    private int id;
    private String name;
    private String course;
    private int age;

    public Student(int id, String name, String course, int age) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               " | Name: " + name +
               " | Course: " + course +
               " | Age: " + age;
    }

    public String toFileFormat() {
        return id + "," + name + "," + course + "," + age;
    }
}

public class StudentManagementSystem {

    private static final String FILE_NAME = "students.txt";
    private static final ArrayList<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        loadStudents();

        while (true) {
            System.out.println("\n==================================");
            System.out.println("     STUDENT MANAGEMENT SYSTEM");
            System.out.println("==================================");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.println("==================================");

            System.out.print("Enter your choice: ");
            int choice = getIntegerInput();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    updateStudent();
                    break;

                case 4:
                    deleteStudent();
                    break;

                case 5:
                    saveStudents();
                    System.out.println("Thank you for using Student Management System!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Add Student
    private static void addStudent() {

        System.out.print("Enter Student ID: ");
        int id = getIntegerInput();

        if (findStudent(id) != null) {
            System.out.println("Student ID already exists!");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Course: ");
        String course = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = getIntegerInput();

        Student student = new Student(id, name, course, age);
        students.add(student);

        saveStudents();

        System.out.println("Student added successfully!");
    }

    // View Students
    private static void viewStudents() {

        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n----------- STUDENT RECORDS -----------");

        for (Student student : students) {
            System.out.println(student);
        }

        System.out.println("---------------------------------------");
    }

    // Update Student
    private static void updateStudent() {

        System.out.print("Enter Student ID to update: ");
        int id = getIntegerInput();

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new course: ");
        String course = scanner.nextLine();

        System.out.print("Enter new age: ");
        int age = getIntegerInput();

        student.setName(name);
        student.setCourse(course);
        student.setAge(age);

        saveStudents();

        System.out.println("Student updated successfully!");
    }

    // Delete Student
    private static void deleteStudent() {

        System.out.print("Enter Student ID to delete: ");
        int id = getIntegerInput();

        Student student = findStudent(id);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        students.remove(student);

        saveStudents();

        System.out.println("Student deleted successfully!");
    }

    // Find Student
    private static Student findStudent(int id) {

        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }

        return null;
    }

    // Save data to file
    private static void saveStudents() {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Student student : students) {
                writer.write(student.toFileFormat());
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving student data: " + e.getMessage());
        }
    }

    // Load data from file
    private static void loadStudents() {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 4) {

                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    String course = data[2];
                    int age = Integer.parseInt(data[3]);

                    students.add(
                        new Student(id, name, course, age)
                    );
                }
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading student data.");
        }
    }

    // Integer input validation
    private static int getIntegerInput() {

        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;

            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}