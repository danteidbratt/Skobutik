package DbModell;

import java.time.LocalDateTime;
import java.util.*;

public class Slutilager {
    
    private Sko sko;
    private List<LocalDateTime> datum;

    public Sko getSko() {
        return sko;
    }

    public void setSko(Sko sko) {
        this.sko = sko;
    }

    public List<LocalDateTime> getDatum() {
        return datum;
    }

    public void setDatum(List<LocalDateTime> datum) {
        this.datum = datum;
    }

}