package ViewModels;

import DbModell.Kategori;
import DbModell.Märke;
import java.util.*;

public class ViewModell {
    
    private int modellID;
    private String namn;
    private int pris;
    private Märke märke;
    private List<Kategori> kategorier;

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

    public Märke getMärke() {
        return märke;
    }

    public void setMärke(Märke märke) {
        this.märke = märke;
    }

    public List<Kategori> getKategorier() {
        return kategorier;
    }

    public void setKategorier(List<Kategori> kategorier) {
        this.kategorier = kategorier;
    }

}