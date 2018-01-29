package skobutik;

import java.sql.SQLException;
import java.util.*;

public class Skobutik {
    
    Scanner scanner;
    Controller controller;

    public Skobutik() {
        scanner = new Scanner(System.in);
        controller = new Controller();
    }
    
    public void start() throws SQLException, ClassNotFoundException{
        while(true){
            System.out.println("Vad vill du göra?\n"
                    + "\n[1] Visa kundinformation"
                    + "\n[2] Visa Produkter per Kategori"
                    + "\n[3] Lägg beställning"
                    + "\n[0] Avsluta\n");

            switch(scanner.nextLine()){
                case "1":
                    visaKundInfo();
                    break;
                case "2":
                    visaProdukter();
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
    
    public void visaKund(){
        System.out.println("Ange KundID:");
        System.out.println(controller.getKundNamn(scanner.nextLine()));
    }
    
    public void visaKundTotal(){
        System.out.println("Ange kundens namn/ID:\n");
        controller.getKundlistaMedTotal(scanner.nextLine())
                .entrySet()
                .stream()
                .forEach(k -> System.out.print("\nNamn: " + k.getKey() + 
                                             "\tTotal: " + k.getValue() + "\n"));
        System.out.println();
    }
    
//    public void visaVilkaSomHarBeställtEnModell(){
//        System.out.println("Ange modellens namn:");
//        controller.orderedModell(scanner.nextLine()).forEach(n -> System.out.println(n));
//    }
    
    public void visaKundInfo(){
        
    }
    
    public void visaProdukter(){
        
    }
    
    public void läggBeställning(){
        
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Skobutik skobutik = new Skobutik();
        skobutik.visaKundTotal();
    }
}