package ppd;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws Exception {


        Grammar grammar = new Grammar("g1.txt");
        System.out.println(grammar.toString());

        RecursiveDescendent rd = new RecursiveDescendent(grammar);
        ArrayList<String> sequence = new ArrayList<>();
        sequence.add("a");
        sequence.add("a");
        sequence.add("c");
        sequence.add("b");
        sequence.add("c");

        List<String> productionSequence = rd.scanSequence(sequence);
        //System.out.println(productionSequence);

        ParserOutput parserOutput = new ParserOutput(grammar);
        parserOutput.addProductionString(productionSequence);
        System.out.println(parserOutput.toString());
    }
}
