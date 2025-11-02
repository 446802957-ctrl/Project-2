package project2;

public class UndergradStudent extends Student {
    public UndergradStudent(String name, String kkuId, int numCourses) {
        super(name, kkuId, numCourses, new UndergradPolicy());
    }
    public String level() { return "Undergraduate"; }
}
