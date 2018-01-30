package skobutik;

import ViewModels.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Skobutik {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    Scanner scanner;
    Controller controller;
    String kundID;
    List<ViewSko> skor;
    List<ViewBeställning> beställningar;

    public Skobutik() {
        scanner = new Scanner(System.in);
        controller = new Controller();
        skor = new ArrayList<>();
        beställningar = new ArrayList<>();
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
    }
    
    public void visaProdukterPerKategori(){
        System.out.println();
        controller.getModellerPerKategori()
                .entrySet()
                .stream()
                .forEach((t) -> {
                    System.out.println(t.getKey() + ": " + t.getValue());
                });
    }
    
    public void läggBeställning(){
        String inputNamn;
        String inputStorlek;
        String inputFärg;
        String inputSkoID;
        String inputBeställningID = null;
        
        Map<Integer,String> alternatives = new HashMap<>();
        List<String> stuff;
        skor = controller.getAllaSkor();
        skor = skor.stream().filter(s -> s.getAntal() > 0).collect(Collectors.toList());
        stuff = skor.stream()
                .map(ViewSko::getNamn)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
        System.out.println("\nVälj Modell:\n");
        alternatives = generateMapFromList(stuff);
        printAlternatives(alternatives);
        System.out.println();
        inputNamn = alternatives.get(Integer.parseInt(scanner.nextLine()));
        skor = skor.stream()
                .filter(s -> s.getNamn().equalsIgnoreCase(inputNamn))
                .collect(Collectors.toList());
        stuff = skor.stream()
                .map(t -> String.valueOf(t.getStorlek()))
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
        Collections.sort(stuff);
        alternatives = generateMapFromList(stuff);
        System.out.println("\nVälj Storlek:\n");
        printAlternatives(alternatives);
        System.out.println();
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
        System.out.println();
        inputFärg = alternatives.get(Integer.parseInt(scanner.nextLine()));
        skor = skor.stream()
                .filter(s -> s.getFärg().equals(inputFärg))
                .collect(Collectors.toList());
        inputSkoID = String.valueOf(skor.get(0).getSkoID());
        System.out.println("\nDitt val:\n" +
                           "\nModel:  \t" + inputNamn +
                           "\nStorlek:\t" + inputStorlek +
                           "\nFärg:   \t" + inputFärg +
                           "\n\n[1] Bekräfta" +
                           "\n[2] Avbryt\n");
        if(scanner.nextLine().trim().equals("1")) {
            beställningar = controller.getBeställningarIKund(String.valueOf(kundID));
            stuff = beställningar.stream()
                    .map(ViewBeställning::getDatum)
                    .map(d -> d.toString()).map(s -> s.replace('T', ' '))
                    .collect(Collectors.toList());
            System.out.println("\nVälj Beställning:\n");
            alternatives = generateMapFromList(stuff);
            printAlternatives(alternatives);
            System.out.println("[" + (beställningar.size()+1) + "] Ny Beställning\n");
            int lalala = Integer.parseInt(scanner.nextLine());
            for (ViewBeställning b : beställningar) {
                if ((lalala < alternatives.size()+1) && b.getDatum().compareTo(LocalDateTime.parse(alternatives.get(lalala), formatter)) == 0){
                    inputBeställningID = String.valueOf(b.getID());
                    break;
                }
            }
            if(controller.placeOrder(inputSkoID, inputBeställningID, kundID) == 0)
                System.out.println("\nTack för din beställning");
            else
                System.out.println("\nVänligen försök igen");
        }
    }
    
    public boolean login(){
        String tempNamn;
        String tempLösen;
        System.out.print("- Logga in -\n\nAnge namn:\t");
        tempNamn = scanner.nextLine().trim();
        System.out.print("\nAnge lösenord:\t");
        tempLösen = scanner.nextLine().trim();
        kundID = String.valueOf(controller.login(tempNamn, tempLösen));
        if(Integer.parseInt(kundID) > 0) {
            System.out.println("\n----------------****************----------------");
            return true;
        }
        return false;
    }
    
    public void printAlternatives(Map<Integer,String> alternatives){
        alternatives.entrySet().stream().forEach(m -> System.out.println("[" + m.getKey() + "] " + m.getValue()));
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