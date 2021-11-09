package flcd.compiler.IO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileWriter {
    private String pathPrefix = "C:\\Users\\cata0\\OneDrive\\Desktop\\School\\3rdYear\\Compiler\\src\\flcd\\compiler\\IO\\";
    public FileWriter() {
    }

    public void writeToFile(ArrayList<String> lines, String filePath){
        Path fileName = Path.of(pathPrefix + filePath);
        String finalString = "";
        for(int i=0; i<lines.size(); i++){
            finalString = finalString + lines.get(i);
        }
        try {
            Files.writeString(fileName, finalString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
