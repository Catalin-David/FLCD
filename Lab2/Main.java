package flcd.compiler;

public class Main {

    public static void main(String[] args) {
        SymbolTable table = new SymbolTable(1, 0.7);
        table.add("something");
        table.add("1010");
        table.add("-9999");
        table.add("true");
        table.add("variable2");

        Integer[] result = new Integer[2];
        result = table.find("something");
        System.out.println("find(\"something\") = " + String.valueOf(result[0]) + " " + String.valueOf(result[1]));
        result = table.find("1010");
        System.out.println("find(\"1010\") = " + String.valueOf(result[0]) + " " + String.valueOf(result[1]));
        result = table.find("-9999");
        System.out.println("find(\"-9999\") = " + String.valueOf(result[0]) + " " + String.valueOf(result[1]));
        result = table.find("true");
        System.out.println("find(\"true\") = " + String.valueOf(result[0]) + " " + String.valueOf(result[1]));
        result = table.find("variable2");
        System.out.println("find(\"variable2\") = " + String.valueOf(result[0]) + " " + String.valueOf(result[1]));
        result = table.find("variable1");
        System.out.println("find(\"variable1\") = " + String.valueOf(result[0]) + " " + String.valueOf(result[1]));
    }
}
