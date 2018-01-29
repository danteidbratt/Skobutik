package DbModell;

public class Köp {
    
    private int skoID;
    private int beställningID;

    public Köp(int skoID, int beställningID) {
        this.skoID = skoID;
        this.beställningID = beställningID;
    }

    public int getSkoID() {
        return skoID;
    }

    public void setSkoID(int skoID) {
        this.skoID = skoID;
    }

    public int getBeställningID() {
        return beställningID;
    }

    public void setBeställningID(int beställningID) {
        this.beställningID = beställningID;
    }
    
}