package skobutik;

import DbModell.*;
import java.util.*;

public class Clone {
    
    private final Map<Integer, Beställning> beställningar;
    private final Map<Integer, Betyg> betyg;
    private final Map<Integer, Färg> färger;
    private final Map<Integer, Kategori> kategorier;
    private final Map<Integer, Kund> kunder;
    private final Map<Integer, Lagerstatus> lagerstatus;
    private final Map<Integer, Modell> modeller;
    private final Map<Integer, Märke> märken;
    private final Map<Integer, Ort> orter;
    private final Map<Integer, Recension> recensioner;
    private final Map<Integer, Sko> skor;
    private final Map<Integer, Slutilager> slutilager; 
    private final Map<Integer, Storlek> storlekar;

    public Clone() {
        beställningar = new HashMap<>();
        betyg = new HashMap<>();
        färger = new HashMap<>();
        kategorier = new HashMap<>();
        kunder = new HashMap<>();
        lagerstatus = new HashMap<>();
        modeller = new HashMap<>();
        märken = new HashMap<>();
        orter = new HashMap<>();
        recensioner = new HashMap<>();
        skor = new HashMap<>();
        slutilager = new HashMap<>();
        storlekar = new HashMap<>();
    }

    public Map<Integer, Beställning> getBeställningar() {
        return beställningar;
    }

    public Map<Integer, Betyg> getBetyg() {
        return betyg;
    }

    public Map<Integer, Färg> getFärger() {
        return färger;
    }

    public Map<Integer, Kategori> getKategorier() {
        return kategorier;
    }

    public Map<Integer, Kund> getKunder() {
        return kunder;
    }

    public Map<Integer, Lagerstatus> getLagerstatus() {
        return lagerstatus;
    }

    public Map<Integer, Modell> getModeller() {
        return modeller;
    }

    public Map<Integer, Märke> getMärken() {
        return märken;
    }

    public Map<Integer, Ort> getOrter() {
        return orter;
    }

    public Map<Integer, Recension> getRecensioner() {
        return recensioner;
    }

    public Map<Integer, Sko> getSkor() {
        return skor;
    }

    public Map<Integer, Slutilager> getSlutilager() {
        return slutilager;
    }

    public Map<Integer, Storlek> getStorlekar() {
        return storlekar;
    }
    
}

//    public void putObject(Object object){
//        if (object instanceof Beställning)
//            beställningar.put(((Beställning) object).getId(), (Beställning) object);
//        else if (object instanceof Betyg)
//            betyg.put(((Betyg) object).getId(), (Betyg) object);
//        else if (object instanceof Färg)
//            färger.put(((Färg) object).getId(), (Färg) object);
//        else if (object instanceof Kategori)
//            kategorier.put(((Kategori) object).getId(), (Kategori) object);
//        else if (object instanceof Lagerstatus)
//            lagerstatus.put(((Lagerstatus) object).getId(), (Lagerstatus) object);
//        else if (object instanceof Modell)
//            modeller.put(((Modell) object).getId(), (Modell) object);
//        else if (object instanceof Märke)
//            märken.put(((Märke) object).getId(), (Märke) object);
//        else if (object instanceof Ort)
//            orter.put(((Ort) object).getId(), (Ort) object);
//        else if (object instanceof Recension)
//            recensioner.put(((Recension) object).getId(), (Recension) object);
//        else if (object instanceof Sko)
//            skor.put(((Sko) object).getId(), (Sko) object);
//        else if (object instanceof Slutilager)
//            slutilager.put(((Slutilager) object).getId(), (Slutilager) object);
//        else if (object instanceof Storlek)
//            storlekar.put(((Storlek) object).getId(), (Storlek) object);
//    }

//    public Beställning> getBeställning() {
//        return beställningar;
//    }