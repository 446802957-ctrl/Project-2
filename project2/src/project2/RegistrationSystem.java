package project2;

import java.util.LinkedHashMap;
import java.util.Map;

public class RegistrationSystem {
    private final Map<String, Student> byId = new LinkedHashMap<>();

    // Add new student to the system
    public boolean addStudent(Student s) {
        if (byId.containsKey(s.getKkuId())) return false;
        byId.put(s.getKkuId(), s);
        return true;
    }

    // Find a student by KKU ID
    public Student find(String id) {
        return byId.get(id);
    }

    // Display all registered students in a clean formatted table
    public void listAll() {
        if (byId.isEmpty()) {
            System.out.println("No students yet.\n");
            return;
        }

        // Print table header
        System.out.printf("%-12s %-26s %-13s %-8s %-17s%n",
                "ID", "Name", "Level", "Courses", "Policy Threshold");
        System.out.println("---------------------------------------------------------------------");

        // Print student details
        for (Student s : byId.values()) {
            System.out.printf("%-12s %-26s %-13s %-8d %-17s%n",
                    s.getKkuId(),
                    s.getName(),
                    s.level(),
                    s.getNumCourses(),
                    s.getPolicy().threshold() + "%");
        }
        System.out.println();
    }
}

