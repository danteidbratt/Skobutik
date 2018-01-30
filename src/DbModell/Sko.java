package DbModell;

public class Sko extends Modell{
    
    private int skoID;
    private int storlek;
    private String färg;
    private int antal;

    public int getID() {
        return skoID;
    }

    public void setID(int ID) {
        this.skoID = ID;
    }
    
    public int getStorlek() {
        return storlek;
    }

    public void setStorlek(int storlek) {
        this.storlek = storlek;
    }

    public String getFärg() {
        return färg;
    }

    public void setFärg(String färg) {
        this.färg = färg;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }
    
}