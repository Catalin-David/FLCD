package flcd.compiler;

import flcd.compiler.IO.FileReader;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Scanner {
    private SymbolTable st;
    private Pif pif;
    private ArrayList<String> tokens;
    private FileReader reader;
    private String intRegex = "^([+-]?[1-9]\\d*|0)$";
    private String stringRegex = "^\"[a-zA-Z0-9?;:@! ]+\"$";
    private String identifierRegex = "^[a-zA-Z]+[0-9]*$";

    public Scanner() {
        this.st = new SymbolTable(1, 0.75);
        this.pif = new Pif();
        reader = new FileReader();
        readTokens();
    }

    private void readTokens(){
        tokens = reader.readFile("token.in");
        //tokens.forEach(System.out::println);
        //System.out.println(Pattern.matches(stringRegex, "\"a?:bc\""));
        //System.out.println(Pattern.matches(identifierRegex, "a1234567890"));
        //System.out.println(Pattern.matches(identifierRegex, "1234567890"));
        //System.out.println(Pattern.matches(identifierRegex, "1a234567890"));
        //System.out.println(Pattern.matches(stringRegex, "0023"));
        //String line = "val int a=input();";
        //String separators = " [](){}";
        //System.out.println(Pattern.matches(stringRegex, "0023"));
    }

    public boolean isToken(String word){
        for(int i=0; i<tokens.size(); i++){
            if(tokens.get(i).equals(word)){
                return true;
            }
        }
        return false;
    }

    public void scanProgram(String filePath){
        ArrayList<String> lines = reader.readFile(filePath);
        boolean error = false;
        int errorLine = -1;
        String errorToken = "";

        for(int i=0; i<lines.size(); i++){
            ArrayList<String> words = splitWords(lines.get(i));
            if(words.size() == 2 && words.get(0).equals("!!ERROR!!")){
                error = true;
                errorLine = i;
                errorToken = words.get(1);
                break;
            }
            for(int j=0; j<words.size(); j++){
                String word = words.get(j);
                if(isToken(word)){
                    pif.addToPif(word, null, null);
                }
                else{
                    if(Pattern.matches(intRegex, word) || Pattern.matches(stringRegex, word)){
                        st.add(word);
                        Integer[] position = st.find(word);
                        pif.addToPif("constant", position[0], position[1]);
                    }
                    else if(Pattern.matches(identifierRegex, word)){
                        st.add(word);
                        Integer[] position = st.find(word);
                        pif.addToPif("identifier", position[0], position[1]);
                    }
                    else{
                        error = true;
                        errorLine = i;
                        errorToken = word;
                    }
                }
            }
        }

        if(error){
            System.out.println("Lexical error on token " + errorToken + " on line " + String.valueOf(errorLine));
        }
        else{
            System.out.println("Program is lexically correct");
            printData();
        }
    }

    private ArrayList<String> splitWords(String programLine){
        String word = "";
        ArrayList<String> words = new ArrayList<>();

        for(int i=0; i<programLine.length(); i++){
            char c = programLine.charAt(i);
            if(c == '"'){
                word = Character.toString(c);
                i++;
                while(i<programLine.length() && programLine.charAt(i) != '"'){
                    word = word + Character.toString(programLine.charAt(i));
                    i++;
                }
                word = word + Character.toString(c);
                words.add(word);
                word = "";
            }
            else if(('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || ('0' <= c && c <= '9')){
                word = word + c;
            }
            else{
                if(!word.equals("")){
                    words.add(word);
                    word = "";
                }
                if(c == '=' || c == '<' || c == '>' || c == '!'){
                    if(i+1 < programLine.length() && programLine.charAt(i+1) == '='){
                        words.add(Character.toString(c) + Character.toString(programLine.charAt(i+1)));
                        i++;
                    }
                    else{
                        words.add(Character.toString(c));
                    }
                }
                else if(c != ' '){
                    if(c == '(' || c == ')' || c == '[' || c == ']' || c == ';' || c == '+' || c == '-' || c == '*' || c == '/' || c == '%'){
                        words.add(Character.toString(c));
                    }
                    else{
                        ArrayList<String> error = new ArrayList<>();
                        error.add("!!ERROR!!");
                        error.add(Character.toString(c));
                        return error;
                    }
                }
            }
        }
        if(!word.equals("")){
            words.add(word);
        }
        return words;
    }

    private void printData(){
        pif.printPif();
        st.printSymbolTable();
    }
}
