import java.util.*;

public class StudentRecordManager {
    private ArrayList<Student> students;
    private Scanner scanner;

    public StudentRecordManager() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addStudent() {
        Student student = new Student();

        try {
            student.inputDetails();

            if (findStudentByRollNo(student.getRollNo()) != null) {
                System.out.println("Error: Student with Roll No " + student.getRollNo() + " already exists!");
                return;
            }

            students.add(student);
            System.out.println("Student added successfully!");

        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input format!");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found!");
            return;
        }

        System.out.println("\n===== All Student Records =====");
        for (Student student : students) {
            student.displayDetails();
        }
    }
    
    public void searchStudent() {
        if (students.isEmpty()) {
            System.out.println("No student records found!");
            return;
        }

        System.out.print("Enter Roll No to search: ");
        try {
            int rollNo = scanner.nextInt();
            Student student = findStudentByRollNo(rollNo);

            if (student != null) {
                System.out.println("\n===== Student Found =====");
                student.displayDetails();
            } else {
                System.out.println("Student with Roll No " + rollNo + " not found!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid roll number format!");
            scanner.nextLine();
        }
    }

    public void updateStudentMarks() {
        if (students.isEmpty()) {
            System.out.println("No student records found!");
            return;
        }

        System.out.print("Enter Roll No to update marks: ");
        try {
            int rollNo = scanner.nextInt();
            Student student = findStudentByRollNo(rollNo);

            if (student != null) {
                System.out.print("Enter new marks: ");
                double newMarks = scanner.nextDouble();

                while (newMarks < 0 || newMarks > 100) {
                    System.out.print("Invalid marks! Please enter marks between 0 and 100: ");
                    newMarks = scanner.nextDouble();
                }

                student.setMarks(newMarks);
                System.out.println("Marks updated successfully!");
                System.out.println("New Grade: " + student.getGrade());
            } else {
                System.out.println("Student with Roll No " + rollNo + " not found!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid input format!");
            scanner.nextLine();
        }
    }
    
    public void deleteStudent() {
        if (students.isEmpty()) {
            System.out.println("No student records found!");
            return;
        }

        System.out.print("Enter Roll No to delete: ");
        try {
            int rollNo = scanner.nextInt();
            Student student = findStudentByRollNo(rollNo);

            if (student != null) {
                students.remove(student);
                System.out.println("Student record deleted successfully!");
            } else {
                System.out.println("Student with Roll No " + rollNo + " not found!");
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid roll number format!");
            scanner.nextLine();
        }
    }

    public void displayStatistics() {
        if (students.isEmpty()) {
            System.out.println("No student records found!");
            return;
        }

        double totalMarks = 0;
        double highestMarks = Double.MIN_VALUE;
        double lowestMarks = Double.MAX_VALUE;
        Map<Character, Integer> gradeCount = new HashMap<>();

        for (Student student : students) {
            double marks = student.getMarks();
            totalMarks += marks;

            if (marks > highestMarks) highestMarks = marks;
            if (marks < lowestMarks) lowestMarks = marks;

            char grade = student.getGrade();
            gradeCount.put(grade, gradeCount.getOrDefault(grade, 0) + 1);
        }

        double averageMarks = totalMarks / students.size();

        System.out.println("\n===== Class Statistics =====");
        System.out.println("Total Students: " + students.size());
        System.out.printf("Average Marks: %.2f%n", averageMarks);
        System.out.printf("Highest Marks: %.2f%n", highestMarks);
        System.out.printf("Lowest Marks: %.2f%n", lowestMarks);

        System.out.println("\nGrade Distribution:");
        for (Map.Entry<Character, Integer> entry : gradeCount.entrySet()) {
            System.out.println("Grade " + entry.getKey() + ": " + entry.getValue() + " students");
        }
    }
    
    public void sortStudentsByMarks() {
        if (students.isEmpty()) {
            System.out.println("No student records found!");
            return;
        }

        students.sort((s1, s2) -> Double.compare(s2.getMarks(), s1.getMarks()));
        System.out.println("Students sorted by marks (highest to lowest):");
        displayAllStudents();
    }

    private Student findStudentByRollNo(int rollNo) {
        for (Student student : students) {
            if (student.getRollNo() == rollNo) {
                return student;
            }
        }
        return null;
    }

    public void exportToCSV() {
        if (students.isEmpty()) {
            System.out.println("No student records to export!");
            return;
        }

        try {
            java.io.FileWriter writer = new java.io.FileWriter("student_records.csv");
            writer.write("Roll No,Name,Course,Marks,Grade\n");

            for (Student student : students) {
                writer.write(String.format("%d,%s,%s,%.1f,%c\n",
                    student.getRollNo(), student.getName(), student.getCourse(),
                    student.getMarks(), student.getGrade()));
            }

            writer.close();
            System.out.println("Student records exported to student_records.csv successfully!");
        } catch (java.io.IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }

    public int getTotalStudents() {
        return students.size();
    }
}
