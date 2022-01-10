package ppd;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileReader {
    private String pathPrefix = "C:\\Users\\cata0\\OneDrive\\Desktop\\School\\3rdYear\\Parallel & Distributed Prog\\Parser\\src\\ppd\\";

    public FileReader() {
    }

    public ArrayList<String> readFile(String filePath){
        ArrayList<String> lines = new ArrayList<>();
        File file = new File(pathPrefix + filePath);
        try {
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNextLine()){
                String line = fileScanner.nextLine();
                lines.add(line);
            }

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        return lines;
    }
}