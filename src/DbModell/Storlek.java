package DbModell;

public class Storlek {
    
    private final int id;
    private int nummer;


    public Storlek(int id, int nummer) {
        this.id = id;
        this.nummer = nummer;
    }

    public int getId() {
        return id;
    }
    
    public int getNummer() {
        return nummer;
    }

    public void setNummer(int nummer) {
        this.nummer = nummer;
    }
    
}