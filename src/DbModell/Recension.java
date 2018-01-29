package DbModell;

import java.time.LocalDateTime;

public class Recension {

    private final int id;
    private String kommentar;
    private Betyg betyg;
    private LocalDateTime datum;
    private Kund kund;
    private Modell modell;

    public Recension(int id, String kommentar, Betyg betyg, LocalDateTime datum, Kund kund, Modell modell) {
        this.id = id;
        this.kommentar = kommentar;
        this.betyg = betyg;
        this.datum = datum;
        this.kund = kund;
        this.modell = modell;
    }

    public int getId() {
        return id;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public Betyg getBetyg() {
        return betyg;
    }

    public void setBetyg(Betyg betyg) {
        this.betyg = betyg;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public Kund getKund() {
        return kund;
    }

    public void setKund(Kund kund) {
        this.kund = kund;
    }

    public Modell getModell() {
        return modell;
    }

    public void setModell(Modell modell) {
        this.modell = modell;
    }
    
}