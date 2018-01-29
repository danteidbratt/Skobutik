package DbModell;

import java.util.*;

public class Kund {

    private String namn;
    private String lösenord;
    private String ort;
    private List<Beställning> beställningar;

    public Kund(String namn, String lösenord, String ort) {
        this.namn = namn;
        this.lösenord = lösenord;
        this.ort = ort;
        this.beställningar = new ArrayList<>();
    }

    public Kund() {
    }
    

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getLösenord() {
        return lösenord;
    }

    public void setLösenord(String lösenord) {
        this.lösenord = lösenord;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public List<Beställning> getBeställningar() {
        return beställningar;
    }

    public void setBeställningar(List<Beställning> beställningar) {
        this.beställningar = beställningar;
    }
}