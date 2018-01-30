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
    Map<Integer,String> alternatives;
    List<String> stuff;
    int input;

    public Skobutik() {
        scanner = new Scanner(System.in);
        controller = new Controller();
        skor = new ArrayList<>();
        beställningar = new ArrayList<>();
        input = 0;
    }
    
    public void start() throws SQLException, ClassNotFoundException{
        while(true){
            System.out.println("\nVad vill du göra?\n"
                    + "\n[1] Visa kundinformation"
                    + "\n[2] Visa Produkter per Kategori"
                    + "\n[3] Lägg beställning"
                    + "\n[4] Logga ut"
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
                case "4":
                    System.out.println("\n----------------****************----------------");
                    return;
                case "0":
                    System.exit(0);
                    break;
                default:
                    System.out.println("\nOgiltig inmatning");
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
        String price;
        
        skor = controller.getAllaSkor();
        filterByStock();
        System.out.println("\nVälj Modell:\n");
        alternatives = generateMapFromList(stuff);
        printAlternatives(alternatives);
        System.out.println();
        if(!isValidInput(scanner.nextLine()))
            return;
        
        inputNamn = alternatives.get(input);
        filterByName(inputNamn);
        Collections.sort(stuff);
        System.out.println("\nVälj Storlek:\n");
        alternatives = generateMapFromList(stuff);
        printAlternatives(alternatives);
        System.out.println();
        if(!isValidInput(scanner.nextLine()))
            return;
        
        inputStorlek = alternatives.get(input);
        filterBySize(inputStorlek);
        System.out.println("\nVälj Färg:\n");
        alternatives = generateMapFromList(stuff);
        printAlternatives(alternatives);
        System.out.println();
        if(!isValidInput(scanner.nextLine()))
            return;
        
        inputFärg = alternatives.get(input);
        filterByColor(inputFärg);
        inputSkoID = String.valueOf(skor.get(0).getSkoID());
        price = String.valueOf(skor.get(0).getPris());
        System.out.println("\nDitt val:\n" +
                           "\nModel:  \t" + inputNamn +
                           "\nStorlek:\t" + inputStorlek +
                           "\nFärg:   \t" + inputFärg +
                           "\nPris:   \t" + price + ":-" +
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
            if(!isValidInput(scanner.nextLine()))
                return;
            for (ViewBeställning b : beställningar) {
                if (b.getDatum().compareTo(LocalDateTime.parse(alternatives.get(input), formatter)) == 0){
                    inputBeställningID = String.valueOf(b.getID());
                    break;
                }
            }
            System.out.println("\n" + controller.placeOrder(inputSkoID, inputBeställningID, kundID));
        }
    }
    
    private void filterByStock(){
        skor = skor.stream().filter(s -> s.getAntal() > 0).collect(Collectors.toList());
        stuff = skor.stream()
                .map(ViewSko::getNamn)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }
    
    private void filterByName(String input){
        skor = skor.stream()
                .filter(s -> s.getNamn().equalsIgnoreCase(input))
                .collect(Collectors.toList());
        stuff = skor.stream()
                .map(t -> String.valueOf(t.getStorlek()))
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }
    
    private void filterBySize(String input){
        skor = skor.stream()
                .filter(s -> s.getStorlek() == Integer.parseInt(input))
                .collect(Collectors.toList());
        stuff = skor.stream()
                .map(ViewSko::getFärg)
                .collect(Collectors.toSet())
                .stream()
                .collect(Collectors.toList());
    }
    
    private void filterByColor(String input){
        skor = skor.stream()
                .filter(s -> s.getFärg().equals(input))
                .collect(Collectors.toList());
    }
    
    private boolean isValidInput(String input){
        try{
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            System.out.println("\nOgiltig inmatning");
            return false;
        }
        if(Integer.parseInt(input) > alternatives.size()){
            System.out.println("\nOgiltig inmatning");
            return false;
        }
        this.input = Integer.parseInt(input);
        return true;
    }
    
    public void printAlternatives(Map<Integer,String> alternatives){
        alternatives.entrySet()
                .stream()
                .forEach(m -> System.out.println("[" + m.getKey() + "] " + m.getValue()));
    }
    
    public Map<Integer,String> generateMapFromList(List stuff){
        Map<Integer,String> theMap = new HashMap<>();
        for (int i = 0; i < stuff.size(); i++) {
            theMap.put(i+1, (String)stuff.get(i));
        }
        stuff.clear();
        return theMap;
    }
    
    public boolean login(){
        String tempNamn;
        String tempLösen;
        System.out.print("\n- Logga in -\n\nAnge namn:\t");
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
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Skobutik skobutik = new Skobutik();
        System.out.println("--- Dantes OfflineSkor ---");
        while(true){
            if (skobutik.login())
                skobutik.start();
            else
                System.out.println("\nFelaktigt namn eller lösenord");
        }
    }
}