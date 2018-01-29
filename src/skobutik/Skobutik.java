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
        String input;
        skor = controller.getAllaSkor();
        System.out.println("\nVälj Modell:\n");
        Map<Integer,String> modeller = new HashMap<>();
        skor.forEach((t) -> {
            if(!modeller.containsValue(t.getNamn()))
                modeller.put(t.getModellID(), t.getNamn());
        });
        modeller.entrySet().stream().forEach(m -> System.out.println("[" + m.getKey() + "] " + m.getValue()));
//        controller.getAllaModellNamn()
//                .entrySet()
//                .stream()
//                .forEach(m -> System.out.println("[" + m.getKey() + "] " + m.getValue()));
//        System.out.println();
//        input = scanner.nextLine();
//        System.out.println("\nVälj Storlek:\n");
//        controller.getStorlekarFörModell(input).forEach(s -> System.out.println(s));
//        System.out.println();
//        input = scanner.nextLine();
        
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