package flcd.compiler;

import java.util.ArrayList;

public class SymbolTable {
    private ArrayList<String>[] hashMap;
    private Integer size;
    private Integer capacity;
    private final double threshold;

    public SymbolTable(Integer capacity, double threshold) {
        this.hashMap = new ArrayList[capacity];
        for(int i=0; i<capacity; i++){
            hashMap[i] = new ArrayList<>();
        }
        this.capacity = capacity;
        this.size = 0;
        this.threshold = threshold;
    }

    public void add(String identifier){
        Integer[] alreadyAdded = find(identifier);
        if(alreadyAdded[0] != -1){
            return;
        }
        double sizeCheck = size;
        double capacityCheck = capacity;

        if(sizeCheck >= capacityCheck * threshold){
            resize();
        }

        int hashValue = hash(identifier);
        hashMap[hashValue].add(identifier);
        size++;
    }

    private void resize(){
        ArrayList<String>[] previousHashMap = hashMap;
        int previousCapacity = capacity;
        capacity = capacity * 2;
        hashMap = new ArrayList[capacity];
        for(int i=0; i<capacity; i++){
            hashMap[i] = new ArrayList<>();
        }
        size = 0;
        for(int i=0; i<previousCapacity; i++){
            for(String identifier: previousHashMap[i]){
                add(identifier);
            }
        }
    }

    public Integer[] find(String identifier){
        int hashValue = hash(identifier);
        Integer[] result = new Integer[2];
        result[0] = result[1] = -1;
        for(int i=0; i<hashMap[hashValue].size(); i++){
            String s = hashMap[hashValue].get(i);
            if(identifier.equals(s)){
                result[0] = hashValue;
                result[1] = i;
                return result;
            }
        }
        return result;
    }

    public int hash(String identifier){
        int sum = 0;
        for(int i=0; i<identifier.length(); i++){
            char c = identifier.charAt(i);
            sum = sum + (int) c;
        }
        return sum % capacity;
    }
}
