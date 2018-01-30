
package ViewModels;

import DbModell.Kategori;
import DbModell.Märke;
import java.util.List;

public class ViewSko {
    
    private int skoID;
    private int storlek;
    private String färg;
    private int antal;
    private int modellID;
    private String namn;
    private int pris;
    private Märke märke;
    private List<Kategori> kategorier;

    public int getSkoID() {
        return skoID;
    }

    public void setSkoID(int skoID) {
        this.skoID = skoID;
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