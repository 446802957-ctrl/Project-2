package project2;

public class UndergradPolicy implements PassPolicy {
    public boolean isPass(int mark) { return mark >= 60; }
    public int threshold() { return 60; }
}
