package DbModell;

import java.util.*;

public class Modell {
    
    private int modellID;
    private String namn;
    private int pris;
    private String märke;
    private List<String> kategorier;

    public Modell(int modellID, String namn, int pris, String märke) {
        this.modellID = modellID;
        this.namn = namn;
        this.pris = pris;
        this.märke = märke;
        this.kategorier = new ArrayList<>();
    }
    
    public Modell() {
    }

    public int getModellID() {
        return modellID;
    }

    public void setModellID(int modellID) {
        this.modellID = modellID;
    }
    
    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }

    public String getMärke() {
        return märke;
    }

    public void setMärke(String märke) {
        this.märke = märke;
    }

    public List<String> getKategorier() {
        return kategorier;
    }

    public void setKategorier(List<String> kategorier) {
        this.kategorier = kategorier;
    }

}