import java.util.Scanner;

public class StudentRecordSystem {
    private static StudentRecordManager manager;
    private static Scanner scanner;

    public static void main(String[] args) {
        manager = new StudentRecordManager();
        scanner = new Scanner(System.in);

        System.out.println("===========================================");
        System.out.println("   STUDENT RECORD MANAGEMENT SYSTEM");
        System.out.println("===========================================");

        int choice;
        do {
            displayMenu();
            choice = getChoice();
            processChoice(choice);
        } while (choice != 9);

        System.out.println("Exiting the application. Goodbye!");
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n===== Student Record Menu =====");
        System.out.println("1. Add Student");
        System.out.println("2. Display All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student Marks");
        System.out.println("5. Delete Student");
        System.out.println("6. Display Statistics");
        System.out.println("7. Sort Students by Marks");
        System.out.println("8. Export to CSV");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (Exception e) {
            System.out.println("Invalid input! Please enter a number between 1 and 9.");
            scanner.nextLine();
            return -1;
        }
    }
    
    private static void processChoice(int choice) {
        switch (choice) {
            case 1:
                manager.addStudent();
                break;
            case 2:
                manager.displayAllStudents();
                break;
            case 3:
                manager.searchStudent();
                break;
            case 4:
                manager.updateStudentMarks();
                break;
            case 5:
                manager.deleteStudent();
                break;
            case 6:
                manager.displayStatistics();
                break;
            case 7:
                manager.sortStudentsByMarks();
                break;
            case 8:
                manager.exportToCSV();
                break;
            case 9:
                System.out.println("Thank you for using Student Record Management System!");
                break;
            default:
                System.out.println("Invalid choice! Please enter a number between 1 and 9.");
        }

        if (choice != 9 && choice >= 1 && choice <= 8) {
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
}
