package project2;

public class GradStudent extends Student {
    public GradStudent(String name, String kkuId, int numCourses) {
        super(name, kkuId, numCourses, new GradPolicy());
    }
    public String level() { return "Graduate"; }
}
