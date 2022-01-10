package ppd;

import java.util.*;

public class ParserOutput {
    Grammar grammar;
    Map<String, Integer> noOfChildren;
    List<String> values;
    List<Integer> father;
    List<Integer> child;
    List<Integer> rightSibling;

    public ParserOutput(Grammar grammar) {
        this.grammar = grammar;
        this.values = new ArrayList<>();
        this.father = new ArrayList<>();
        this.child = new ArrayList<>();
        this.rightSibling = new ArrayList<>();
        this.noOfChildren = new HashMap<>();

        for (String nonTerminal : grammar.getNonTerminalsList()) {
            ArrayList<ArrayList<String>> productions = grammar.getProductions().get(nonTerminal);

            for (int i = 0; i < productions.size(); i++) {
                noOfChildren.put(nonTerminal + "#" + (i + 1), productions.get(i).size());
            }
        }

        for (String terminal : grammar.getTerminals()) {
            noOfChildren.put(terminal, 0);
        }
        noOfChildren.put("ε", 1);
    }

    public void addProductionString(List<String> productionString) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);

        for (String production : productionString) {
            int father = stack.pop();
            int index = add(production, father);

            for (int i = 0; i < noOfChildren.get(production); i++) {
                stack.push(index);
            }
        }
    }

    public int add(String node, int parent) {
        values.add(node);
        rightSibling.add(-1);
        child.add(-1);

        int index = values.size() - 1;

        if (parent == -1) {
            father.add(-1);
            return index;
        }

        if (child.get(parent) == -1)
            child.set(parent, index);
        else {
            int current = child.get(parent);
            int previous = -1;

            while (current != -1) {
                previous = current;
                current = rightSibling.get(current);
            }
            rightSibling.set(previous, index);
        }
        father.add(parent);

        return index;
    }

    @Override
    public String toString() {

        return "Values: " +
                this.values + "\n" +
                "Father: " +
                this.father + "\n" +
                "Child : " +
                this.child + "\n" +
                "Right sibling: " +
                this.rightSibling + "\n";
    }
}
