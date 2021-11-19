package flcd.compiler;

public class Transition {
    public String n1, n2;
    public Integer v;

    public Transition(String n1, String n2, Integer v) {
        this.n1 = n1;
        this.n2 = n2;
        this.v = v;
    }

    @Override
    public String toString() {
        return n1 +
                "-" +
                String.valueOf(v) +
                "->" +
                n2;
    }
}
