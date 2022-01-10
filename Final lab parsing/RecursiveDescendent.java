package ppd;

import java.util.*;

public class RecursiveDescendent {

    Grammar grammar;
    Configuration configuration;

    enum CONFIGURATION_STATE {
        NORMAL, BACK, SUCCESS, ERROR
    }

    public RecursiveDescendent(Grammar grammar) {
        this.grammar = grammar;
        this.configuration = new Configuration(1, CONFIGURATION_STATE.NORMAL, new Stack<>(), new Stack<>());

        configuration.workingStack.push("ε");
        configuration.inputStack.push("ε");
        configuration.inputStack.push(grammar.getStartSymbol());
    }

    public Stack<String> scanSequence(List<String> input) throws Exception {

        while (configuration.state != CONFIGURATION_STATE.SUCCESS && configuration.state != CONFIGURATION_STATE.ERROR) {

            String inputTop = configuration.inputStack.peek();
            String workingTop = configuration.workingStack.peek();

            if (configuration.position == input.size() + 1 && configuration.inputStack.peek().equals("ε"))
                success();

            if (configuration.state == CONFIGURATION_STATE.NORMAL) {
                if (grammar.hasNonTerminal(inputTop)){
                    expand();
                }
                else if (input.size() >= configuration.position && input.get(configuration.position - 1).equals(inputTop))
                    advance();
                else momentaryInsucces();
            }
            else if (configuration.state == CONFIGURATION_STATE.BACK) {
                if (!grammar.getTerminals().contains(workingTop))
                    anotherTry();
                else back();
            }
        }
        if (configuration.state == CONFIGURATION_STATE.SUCCESS) {
            System.out.println("Sequence accepted!");
            return configuration.workingStack;

        } else {
            throw new Exception("Syntax Error!");
        }
    }

    void success() {
        configuration.state = CONFIGURATION_STATE.SUCCESS;
    }

    void expand() {
        String nonTerminal = configuration.inputStack.pop();
//        System.out.println(configuration.inputStack);
        List<String> result = new ArrayList<>(grammar.getProductions().get(nonTerminal).get(0));
        Collections.reverse(result);
        result.forEach(s -> configuration.inputStack.push(s));
        configuration.workingStack.add(nonTerminal + "#1");
    }

    void advance() {
//        System.out.println(configuration);
        configuration.position ++;
        String element = configuration.inputStack.pop();
        configuration.workingStack.push(element);
    }

    void momentaryInsucces() {
//        System.out.println(configuration);
        configuration.state = CONFIGURATION_STATE.BACK;
    }

    void back() {
//        System.out.println(configuration);
        configuration.position--;
        configuration.inputStack.push(configuration.workingStack.pop());
    }

    void anotherTry() {
//        System.out.println(configuration);
        String topWorking = configuration.workingStack.peek();
        String nonTerminal = Arrays.asList(topWorking.split("#")).get(0);
        int productionNumber = Integer.parseInt(Arrays.asList(topWorking.split("#")).get(1));
        List<String> currentProduction = this.grammar.getProductions().get(nonTerminal).get(productionNumber - 1);

        if (productionNumber != this.grammar.getProductions().get(nonTerminal).size()) {
            configuration.state = CONFIGURATION_STATE.NORMAL;

            currentProduction.forEach(s -> configuration.inputStack.pop());
            configuration.workingStack.pop();
            configuration.workingStack.push(nonTerminal + "#" + (productionNumber + 1));

            List<String> nextProduction = new ArrayList<>(this.grammar.getProductions().get(nonTerminal).get(productionNumber));
            Collections.reverse(nextProduction);
            nextProduction.forEach(s -> configuration.inputStack.push(s));

        } else if (configuration.position == 1 && nonTerminal.equals(this.grammar.getStartSymbol()))
            configuration.state = CONFIGURATION_STATE.ERROR;
        else {
            configuration.workingStack.pop();
            currentProduction.forEach(s -> configuration.inputStack.pop());
            configuration.inputStack.push(nonTerminal);
        }
    }
}
