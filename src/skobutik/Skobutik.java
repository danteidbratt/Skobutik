package skobutik;

import ViewModels.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Skobutik {
    
    Scanner scanner;
    Controller controller;
    int kundID;
    List<ViewSko> skor;

    public Skobutik() {
        scanner = new Scanner(System.in);
        controller = new Controller();
        skor = new ArrayList<>();
    }
    
    public void start() throws SQLException, ClassNotFoundException{
        while(true){
            System.out.println("\nVad vill du göra?\n"
                    + "\n[1] Visa kundinformation"
                    + "\n[2] Visa Produkter per Kategori"
                    + "\n[3] Lägg beställning"
                    + "\n[0] Avsluta\n");

            switch(scanner.nextLine()){
                case "1":
                    visaKundTotal();
                    break;
                case "2":
                    visaProdukterPerKategori();
                    break;
                case "3":
                    läggBeställning();
                    break;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Ogiltig inmatning");
                    break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void visaKundTotal(){
        System.out.println("\nAnge kundens namn/ID:\n");
        controller.getKundlistaMedTotal(scanner.nextLine())
                .entrySet()
                .stream()
                .forEach(k -> System.out.print("\nNamn: " + k.getKey() + 
                                             "\tTotal: " + k.getValue() + "\n"));
        System.out.println();
    }
    
    public void visaProdukterPerKategori(){
        controller.getModellerPerKategori()
                .entrySet()
                .stream()
                .forEach((t) -> {
                    System.out.print("\n" + t.getKey() + ":\t");
                    t.getValue().forEach((s) -> {
                        System.out.print(s + ", ");
                    });
                });
    }
    
    public void läggBeställning(){
        Map<Integer,String> alternatives = new HashMap<>();
        List<String> stuff = new ArrayList<>();
        String inputNamn;
        String inputStorlek;
        String inputFärg;
        skor = controller.getAllaSkor();
        skor = skor.stream().filter(s -> s.getAntal() > 0).collect(Collectors.toList());
        System.out.println("\nVälj Modell:\n");
        for (ViewSko vs : skor) {
            if(!alternatives.containsValue(vs.getNamn()))
                alternatives.put(vs.getModellID(), vs.getNamn());
        }
        printAlternatives(alternatives);
        inputNamn = alternatives.get(Integer.parseInt(scanner.nextLine()));
        System.out.println("\nVälj Storlek:\n");
        stuff = skor.stream()
                .filter(s -> s.getNamn().equalsIgnoreCase(inputNamn))
                .collect(Collectors.toList())
                .stream()
                .map(t -> String.valueOf(t.getStorlek()))
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
        Collections.sort(stuff);
        alternatives = generateMapFromList(stuff);
        printAlternatives(alternatives);
        inputStorlek = alternatives.get(Integer.parseInt(scanner.nextLine()));
        skor = skor.stream()
                .filter(s -> s.getStorlek() == Integer.parseInt(inputStorlek))
                .collect(Collectors.toList());
        stuff = skor.stream()
                .map(ViewSko::getFärg)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
        System.out.println("\nVälj Färg:\n");
        alternatives = generateMapFromList(stuff);
        printAlternatives(alternatives);
        inputFärg = alternatives.get(Integer.parseInt(scanner.nextLine()));
        System.out.println("\nDitt val:\n" +
                           "\nModel:  \t" + inputNamn +
                           "\nStorlek:\t" + inputStorlek +
                           "\nFärg:   \t" + inputFärg +
                           "\n\n[1] Bekräfta" +
                           "\n[2] Avbryt");
    }
    
    public boolean login(){
        String tempNamn;
        String tempLösen;
        System.out.print("\nAnge namn:\t");
        tempNamn = scanner.nextLine().trim();
        System.out.print("\nAnge lösenord:\t");
        tempLösen = scanner.nextLine().trim();
        kundID = controller.login(tempNamn, tempLösen);
        if(kundID > 0)
            return true;
        return false;
    }
    
    public void printAlternatives(Map<Integer,String> alternatives){
        alternatives.entrySet().stream().forEach(m -> System.out.println("[" + m.getKey() + "] " + m.getValue()));
        System.out.println();
    }
    
    public Map<Integer,String> generateMapFromList(List stuff){
        Map<Integer,String> theMap = new HashMap<>();
        for (int i = 0; i < stuff.size(); i++) {
            theMap.put(i+1, (String)stuff.get(i));
        }
        stuff.clear();
        return theMap;
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Skobutik skobutik = new Skobutik();
        while(true){
            if (skobutik.login())
                skobutik.start();
            else
                System.out.println("\nFelaktigt namn eller lösenord");
        }
    }
}