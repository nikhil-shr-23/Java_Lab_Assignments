import java.io.*;
import java.util.*;

class Student {
    int roll;
    String name;
    String email;
    String course;
    double marks;

    public Student(int roll, String name, String email, String course, double marks) {
        this.roll = roll;
        this.name = name;
        this.email = email;
        this.course = course;
        this.marks = marks;
    }

    public String toString() {
        return "Roll No: " + roll + "\n" +
               "Name: " + name + "\n" +
               "Email: " + email + "\n" +
               "Course: " + course + "\n" +
               "Marks: " + marks + "\n";
    }
}

class FileUtil {
    public static void saveToFile(ArrayList<Student> list) {
        try {
            FileWriter fw = new FileWriter("students.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (Student s : list) {
                bw.write(s.roll + "," + s.name + "," + s.email + "," + s.course + "," + s.marks);
                bw.newLine();
            }
            bw.close();
            System.out.println("Saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    public static ArrayList<Student> readFromFile() {
        ArrayList<Student> list = new ArrayList<>();
        File f = new File("students.txt");

        if (!f.exists()) {
            return list;
        }

        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line;
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    int r = Integer.parseInt(data[0]);
                    String n = data[1];
                    String e = data[2];
                    String c = data[3];
                    double m = Double.parseDouble(data[4]);
                    
                    Student s = new Student(r, n, e, c, m);
                    list.add(s);
                }
            }
            br.close();
            System.out.println("Loaded records.");
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        return list;
    }

    public static void checkFile() {
        try {
            File f = new File("students.txt");
            if (f.exists()) {
                System.out.println("File size: " + f.length());
                
                RandomAccessFile raf = new RandomAccessFile(f, "r");
                raf.seek(0);
                System.out.println("First line check: " + raf.readLine());
                raf.close();
            }
        } catch (Exception e) {
            System.out.println("Error checking file.");
        }
    }
}

public class StudentRMS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> list = FileUtil.readFromFile();

        while (true) {
            System.out.println("\n1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Sort by Marks (PriorityQueue)");
            System.out.println("4. Save & Exit");
            System.out.print("Choice: ");
            
            int ch = sc.nextInt();
            
            if (ch == 1) {
                System.out.print("Roll: ");
                int r = sc.nextInt();
                sc.nextLine(); // clear buffer
                
                System.out.print("Name: ");
                String n = sc.nextLine();
                
                System.out.print("Email: ");
                String e = sc.nextLine();
                
                System.out.print("Course: ");
                String c = sc.nextLine();
                
                System.out.print("Marks: ");
                double m = sc.nextDouble();

                Student st = new Student(r, n, e, c, m);
                list.add(st);
                
            } else if (ch == 2) {
                Iterator<Student> it = list.iterator();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }
                
            } else if (ch == 3) {
                Comparator<Student> comp = new Comparator<Student>() {
                    public int compare(Student s1, Student s2) {
                        if (s1.marks < s2.marks) return 1;
                        if (s1.marks > s2.marks) return -1;
                        return 0;
                    }
                };
                PriorityQueue<Student> pq = new PriorityQueue<>(comp);
                pq.addAll(list);
                
                while (!pq.isEmpty()) {
                    System.out.println(pq.poll());
                }
                
            } else if (ch == 4) {
                FileUtil.saveToFile(list);
                FileUtil.checkFile(); 
                break;
            }
        }
        sc.close();
    }
}