package flcd.compiler;

public class PifEntry {
    private String type;
    private Integer hashValue;
    private Integer index;

    public PifEntry(String type, Integer hashValue, Integer index){
        this.type = type;
        this.hashValue = hashValue;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHashValue() {
        return hashValue;
    }

    public void setHashValue(Integer hashValue) {
        this.hashValue = hashValue;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "{ " + type + " => {" + String.valueOf(hashValue) + "," + String.valueOf(index) + "} }";
    }
}
