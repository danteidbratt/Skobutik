package DbModell;

public class Betyg {
    
    private int id;
    private String Omdöme;
    private int poäng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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