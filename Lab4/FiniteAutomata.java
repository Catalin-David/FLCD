package flcd.compiler;

import flcd.compiler.IO.FileReader;

import java.util.ArrayList;
import java.util.Scanner;

public class FiniteAutomata {
    private ArrayList<String> Q, F;
    private String q0;
    private ArrayList<Integer> E;
    private ArrayList<Transition> S;
    private boolean deterministic;


    public FiniteAutomata() {
        Q = new ArrayList<>();
        E = new ArrayList<>();
        q0 = "";
        F = new ArrayList<>();
        S = new ArrayList<>();
        readFiniteAutomata("FA.in");
        checkDeterministic();
    }

    public void readFiniteAutomata(String file){
        FileReader reader = new FileReader();
        ArrayList<String> lines = reader.readFile(file);
        Q = splitIntoStrings(lines.get(0));
        lines.remove(0);
        E = splitIntoInts(lines.get(0));
        lines.remove(0);
        q0 = splitIntoStrings(lines.get(0)).get(0);
        lines.remove(0);
        F = splitIntoStrings(lines.get(0));
        lines.remove(0);
        createS(lines);
    }

    private void checkDeterministic(){
        this.deterministic = true;
        for(int i=0; i<S.size(); i++){
            for(int j=0; j<S.size(); j++){
                if(i != j && S.get(i).n1.equals(S.get(j).n1) && S.get(i).v.equals(S.get(j).v)){
                    this.deterministic = false;
                }
            }
        }
    }

    public ArrayList<String> splitIntoStrings(String line){
        String[] tokens = line.split(" ");
        ArrayList<String> result = new ArrayList<>();
        for(String token: tokens){
            result.add(token);
        }
        return result;
    }

    public ArrayList<Integer> splitIntoInts(String line){
        String[] tokens = line.split(" ");
        ArrayList<Integer> result = new ArrayList<>();
        for(String token: tokens){
            result.add(Integer.valueOf(token));
        }
        return result;
    }

    public void createS(ArrayList<String> lines){
        for(String line: lines){
            ArrayList<String> tokens = splitIntoStrings(line);
            S.add(new Transition(tokens.get(0), tokens.get(2), Integer.valueOf(tokens.get(1))));
        }
    }

    private boolean verifySequence(String sequence){
        if(sequence.length() == 0){
            return F.contains(q0);
        }
        String currentState = q0;
        for(int i=0; i<sequence.length(); i++){
            int input = Integer.parseInt(String.valueOf(sequence.charAt(i)));
            boolean found = false;
            for(Transition t: S){
                if(t.n1.equals(currentState) && t.v == input){
                    currentState = t.n2;
                    found = true;
                    break;
                }
            }
            if(!found){
                return false;
            }
        }
        return F.contains(currentState);
    }

    public void runConsole(){
        Scanner userInput = new Scanner(System.in);
        while(true){
            System.out.println("0. Exit");
            System.out.println("1. Display set of states");
            System.out.println("2. Display the alphabet");
            System.out.println("3. Display the initial state");
            System.out.println("4. Display set of final states");
            System.out.println("5. Display set of transitions");
            System.out.println("6. Verify sequence");
            System.out.println(">");

            int option = Integer.parseInt(userInput.nextLine());
            if(option == 0){
                break;
            }
            else if(option == 1){
                System.out.println("States are:");
                for(String str: Q){
                    System.out.println(str);
                }
            }
            else if(option == 2){
                System.out.println("Alphabet is:");
                for(Integer str: E){
                    System.out.println(str);
                }
            }
            else if(option == 3){
                System.out.println("Initial state is:");
                System.out.println(q0);
            }
            else if(option == 4){
                System.out.println("Final states are:");
                for(String str: F){
                    System.out.println(str);
                }
            }
            else if(option == 5){
                System.out.println("Transitions are:");
                for(Transition tr: S){
                    System.out.println(tr);
                }
            }
            else if(option == 6){
                if(deterministic){
                    System.out.println("Sequence: >");
                    String sequence = userInput.nextLine();

                    if(verifySequence(sequence)){
                        System.out.println("Sequence is accepted");
                    }
                    else{
                        System.out.println("Sequence is not accepted");
                    }
                }
                else{
                    System.out.println("FA is not deterministic");
                }
            }
        }
    }
}
