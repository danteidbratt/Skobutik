package DbModell;

import java.time.LocalDateTime;

public class Slutilager {
    
    private final int id;
    private final Sko sko;
    private final LocalDateTime datum;

    public Slutilager(int id, Sko sko, LocalDateTime datum) {
        this.id = id;
        this.sko = sko;
        this.datum = datum;
    }

    public int getId() {
        return id;
    }
    
    public Sko getSko() {
        return sko;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

}