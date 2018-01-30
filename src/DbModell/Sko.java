package DbModell;

public class Sko extends Modell{
    
    private int skoID;
    private Storlek storlek;
    private Färg färg;
    private int antal;

    public int getSkoID() {
        return skoID;
    }

    public void setSkoID(int skoID) {
        this.skoID = skoID;
    }

    public Storlek getStorlek() {
        return storlek;
    }

    public void setStorlek(Storlek storlek) {
        this.storlek = storlek;
    }

    public Färg getFärg() {
        return färg;
    }

    public void setFärg(Färg färg) {
        this.färg = färg;
    }
    
    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }
    
}