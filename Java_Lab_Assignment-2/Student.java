import java.util.Scanner;

class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}

class StudentRecord {
    // this class ia wrapper
    Integer rollNo; 
    String name;
    String email;
    String course;
    Double marks;

    public StudentRecord(Integer rollNo, String name, String email, String course, Double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
    }

    public String getGrade() {
        if (marks >= 90) return "A";
        else if (marks >= 75) return "B";
        else if (marks >= 50) return "C";
        else return "F";
    }
}

interface RecordActions {
    void displayStudent(StudentRecord s);
}

class Loader implements Runnable {
    @Override
    public void run() {
        System.out.print("Loading");
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(500);
                System.out.print(".");
            }
            System.out.println(); 
        } catch (InterruptedException e) {
            System.out.println("\nLoading interrupted!");
        }
    }
}

public class Student implements RecordActions {

    @Override
    public void displayStudent(StudentRecord s) {
        System.out.println("Roll No: " + s.rollNo);
        System.out.println("Name: " + s.name);
        System.out.println("Email: " + s.email);
        System.out.println("Course: " + s.course);
        System.out.println("Marks: " + s.marks);
        System.out.println("Grade: " + s.getGrade());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Student manager = new Student(); 
        StudentRecord studentRecord = null;

        try {
            System.out.print("Enter Roll No- ");
            String rollInput = scanner.nextLine();
            if (rollInput.isEmpty()) throw new IllegalArgumentException("Roll No cant be empty");
            Integer rollNo = Integer.parseInt(rollInput); 

            System.out.print("Enter Name- ");
            String name = scanner.nextLine();
            if (name.isEmpty()) throw new IllegalArgumentException("Name cant be empty.");

            System.out.print("Enter Email- ");
            String email = scanner.nextLine();

            System.out.print("Enter Course- ");
            String course = scanner.nextLine();

            System.out.print("Enter Marks: ");
            String marksInput = scanner.nextLine();
            Double marks = Double.parseDouble(marksInput);


            if (marks < 0 || marks > 100) {
                throw new IllegalArgumentException("Marks must be between 0 and 100.");
            }

          
            studentRecord = new StudentRecord(rollNo, name, email, course, marks);


            Thread loadingThread = new Thread(new Loader());
            loadingThread.start();
            loadingThread.join(); 

           
            if (studentRecord == null) {
                throw new StudentNotFoundException("Error: Student record could not be created.");
            }


            manager.displayStudent(studentRecord);

        } catch (NumberFormatException e) {
            System.out.println("\nError: Invalid number format. Please enter numbers");
        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
        } catch (StudentNotFoundException e) {
            System.out.println("\nCustom Error: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("\nError: Thread execution interrupted.");
        } catch (Exception e) {
            System.out.println("\nAn unexpected error occurred: " + e.getMessage());
        } finally {
            System.out.println("Program execution completed.");
            scanner.close();
        }
    }
}