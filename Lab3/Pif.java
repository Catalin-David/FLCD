package flcd.compiler;

import flcd.compiler.IO.FileWriter;
import java.util.ArrayList;

public class Pif {
    private ArrayList<PifEntry> entries;

    public Pif() {
        this.entries = new ArrayList<>();
    }

    public void addToPif(String type, Integer hashValue, Integer index){
        entries.add(new PifEntry(type, hashValue, index));
    }

    public void printPif(){
        FileWriter fileWriter = new FileWriter();
        ArrayList<String> lines = new ArrayList<>();
        entries.forEach(entry -> {
            lines.add(entry.toString() + "\n");
        });
        fileWriter.writeToFile(lines, "pif.out");
    }
}
