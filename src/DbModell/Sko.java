package DbModell;

public class Sko extends Modell{
    
    private int storlek;
    private String färg;
    private int antal;

    public Sko(int storlek, String färg, int antal, Modell modell) {
        super(modell.getNamn(), modell.getPris(), modell.getMärke());
        this.storlek = storlek;
        this.färg = färg;
        this.antal = antal;
    }

    public Sko() {
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