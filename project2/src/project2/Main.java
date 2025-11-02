package project2;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        RegistrationSystem rs = new RegistrationSystem();

        while (true) {
            System.out.println("""
                Menu:
                1. Add Student
                2. Enter Marks
                3. Modify a Mark
                4. Display Student Details
                5. Display Marks Info
                6. List All Students
                7. Exit
                """);
            System.out.print("Choice: ");
            int ch = safeInt(in);

            switch (ch) {
                case 1 -> addStudentFlow(in, rs);
                case 2 -> enterMarksFlow(in, rs);
                case 3 -> modifyMarkFlow(in, rs);
                case 4 -> displayStudentFlow(in, rs);
                case 5 -> displayMarksFlow(in, rs);
                case 6 -> rs.listAll();
                case 7 -> { in.close(); return; }
                default -> System.out.println("Invalid choice.\n");
            }
        }
    }

    /* ---------- Flows ---------- */
    private static void addStudentFlow(Scanner in, RegistrationSystem rs) {
        System.out.print("Enter Student Name: ");
        String name = in.nextLine().trim();
        String id;
        while (true) {
            System.out.print("Enter KKU ID (starts with 4, 9 digits): ");
            id = in.nextLine().trim();
            if (id.matches("^4\\d{8}$")) break;
            System.out.println("Invalid ID.");
        }
        int n;
        while (true) {
            System.out.print("How many courses (1..5): ");
            n = safeInt(in);
            if (n >= 1 && n <= 5) break;
            System.out.println("Must be 1..5.");
        }
        System.out.print("Level (1=Undergrad, 2=Grad): ");
        int lvl = safeInt(in);
        Student s = (lvl == 2) ? new GradStudent(name, id, n) : new UndergradStudent(name, id, n);

        System.out.println(rs.addStudent(s) ? "Student added.\n" : "ID already exists.\n");
    }

    private static void enterMarksFlow(Scanner in, RegistrationSystem rs) {
        Student s = askStudent(in, rs); if (s == null) return;
        System.out.printf("Enter %d marks (0..100):%n", s.getNumCourses());
        for (int i = 0; i < s.getNumCourses(); i++) {
            int m;
            while (true) {
                System.out.printf("Course %d: ", i + 1);
                m = safeInt(in);
                if (m >= 0 && m <= 100) break;
                System.out.println("Invalid mark.");
            }
            s.setMark(i, m);
        }
        System.out.println("Marks saved.\n");
    }

    private static void modifyMarkFlow(Scanner in, RegistrationSystem rs) {
        Student s = askStudent(in, rs); if (s == null) return;
        System.out.printf("Which course to modify (1..%d): ", s.getNumCourses());
        int idx = safeInt(in) - 1;
        if (idx < 0 || idx >= s.getNumCourses()) { System.out.println("Invalid course.\n"); return; }
        System.out.print("New mark (0..100): ");
        int m = safeInt(in);
        try { s.setMark(idx, m); System.out.println("Updated.\n"); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage() + "\n"); }
    }

    private static void displayStudentFlow(Scanner in, RegistrationSystem rs) {
        Student s = askStudent(in, rs); if (s == null) return;
        System.out.println("Name: " + s.getName());
        System.out.println("KKU ID: " + s.getKkuId());
        System.out.println("Level: " + s.level());
        System.out.println("Passing Threshold: " + s.getPolicy().threshold() + "%");
        s.printMarksTable(true, true);
    }

    private static void displayMarksFlow(Scanner in, RegistrationSystem rs) {
        Student s = askStudent(in, rs); if (s == null) return;
        s.printMarksTable(true, false);
    }

    /* ---------- Helpers ---------- */
    private static Student askStudent(Scanner in, RegistrationSystem rs) {
        System.out.print("Enter KKU ID: ");
        Student s = rs.find(in.nextLine().trim());
        if (s == null) System.out.println("Student not found.\n");
        return s;
    }
    private static int safeInt(Scanner in) {
        while (true) {
            try { return Integer.parseInt(in.nextLine().trim()); }
            catch (Exception e) { System.out.print("Enter a number: "); }
        }
    }
}

