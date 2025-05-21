package Components;

public class Record {
    public String name;
    public int score;

    public Record(String name, int score) {
        this.name = name;
        this.score = score;
    }

    // Optional: toString for debugging
    public String toString() {
        return name + ": " + score;
    }
}
