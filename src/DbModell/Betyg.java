package DbModell;

public class Betyg {
    
    private final int id;
    private String Omdöme;
    private int poäng;

    public Betyg(int id, String Omdöme, int poäng) {
        this.id = id;
        this.Omdöme = Omdöme;
        this.poäng = poäng;
    }

    public int getId() {
        return id;
    }
    
    public String getOmdöme() {
        return Omdöme;
    }

    public void setOmdöme(String Omdöme) {
        this.Omdöme = Omdöme;
    }

    public int getPoäng() {
        return poäng;
    }

    public void setPoäng(int poäng) {
        this.poäng = poäng;
    }
    
}