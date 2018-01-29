package skobutik;

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
        anslutning.getBeställningarIKund(anslutning.getKunder(namnID).get(0).getID()).forEach((b) -> {
            anslutning.getAllaSkorIBeställning(b.getID()).forEach((s) -> {
                total.add(s.getPris());
            });
        });
        return total.stream().reduce(0, (u,e) -> u+e);
    }
    
    public Map<String, List<String>> getModellerPerKategori(){
        Map<String, List<String>> modellerPerKategori = new HashMap<>();
        anslutning.getKategorier("").forEach((k) -> {
            modellerPerKategori.put(k, new ArrayList<>());
            anslutning.getModeller("").forEach((m) -> {
                m.getKategorier().forEach((mk) -> {
                    if(k.equals(mk))
                        modellerPerKategori.get(k).add(m.getNamn());
                });
            });
        });
        return modellerPerKategori;
    }
    
    public List<ViewSko> getAllaSkor(){
        List<ViewSko> allaSkor = new ArrayList<>();
        anslutning.getSkor("").forEach(s -> allaSkor.add(new ViewSko(s.getID(), s.getStorlek(), s.getFärg(), s.getAntal(),s.getModellID(), s.getNamn(), s.getPris(), s.getMärke(), s.getKategorier())));
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
}