
package ViewModels;

import java.util.List;

public class ViewSko {
    
    private final int skoID;
    private final int storlek;
    private final String färg;
    private final int antal;
    private final int modellID;
    private final String namn;
    private final int pris;
    private final String märke;
    private final List<String> kategorier;

    public ViewSko(int skoID, int storlek, String färg, int antal, int modellID, String namn, int pris, String märke, List<String> kategorier) {
        this.skoID = skoID;
        this.storlek = storlek;
        this.färg = färg;
        this.antal = antal;
        this.modellID = modellID;
        this.namn = namn;
        this.pris = pris;
        this.märke = märke;
        this.kategorier = kategorier;
    }
    
    public int getSkoID() {
        return skoID;
    }

    public int getStorlek() {
        return storlek;
    }

    public String getFärg() {
        return färg;
    }

    public int getAntal() {
        return antal;
    }

    public int getModellID() {
        return modellID;
    }

    public String getNamn() {
        return namn;
    }

    public int getPris() {
        return pris;
    }

    public String getMärke() {
        return märke;
    }

    public List<String> getKategorier() {
        return kategorier;
    }
}