import java.util.Scanner;

public class Student extends Person {
    private int rollNo;
    private String course;
    private double marks;
    private char grade;

    public Student() {
        super();
        this.rollNo = 0;
        this.course = "";
        this.marks = 0.0;
        this.grade = 'F';
    }

    public Student(int rollNo, String name, String course, double marks) {
        super(name);
        this.rollNo = rollNo;
        this.course = course;
        this.marks = marks;
        this.grade = calculateGrade();
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
        this.grade = calculateGrade();
    }

    public char getGrade() {
        return grade;
    }
    
    public void inputDetails() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Roll No: ");
        this.rollNo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Name: ");
        this.name = scanner.nextLine();

        System.out.print("Enter Course: ");
        this.course = scanner.nextLine();

        System.out.print("Enter Marks: ");
        this.marks = scanner.nextDouble();

        while (marks < 0 || marks > 100) {
            System.out.print("Invalid marks! Please enter marks between 0 and 100: ");
            this.marks = scanner.nextDouble();

            scanner.close();
        }

        this.grade = calculateGrade();
    }

    public char calculateGrade() {
        if (marks >= 90) {
            return 'A';
        } else if (marks >= 80) {
            return 'B';
        } else if (marks >= 70) {
            return 'C';
        } else if (marks >= 60) {
            return 'D';
        } else {
            return 'F';
        }
    }

    @Override
    public void displayDetails() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Course: " + course);
        System.out.println("Marks: " + marks);
        System.out.println("Grade: " + grade);
        System.out.println("------------------------");
    }
    
    public String getPerformanceStatus() {
        switch (grade) {
            case 'A': return "Excellent";
            case 'B': return "Good";
            case 'C': return "Average";
            case 'D': return "Below Average";
            case 'F': return "Fail";
            default: return "Unknown";
        }
    }

    @Override
    public String toString() {
        return String.format("Student{rollNo=%d, name='%s', course='%s', marks=%.1f, grade=%c}",
                           rollNo, name, course, marks, grade);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return rollNo == student.rollNo;
    }
    
}
