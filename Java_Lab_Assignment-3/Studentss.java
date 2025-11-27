import java.io.*;
import java.util.*;


class StudentNotFoundException extends Exception {
    public StudentNotFoundException(String message) {
        super(message);
    }
}


class InvalidStudentDataException extends Exception {
    public InvalidStudentDataException(String message) {
        super(message);
    }
}



class Student implements Serializable {

    private Integer id; 
    private String name;
    private Double marks;

    public Student(Integer id, String name, Double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public Double getMarks() { return marks; }

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
        return s2.getMarks().compareTo(s1.getMarks()); 
    }
}


class DataOperationTask extends Thread {
    private String taskName;

    public DataOperationTask(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.print(taskName + " processing");
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(300); // 300ms 
                System.out.print(".");
            }
            System.out.println(" Done!");
        } catch (InterruptedException e) {
            System.out.println("Task interrupted.");
        }
    }
}


public class Studentss {
    private static final String FILE_NAME = "students.txt";
    private static ArrayList<Student> studentList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadRecords();

        boolean running = true;
        while (running) {
            System.out.println("\n==Student management");
            System.out.println("1. Add Student");
            System.out.println("2. View All");
            System.out.println("3. Find Student");
            System.out.println("4. Save & Exit");
            System.out.print("Enter choice: ");

            try {
                String input = scanner.nextLine();
                Integer choice = Integer.parseInt(input);

                switch (choice) {
                    case 1:
                        addStudent();
                        break;
                    case 2:
                        viewStudents();
                        break;
                    case 3:
                        findStudentById();
                        break;
                    case 4:
                        saveAndExit();
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try 1-4.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Please enter a valid numeric choice.");
            } catch (Exception e) {
                System.err.println("Unexpected Error: " + e.getMessage());
            } finally {
                
            }
        }
    }

   

    private static void addStudent() {
        DataOperationTask loadingThread = new DataOperationTask("Initializing");
        loadingThread.start();
        try {
            loadingThread.join(); 
        } catch (InterruptedException e) { e.printStackTrace(); }

        try {
            System.out.print("Enter ID ");
            Integer id = Integer.valueOf(scanner.nextLine()); 

            System.out.print("Enter Name ");
            String name = scanner.nextLine();
            
            
            if (name.trim().isEmpty()) {
                throw new InvalidStudentDataException("Name cannot be empty.");
            }

            System.out.print("Enter Marks (0-100): ");
            Double marks = Double.valueOf(scanner.nextLine()); 

          
            if (marks < 0 || marks > 100) {
                throw new InvalidStudentDataException("Marks must be between 0 and 100.");
            }

            studentList.add(new Student(id, name, marks));
            System.out.println("Student added successfully.");

        } catch (NumberFormatException e) {
            System.err.println("Input Error: You must enter numbers for ID and Marks.");
        } catch (InvalidStudentDataException e) {
            System.err.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unknown Error occurred.");
        }
    }

    private static void viewStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No records to display.");
            return;
        }
        Collections.sort(studentList, new MarksComparator());
        System.out.println("\n--- Student List (Sorted by Marks) ---");
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void findStudentById() {
        try {
            System.out.print("Enter Student ID to search: ");
            Integer searchId = Integer.parseInt(scanner.nextLine());
            boolean found = false;

            for (Student s : studentList) {
                if (s.getId().equals(searchId)) {
                    System.out.println("Record Found: " + s);
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new StudentNotFoundException("Student with ID " + searchId + " does not exist.");
            }

        } catch (NumberFormatException e) {
            System.err.println("Error: ID must be a number.");
        } catch (StudentNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }


    private static void saveAndExit() {
        Thread saveThread = new Thread(() -> {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (Student s : studentList) {
                    bw.write(s.toFileString());
                    bw.newLine();
                    Thread.sleep(100); 
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Error during save process: " + e.getMessage());
            }
        });

        DataOperationTask uiThread = new DataOperationTask("Saving Data");

        
        uiThread.start();
        saveThread.start();

        try {
        
            saveThread.join();
            uiThread.join(); 
            System.out.println("System Exited Safely.");
        } catch (InterruptedException e) {
            System.err.println("Shutdown interrupted.");
        }
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
                        Integer.valueOf(parts[0]),
                        parts[1],
                        Double.valueOf(parts[2])
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not load previous data.");
        }
    }
}