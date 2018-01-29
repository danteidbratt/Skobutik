package skobutik;

import DbModell.*;
import java.util.*;

public class Controller {

    private final Anslutning anslutning = new Anslutning();
    
    public Map<String, Integer> getKundlistaMedTotal(String namnID){
        Map<String, Integer> kundlista = new HashMap<>();
        if(!isEmpty(namnID)){
            kundlista.put(getKundNamn(namnID), getKundTotal(namnID));
        }
        else {
            for (Kund kund : anslutning.getAllaKunder()) {
                kundlista.put(kund.getNamn(), getKundTotal(kund.getNamn()));
            }
        }
        return kundlista;
    }
    
    public String getKundNamn(String namnID){
        return anslutning.getSpecificKund(namnID).getNamn();
    }
    
    public int getKundTotal(String namnID){
        int total = 0;
        for (Beställning beställning : anslutning.getSpecifikKundsBeställningar(anslutning.getSpecificKund(namnID).getID())) {
            for (Sko sko : anslutning.getAllaSkorISpecifikBeställning(beställning.getID())) {
                total += sko.getPris();
            }
        }
        return total;
    }
    
    public List<String> orderedModell(String modellNamn){
        List<String> kundNamn = new ArrayList<>();
        for (Kund kund : anslutning.getAllaKunder()) {
            for (Beställning beställning : kund.getBeställningar()) {
                for (Sko sko : beställning.getSkor()) {
                    if(sko.getNamn().equalsIgnoreCase(modellNamn)){
                        if (!kundNamn.contains(kund.getNamn())) {
                            kundNamn.add(kund.getNamn());
                        }
                    }
                }
            }
        }
        return kundNamn;
    }
    
    private boolean isEmpty(String s){
        return s.length() == 0;
    }
}