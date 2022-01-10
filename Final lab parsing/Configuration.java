package ppd;

import java.util.Stack;

public class Configuration {

    public int position;
    RecursiveDescendent.CONFIGURATION_STATE state;
    Stack<String> workingStack;
    Stack<String> inputStack;

    public Configuration(int position, RecursiveDescendent.CONFIGURATION_STATE state, Stack<String> workingStack, Stack<String> inputStack) {
        this.position = position;
        this.state = state;
        this.workingStack = workingStack;
        this.inputStack = inputStack;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "position=" + position +
                ", state=" + state +
                ", workingStack=" + workingStack +
                ", inputStack=" + inputStack +
                '}';
    }
}