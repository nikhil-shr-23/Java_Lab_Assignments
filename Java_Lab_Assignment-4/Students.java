import java.io.*;
import java.util.*;

class Student {
    private int id;
    private String name;
    private double marks;

    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
//getter functions
    public int getId() { return id; }
    public String getName() { return name; }
    public double getMarks() { return marks; }

    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Marks: " + marks;
    }

    public String toFileString() {
        return id + "," + name + "," + marks;
    }
}


class MarksComparator implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Double.compare(s2.getMarks(), s1.getMarks());
    }
}


public class Students {
    private static final String FILE_NAME = "students.txt";
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadRecords();

        while (true) {
            System.out.println("\n=== Student Record Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All (using Iterator)");
            System.out.println("3. Sort by Marks (using Comparator)");
            System.out.println("4. Show File Attributes (File Class)");
            System.out.println("5. Random Read Demo (RandomAccessFile)");
            System.out.println("6. Save & Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    sortStudents();
                    break;
                case 4:
                    showFileAttributes();
                    break;
                case 5:
                    readRandomly();
                    break;
                case 6:
                    saveRecords();
                    System.out.println("Records saved. Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }


    private static void addStudent() {
        try {
            System.out.print("Enter ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Marks: ");
            double marks = Double.parseDouble(scanner.nextLine());

            studentList.add(new Student(id, name, marks));
            System.out.println("Student added successfully.");
        } catch (Exception e) {
            System.out.println("Error: Invalid input format.");
        }
    }

    private static void viewStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        System.out.println("\n--- Student List ---");
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void sortStudents() {
        Collections.sort(studentList, new MarksComparator());
        System.out.println("Sorted by marks (descending). View list to see changes.");
    }


    private static void loadRecords() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    studentList.add(new Student(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Double.parseDouble(parts[2])
                    ));
                }
            }
            System.out.println("Loaded " + studentList.size() + " records from file.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static void saveRecords() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : studentList) {
                bw.write(s.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void showFileAttributes() {
        File f = new File(FILE_NAME);
        if (f.exists()) {
            System.out.println("\n--- File Attributes ---");
            System.out.println("File Name: " + f.getName());
            System.out.println("Absolute Path: " + f.getAbsolutePath());
            System.out.println("Size: " + f.length() + " bytes");
            System.out.println("Can Read: " + f.canRead());
            System.out.println("Can Write: " + f.canWrite());
        } else {
            System.out.println("File does not exist yet (Save records first).");
        }
    }

    private static void readRandomly() {
        File f = new File(FILE_NAME);
        if (!f.exists()) {
            System.out.println("File must exist to read randomly. Save first.");
            return;
        }

        System.out.println("\n--- Random Access Demo ---");
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            raf.seek(0);
            System.out.println("Seek to position 0. Reading first line:");
            System.out.println("-> " + raf.readLine());


            if (raf.length() > 5) {
                raf.seek(5); 
                System.out.println("Seek to position 5. Rest of the line:");
                System.out.println("-> " + raf.readLine());
            }
        } catch (IOException e) {
            System.out.println("RAF Error: " + e.getMessage());
        }
    }
}