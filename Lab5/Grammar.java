package ppd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grammar {
    private ArrayList<String> terminals;
    private ArrayList<String> nonTerminals;
    private HashMap<String, ArrayList<ArrayList<String>>> productions;
    private String startSymbol;
    private String fileName;

    public Grammar(String fileName){
        this.terminals = new ArrayList<>();
        this.nonTerminals = new ArrayList<>();
        this.productions = new HashMap<>();
        this.fileName = fileName;
        this.readFromFile();
    }

    public void printTerminals() {
        StringBuilder builder = new StringBuilder();
        builder.append("Terminals: ");
        for(String terminal: terminals){
            System.out.println(terminal);
            builder.append(terminal + "");
        }
        System.out.println(builder.toString());
    }

    public void printStartSymbol() {
        System.out.println("Start symbol: " + startSymbol);
    }

    public void printProductions() {
        System.out.println("Productions:");
        StringBuilder builder = new StringBuilder();
        for(Map.Entry<String, ArrayList<ArrayList<String>>> entry : productions.entrySet()){
            String key = entry.getKey();
            ArrayList<ArrayList<String>> value = entry.getValue();

            builder.append(key + " -> ");
            for(ArrayList<String> arr : value){
                for(String str: arr){
                    builder.append(str + " ");
                }
                builder.append("| ");
            }

            builder.append("\n");
        }
        System.out.println(builder.toString());
    }

    public void getNonTerminals() {
        StringBuilder builder = new StringBuilder();
        builder.append("Nonterminals: ");
        for(String nonTerminal: nonTerminals){
            builder.append(nonTerminal + " ");
        }
        System.out.println(builder.toString());
    }


    public ArrayList<String> splitIntoStrings(String line, String regex){
        String[] tokens = line.split(regex);
        ArrayList<String> result = new ArrayList<>();
        for(String token: tokens){
            result.add(token);
        }
        return result;
    }

    @Override
    public String toString() {
        return "Grammar \n" + "\nNonTerminals = " + nonTerminals + "\n\nTerminals = " + terminals + "\n\nStart Symbol = '" + startSymbol + '\'';
    }


    private void readFromFile(){

        FileReader fileReader = new FileReader();
        ArrayList<String> lines = fileReader.readFile("g1.txt");

        String nonTerminalsString = lines.get(0);
        lines.remove(0);

        nonTerminals = splitIntoStrings(nonTerminalsString, " ");

        String terminalsString = lines.get(0);
        lines.remove(0);

        terminals = splitIntoStrings(terminalsString, " ");

        startSymbol = lines.get(0);
        lines.remove(0);

        for(String line: lines){
            ArrayList<String> production = splitIntoStrings(line, ">");

            String tokenLine = production.get(1);
            ArrayList<String> tokens = splitIntoStrings(tokenLine, "|");

            String nonterminal = splitIntoStrings(production.get(0), " ").get(0);

            tokens.remove(0);

            ArrayList<ArrayList<String>> productionElements = new ArrayList<>();
            for(String token: tokens){
                ArrayList<String> elements = splitIntoStrings(token, " ");
                productionElements.add(elements);
            }
            productions.put(nonterminal, productionElements);
        }
    }

    public String productionsForNonterminal(String nonTerminal){
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public boolean checkGfc(){
        if(!this.nonTerminals.contains(startSymbol)){
            return false;
        }

        for(Map.Entry<String, ArrayList<ArrayList<String>>> entry: productions.entrySet()){
            String key = entry.getKey();
            if(!this.nonTerminals.contains(key)){
                return false;
            }

            ArrayList<ArrayList<String>> value = entry.getValue();

            for(ArrayList<String> arr: value){
                for(String elem: arr){
                    if(!this.nonTerminals.contains(elem) && !this.terminals.contains(elem)){
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
