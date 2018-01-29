package DbModell;

import java.time.LocalDateTime;
import java.util.*;

public class Beställning {

    private int ID;
    private String datum;
    private boolean expiderad;
    private List<Sko> skor;

    public Beställning(int ID, String datum, boolean expiderad) {
        this.ID = ID;
        this.datum = datum;
        this.expiderad = expiderad;
        this.skor = new ArrayList<>();
    }

    public Beställning() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
    
    public boolean isExpiderad() {
        return expiderad;
    }

    public void setExpiderad(boolean expiderad) {
        this.expiderad = expiderad;
    }

    public List<Sko> getSkor() {
        return skor;
    }

    public void setSkor(List<Sko> Skor) {
        this.skor = Skor;
    }

}