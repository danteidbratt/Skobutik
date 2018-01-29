package skobutik;

import DbModell.*;
import java.sql.*;
import java.time.LocalDateTime;
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
    
    public List<String> getOrter(String ID){
        List<String> allaOrter = new ArrayList<>();
        String query = "select * from ort";
        if (ID.length() > 0) {
            query = "select * from ort where ID = " + ID;
        }
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
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
    
    public List<Kund> getKunder(String namnID){
        List<Kund> allaKunder = new ArrayList<>();
        String ortQuery = "select * from kund";
        if(namnID.length() > 0){
            if(isInteger(namnID))
                ortQuery = "select * from kund where ID = ?";
            else 
                ortQuery = "select * from kund where namn like ?";
        }
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(ortQuery);
        ){
            if(namnID.length() > 0){
                stmt.setString(1, namnID);
            }
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Kund tempKund = new Kund(rs.getInt("ID"), rs.getString("namn"), rs.getString("lösenord"), getOrter(String.valueOf(rs.getInt("ortID"))).get(0));
                allaKunder.add(tempKund);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaKunder;
    }
        
    public List<String> getMärken(String ID){
        List<String> allaMärken = new ArrayList<>();
        String query = "select * from märke";
        if (ID.length() > 0) {
            query = "select * from märke where ID = " + ID;
        }
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
    
    public List<String> getKategorier(String ID){
        List<String> allaKategorier = new ArrayList<>();
        String query = "select * from kategori";
        if (ID.length() > 0) {
            query = "select * from kategori where ID = " + ID;
        }
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
    
    public List<String> getAllaKategorierFörSpecifikModell(int modellID){
        List<String> kategorierFörSpecifikModell = new ArrayList<>();
        List<Integer> kategoriIDs = new ArrayList<>();
        String query1 = "select * from skotyp";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt1 = con.prepareStatement(query1);
            ResultSet rs1 = stmt1.executeQuery();
        ){
            while(rs1.next()){
                if (rs1.getInt("modellID") == modellID)
                    kategoriIDs.add(rs1.getInt("kategoriID"));
            }
            for (Integer kategoriID : kategoriIDs) {
                kategorierFörSpecifikModell.add(getKategorier(String.valueOf(kategoriID)).get(0));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return kategorierFörSpecifikModell;
    }
    
    public List<Integer> getStorlekar(String ID){
        List<Integer> allaStorlekar = new ArrayList<>();
        String query = "select * from storlek";
        if (ID.length() > 0) {
            query = "select * from storlek where ID = " + ID;
        }
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
    
    public List<String> getFärger(String ID){
        List<String> allaFärger = new ArrayList<>();
        String query = "select * from färg";
        if (ID.length() > 0) {
            query = "select * from färg where ID = " + ID;
        }
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
    
    public List<Modell> getModeller(String ID){
        List<Modell> allaModeller = new ArrayList<>();
        String query = "select * from modell";
        if(ID.length() > 0){
            query = "select * from modell where ID = " + ID;
        }
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt1 = con.prepareStatement(query);
            ResultSet rs = stmt1.executeQuery();
        ){
            while(rs.next()){
                Modell tempModell = new Modell(rs.getInt("ID"), rs.getString("namn"),rs.getInt("pris"),getMärken(String.valueOf(rs.getInt("märkeID"))).get(0));
                tempModell.setKategorier(getAllaKategorierFörSpecifikModell(tempModell.getModellID()));
                allaModeller.add(tempModell);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaModeller;
    }
    
    public List<Sko> getSkor(String ID){
        List<Sko> allaSkor = new ArrayList<>();
        String query = "select * from sko";
        if(ID.length() > 0){
            query = "select * from sko where ID = " + ID;
        }
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                allaSkor.add(new Sko(rs.getInt("ID"), getStorlekar(String.valueOf(rs.getInt("storlekID"))).get(0), getFärger(String.valueOf(rs.getInt("färgID"))).get(0), getLagerStatus(rs.getInt("ID")), getModeller(String.valueOf(rs.getInt("modellID"))).get(0)));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaSkor;
    }
    
    public List<Sko> getSkorAvSpecifikModell(String modellID){
        List<Sko> allaSkor = new ArrayList<>();
        String query = "select * from sko where modellID = " + modellID;
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                allaSkor.add(new Sko(rs.getInt("ID"), getStorlekar(String.valueOf(rs.getInt("storlekID"))).get(0), getFärger(String.valueOf(rs.getInt("färgID"))).get(0), getLagerStatus(rs.getInt("ID")), getModeller(String.valueOf(rs.getInt("modellID"))).get(0)));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaSkor;
    }
    
    public int getLagerStatus(int skoID){
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
    
    public List<Sko> getAllaSkorIBeställning(int beställningID){
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
                allaSkorISpecifikBeställning.add(getSkor(String.valueOf(skoId)).get(0));
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaSkorISpecifikBeställning;
    }
    
    public List<Beställning> getBeställningar(String ID){
        List<Beställning> allaBeställningar = new ArrayList<>();
        String kundQuery = "select * from beställning";
        if (ID.length() > 0){
            kundQuery = "select * from beställning where ID = " + ID;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(kundQuery);
            ResultSet rs = stmt.executeQuery();
        ){
            while(rs.next()){
                Beställning tempBeställning = new Beställning(rs.getInt("ID"), LocalDateTime.parse(rs.getString("datum").substring(0, 19)), rs.getBoolean("expiderad"));
                allaBeställningar.add(tempBeställning);
            }
                
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaBeställningar;
    }
    
    public List<Beställning> getBeställningarIKund(int kundID){
        List<Beställning> allaBeställningar = new ArrayList<>();
        String kundQuery = "select * from beställning where kundID = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(kundQuery);
        ){
            stmt.setString(1, String.valueOf(kundID));
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Beställning tempBeställning = new Beställning(rs.getInt("ID"), LocalDateTime.parse(rs.getString("datum").substring(0, 19), formatter), rs.getBoolean("expiderad"));
                allaBeställningar.add(tempBeställning);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaBeställningar;
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