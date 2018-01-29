package skobutik;

import DbModell.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Anslutning {

    LogInfo logInfo;
    Clone clone;

    public Anslutning() {
        logInfo = new LogInfo();
        clone = new Clone();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<String> getAllaOrter(){
        List<String> allaOrter = new ArrayList<>();
        String ortQuery = "select * from ort";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(ortQuery);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                allaOrter.add(rs.getString("namn"));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaOrter;
    }
    
    public Ort getSpecificOrt(int ID){
        String query = "select * from ort where id = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, String.valueOf(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return new Ort(rs.getString("namn"));
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Ort();
    }
    
    public List<Kund> getAllaKunder(){
        List<Kund> getAllaKunder = new ArrayList<>();
        String ortQuery = "select * from kund";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(ortQuery);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                Kund tempKund = new Kund(rs.getString("namn"), rs.getString("lösenord"), getSpecificOrt(rs.getInt("ortID")).getNamn());
                tempKund.setBeställningar(getSpecifikKundsBeställningar(rs.getInt("ID")));
                getAllaKunder.add(tempKund);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return getAllaKunder;
    }
        
    public Kund getSpecificKund(String namnId){
        String query = "select * from kund where namn like ?";
        if(isInteger(namnId))
            query = "select * from kund where ID = ?";
        Kund tempKund;
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, (namnId));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                tempKund = new Kund(rs.getString("namn"), rs.getString("lösenord"), getSpecificOrt(rs.getInt("ortID")).getNamn());
                tempKund.setBeställningar(getSpecifikKundsBeställningar(rs.getInt("ID")));
                return tempKund;
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Kund();
    }
    
    public List<String> getAllaMärken(){
        List<String> allaMärken = new ArrayList<>();
        String query = "select * from märke";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                allaMärken.add(rs.getString("namn"));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaMärken;
    }
    
    public String getSpecificMärke(int ID){
        String query = "select * from Märke where id = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, String.valueOf(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getString("namn");
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }
    
    public List<String> getAllaKategorier(){
        List<String> allaKategorier = new ArrayList<>();
        String query = "select * from kategori";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                allaKategorier.add(rs.getString("namn"));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaKategorier;
    }
    
    public String getSpecificKategori(int ID){
        String query = "select * from kategori where id = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, String.valueOf(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getString("namn");
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }
    
    public List<String> getAllaKategorierFörSpecifikModell(int modellID){
        List<String> kategorierFörSpecifikModell = new ArrayList<>();
        List<Integer> kategoriIDs = new ArrayList<>();
        String query1 = "select * from skotyp";
        String query2 = "select * from kategori";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt1 = con.prepareStatement(query1);
            ResultSet rs1 = stmt1.executeQuery();
            PreparedStatement stmt2 = con.prepareStatement(query2);
            ResultSet rs2 = stmt2.executeQuery();
        ){
            while(rs1.next()){
                if (rs1.getInt("modellID") == modellID)
                    kategoriIDs.add(rs1.getInt("kategoriID"));
            }
            while(rs2.next()){
                if (kategoriIDs.contains(rs2.getInt("ID")))
                    kategorierFörSpecifikModell.add(rs2.getString("namn"));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return kategorierFörSpecifikModell;
    }
    
    public List<Integer> getAllaStorlekar(){
        List<Integer> allaStorlekar = new ArrayList<>();
        String query = "select * from storlek";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                allaStorlekar.add(rs.getInt("nummer"));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaStorlekar;
    }
    
    public int getSpecificStorlek(int ID){
        String query = "select * from storlek where id = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, String.valueOf(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getInt("nummer");
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    public List<String> getAllaFärger(){
        List<String> allaFärger = new ArrayList<>();
        String query = "select * from färg";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                allaFärger.add(rs.getString("namn"));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaFärger;
    }
    
    public String getSpecificFärg(int ID){
        String query = "select * from färg where id = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, String.valueOf(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getString("namn");
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }
    
    public List<Modell> getAllaModeller(){
        List<Modell> allaModeller = new ArrayList<>();
        String query1 = "select * from modell";
        List<Integer[]> coupling = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt1 = con.prepareStatement(query1);
            ResultSet rs = stmt1.executeQuery();
        ){
            while(rs.next()){
                int tempID = rs.getInt("ID");
                Modell tempModell = new Modell(rs.getString("namn"),rs.getInt("pris"),getSpecificMärke(rs.getInt("märkeID")));
                for (String string : getAllaKategorierFörSpecifikModell(tempID)) {
                    tempModell.getKategorier().add(string);
                }
                allaModeller.add(tempModell);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaModeller;
    }
    
    public Modell getSpecificModell(int ID){
        Modell theModell;
        String query = "select * from modell where id = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, String.valueOf(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                theModell = new Modell(rs.getString("namn"), rs.getInt("pris"), getSpecificMärke(rs.getInt("märkeID")));
                for (String string : getAllaKategorierFörSpecifikModell(ID)) {
                    theModell.getKategorier().add(string);
                }
                return theModell;
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Modell();
    }
    
    public List<Sko> getAllaSkor(){
        List<Sko> allaSkor = new ArrayList<>();
        String query = "select * from sko";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                allaSkor.add(new Sko(getSpecificStorlek(rs.getInt("storlekID")), getSpecificFärg(rs.getInt("färgID")), getSpecificLagerStatus(rs.getInt("ID")), getSpecificModell(rs.getInt("modellID"))));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaSkor;
    }
    
    public Sko getSpecificSko(int ID){
        String query = "select * from sko where id = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, String.valueOf(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return new Sko(getSpecificStorlek(rs.getInt("storlekID")), getSpecificFärg(rs.getInt("färgID")), getSpecificLagerStatus(rs.getInt("ID")), getSpecificModell(rs.getInt("modellID")));
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return new Sko();
    }
    
    public int getSpecificLagerStatus(int skoID){
        String query = "select * from lagerstatus where skoID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, String.valueOf(skoID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getInt("antal");
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    public List<Sko> getAllaSkorISpecifikBeställning(int beställningID){
        List<Sko> allaSkorISpecifikBeställning = new ArrayList<>();
        List<Integer> skoIds = new ArrayList<>();
        String query1 = "select * from köp where beställningID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query1);
        ){
            stmt.setString(1, String.valueOf(beställningID));
            ResultSet rs1 = stmt.executeQuery();
            while(rs1.next()){
                skoIds.add(rs1.getInt("skoID"));
            }
            for (Integer skoId : skoIds) {
                allaSkorISpecifikBeställning.add(getSpecificSko(skoId));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaSkorISpecifikBeställning;
    }
    
    public List<Beställning> getAllaBeställningar(){
        List<Beställning> allaBeställningar = new ArrayList<>();
        String kundQuery = "select * from beställning";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(kundQuery);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                int tempID = rs.getInt("ID");
                Beställning tempBeställning = new Beställning("1999", rs.getBoolean("expiderad"));
                for (Sko sko : getAllaSkorISpecifikBeställning(tempID)) {
                    tempBeställning.getSkor().add(sko);
                }
                allaBeställningar.add(tempBeställning);
            }
                
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaBeställningar;
    }
    
    public List<Beställning> getSpecifikKundsBeställningar(int kundID){
        List<Beställning> allaBeställningar = new ArrayList<>();
        String kundQuery = "select * from beställning where kundID = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(kundQuery);
        ){
            stmt.setString(1, String.valueOf(kundID));
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                int tempID = rs.getInt("ID");
                Beställning tempBeställning = new Beställning("1999", rs.getBoolean("expiderad"));
                for (Sko sko : getAllaSkorISpecifikBeställning(tempID)) {
                    tempBeställning.getSkor().add(sko);
                }
                allaBeställningar.add(tempBeställning);
            }
                
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaBeställningar;
    }
    
    public Beställning getSpecificBeställning(int ID){
        Beställning theBeställning;
        String query = "select * from beställning where id = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, String.valueOf(ID));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                int tempID = rs.getInt("ID");
                theBeställning = new Beställning("1999", rs.getBoolean("expiderad"));
                for (Sko sko : getAllaSkorISpecifikBeställning(tempID)) {
                    theBeställning.getSkor().add(sko);
                }
                return theBeställning;
            }
        }   catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return new Beställning();
    }
    
    private boolean isInteger(String s){
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException n) {
            return false;
        }
        return true;
    }
}