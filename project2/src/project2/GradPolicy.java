package project2;

public class GradPolicy implements PassPolicy {
    public boolean isPass(int mark) { return mark >= 80; }
    public int threshold() { return 80; }
}
