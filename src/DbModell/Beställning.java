package DbModell;

import java.time.LocalDateTime;
import java.util.*;

public class Beställning {

    private int ID;
    private LocalDateTime datum;
    private boolean expiderad;
    private List<Sko> skor;

    public Beställning() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
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