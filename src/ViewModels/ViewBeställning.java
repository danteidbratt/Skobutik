package ViewModels;

import java.time.*;

public class ViewBeställning {

    private int ID;
    private LocalDateTime datum;

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
    
}