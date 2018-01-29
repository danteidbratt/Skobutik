package skobutik;

import DbModell.*;
import java.util.*;

public class Controller {

    private final Anslutning anslutning = new Anslutning();
    
    public Map<String, Integer> getKundlistaMedTotal(String namnID){
        Map<String, Integer> kundlista = new HashMap<>();
        anslutning.getKunder(namnID).forEach(k -> kundlista.put(k.getNamn(), getKundTotal(String.valueOf(k.getID()))));
        return kundlista;
    }
    
    public String getKundNamn(String namnID){
        return anslutning.getKunder(namnID).get(0).getNamn();
    }
    
    public int getKundTotal(String namnID){
        int total = 0;
        for (Beställning beställning : anslutning.getBeställningarIKund(anslutning.getKunder(namnID).get(0).getID())) {
            for (Sko sko : anslutning.getAllaSkorIBeställning(beställning.getID())) {
                total += sko.getPris();
            }
        }
        return total;
    }
    
//    public List<String> orderedModell(String modellNamn){
//        List<String> kundNamn = new ArrayList<>();
//        for (Kund kund : anslutning.getAllaKunder()) {
//            for (Beställning beställning : kund.getBeställningar()) {
//                for (Sko sko : beställning.getSkor()) {
//                    if(sko.getNamn().equalsIgnoreCase(modellNamn)){
//                        if (!kundNamn.contains(kund.getNamn())) {
//                            kundNamn.add(kund.getNamn());
//                        }
//                    }
//                }
//            }
//        }
//        return kundNamn;
//    }
    
    private boolean isEmpty(String s){
        return s.length() == 0;
    }
}