package skobutik;

import DbModell.*;
import ViewModels.ViewBeställning;
import ViewModels.ViewSko;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    private final Anslutning anslutning = new Anslutning();
    
    public Map<String, Integer> getKundlistaMedTotal(String namnID){
        Map<String, Integer> kundlista = new HashMap<>();
        anslutning.getKunder(namnID).forEach(k -> kundlista.put(k.getNamn(), getKundTotal(String.valueOf(k.getID()))));
        return kundlista;
    }
    
    private int getKundTotal(String namnID){
        List<Integer> total = new ArrayList<>();
        anslutning.getBeställningarIKund(String.valueOf(anslutning.getKunder(namnID).get(0).getID())).forEach((b) -> {
            anslutning.getAllaSkorIBeställning(b.getID()).forEach((s) -> {
                total.add(s.getPris());
            });
        });
        return total.stream().reduce(0, (u,e) -> u+e);
    }
    
    public Map<String, List<String>> getModellerPerKategori(){
        Map<String, List<String>> modellerPerKategori = new HashMap<>();
        anslutning.getKategorier("").forEach((t) -> {
            modellerPerKategori.put(t.getNamn(), new ArrayList<>());
        });
        anslutning.getModeller("").forEach((t) -> {
            t.getKategorier().forEach((s) -> {
                modellerPerKategori.get(s.getNamn()).add(t.getNamn());
            });
        });
        return modellerPerKategori;
    }
    
    public List<ViewSko> getAllaSkor(){
        List<ViewSko> allaSkor = new ArrayList<>();
        for (Sko s : anslutning.getSkor("")) {
            ViewSko temp = new ViewSko();
            temp.setAntal(s.getAntal());
            temp.setFärg(s.getFärg());
            temp.setKategorier(s.getKategorier());
            temp.setModellID(s.getModellID());
            temp.setMärke(s.getMärke());
            temp.setNamn(s.getNamn());
            temp.setPris(s.getPris());
            temp.setSkoID(s.getID());
            temp.setStorlek(s.getStorlek());
            allaSkor.add(temp);
        }
        return allaSkor;
    }
            
    public Map<Integer, String> getAllaModellNamn(){
        return anslutning.getModeller("").stream().collect(Collectors.toMap(t -> t.getModellID(), t -> t.getNamn()));
    }
    
    public Set<Integer> getStorlekarFörModell(String modellID){
        return anslutning.getSkorAvSpecifikModell(modellID).stream().map(t -> t.getStorlek()).collect(Collectors.toSet());
    }
    
    public int login(String namn, String lösenord){
        if(anslutning.getKunder(namn).get(0).getLösenord().equalsIgnoreCase(lösenord))
            return anslutning.getKunder(namn).get(0).getID();
        return 0;
    }
    
    public List<ViewBeställning> getBeställningarIKund(String kundID){
        List<ViewBeställning> beställningarIKund = new ArrayList<>();
        for (Beställning beställning : anslutning.getBeställningarIKund(kundID)) {
            if(!beställning.isExpiderad()){
                ViewBeställning temp = new ViewBeställning();
                temp.setID(beställning.getID());
                temp.setDatum(beställning.getDatum());
                beställningarIKund.add(temp);
            }
        }
        return beställningarIKund;
    }
    
    public int placeOrder(String skoID, String beställningID, String kundID){
        return anslutning.addToCart(skoID, beställningID, kundID);
    }
}