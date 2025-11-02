package project2;

abstract class Student {
    private String name;
    private String kkuId;
    private int[] marks;
    private int numCourses;
    private final PassPolicy policy;

    protected Student(String name, String kkuId, int numCourses, PassPolicy policy) {
        this.name = toTitleCase(name);
        this.kkuId = kkuId;
        this.numCourses = numCourses;
        this.marks = new int[numCourses];
        this.policy = policy;
    }

    public String getName() { return name; }
    public String getKkuId() { return kkuId; }
    public int getNumCourses() { return numCourses; }
    public PassPolicy getPolicy() { return policy; }
    public int getMark(int i) { return marks[i]; }

    public void setMark(int idx, int mark) {
        if (idx < 0 || idx >= numCourses) throw new IllegalArgumentException("Invalid course index.");
        if (mark < 0 || mark > 100)      throw new IllegalArgumentException("Mark must be 0..100.");
        marks[idx] = mark;
    }

    public double average() {
        int t = 0; for (int i = 0; i < numCourses; i++) t += marks[i];
        return numCourses == 0 ? 0 : (double)t / numCourses;
    }
    public int max() { int m = marks[0]; for (int i=1;i<numCourses;i++) if (marks[i]>m) m=marks[i]; return m; }
    public int min() { int m = marks[0]; for (int i=1;i<numCourses;i++) if (marks[i]<m) m=marks[i]; return m; }

    public void printMarksTable(boolean withHeader, boolean avgBeside) {
        if (numCourses == 0) {
            System.out.println("No courses.\n");
            return;      }
        double avg = average();
        if (withHeader) {
            if (avgBeside) {
                System.out.printf("%-8s %-8s %-8s %-8s Avg: %.1f%n",
                        "Course", "Mark", "P/F", "Tag", avg);
            } else {
                System.out.printf("%-8s %-8s %-8s %-8s%n", "Course", "Mark", "P/F", "Tag");
            }
            System.out.println("=".repeat(40));
        }

        int max = max(), min = min();
        for (int i = 0; i < numCourses; i++) {
            String pf = policy.isPass(marks[i]) ? "Pass" : "Fail";
            String tag = "";
            if (marks[i] == max) tag = "Max";
            if (marks[i] == min) tag = "Min";

            System.out.printf("%-8d %-8d %-8s %-8s%n", i + 1, marks[i], pf, tag);
        }

        if (!avgBeside) {
            System.out.printf("%nAverage: %.1f%n%n", avg);
        } else {
            System.out.println();
        }
    }

    public abstract String level();

    protected static String toTitleCase(String s) {
        String[] w = s.trim().split("\\s+");
        StringBuilder b = new StringBuilder();
        for (String x : w) if (!x.isEmpty())
            b.append(Character.toUpperCase(x.charAt(0))).append(x.substring(1).toLowerCase()).append(' ');
        return b.toString().trim();
    }
}
