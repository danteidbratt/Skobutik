package skobutik;

import DbModell.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Anslutning {

    LogInfo logInfo;

    public Anslutning() {
        logInfo = new LogInfo();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public List<Ort> getOrter(String ID){
        List<Ort> allaOrter = new ArrayList<>();
        String query = "select * from ort";
        if (ID.length() > 0) {
            query = "select * from ort where ID = ?";
        }
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if (ID.length() > 0)
                stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Ort temp = new Ort();
                temp.setNamn(rs.getString("namn"));
                allaOrter.add(temp);
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
                Kund temp = new Kund();
                temp.setID(rs.getInt("ID"));
                temp.setNamn(rs.getString("namn"));
                temp.setLösenord(rs.getString("lösenord"));
                temp.setOrt(getOrter(String.valueOf(rs.getInt("ortID"))).get(0));
                allaKunder.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaKunder;
    }
        
    public Map<Integer, Märke> getMärken(String ID){
        Map<Integer, Märke> allaMärken = new HashMap<>();
        String query = "select * from märke";
        if (ID.length() > 0) 
            query = "select * from märke where ID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if (ID.length() > 0)
                stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Märke temp = new Märke();
                temp.setNamn(rs.getString("namn"));
                allaMärken.put(rs.getInt("ID") ,temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaMärken;
    }
    
    public List<Kategori> getKategorier(String ID){
        List<Kategori> allaKategorier = new ArrayList<>();
        String query = "select * from kategori";
        if (ID.length() > 0)
            query = "select * from kategori where ID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if (ID.length() > 0)
                stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Kategori temp = new Kategori();
                temp.setNamn(rs.getString("namn"));
                allaKategorier.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaKategorier;
    }
    
    public List<Kategori> getAllaKategorierFörSpecifikModell(int modellID){
        List<Kategori> kategorierFörSpecifikModell = new ArrayList<>();
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
    
    public Map<Integer, Storlek> getStorlekar(String ID){
        Map<Integer, Storlek> allaStorlekar = new HashMap<>();
        String query = "select * from storlek";
        if (ID.length() > 0) {
            query = "select * from storlek where ID = ?";
        }
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if (ID.length() > 0)
                stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Storlek temp = new Storlek();
                temp.setNummer(rs.getInt("nummer"));
                allaStorlekar.put(rs.getInt("ID"), temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaStorlekar;
    }
    
    public Map<Integer, Färg> getFärger(String ID){
        Map<Integer, Färg> allaFärger = new HashMap<>();
        String query = "select * from färg";
        if (ID.length() > 0)
            query = "select * from färg where ID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if (ID.length() > 0)
                stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Färg temp = new Färg();
                temp.setNamn(rs.getString("namn"));
                allaFärger.put(rs.getInt("ID"), temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaFärger;
    }
    
    public List<Modell> getModeller(String ID){
        List<Modell> allaModeller = new ArrayList<>();
        Map<Integer, Märke> märken = getMärken("");
        String query = "select * from modell";
        if(ID.length() > 0)
            query = "select * from modell where ID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if(ID.length() > 0)
                stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Modell temp = new Modell();
                temp.setModellID(rs.getInt("ID"));
                temp.setNamn(rs.getString("namn"));
                temp.setPris(rs.getInt("pris"));
                temp.setMärke(märken.get(rs.getInt("märkeID")));
                temp.setKategorier(getAllaKategorierFörSpecifikModell(temp.getModellID()));
                allaModeller.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaModeller;
    }
    
    public List<Sko> getSkor(String ID){
        List<Sko> allaSkor = new ArrayList<>();
        Map<Integer, Storlek> storlekar = getStorlekar("");
        Map<Integer, Färg> färger = getFärger("");
        String query = "select * from sko";
        if(ID.length() > 0){
            query = "select * from sko where ID = ?";
        }
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if(ID.length() > 0)
                stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Sko temp = new Sko();
                Modell m = getModeller(String.valueOf(rs.getInt("modellID"))).get(0);
                temp.setSkoID(rs.getInt("ID"));
                temp.setStorlek(storlekar.get(rs.getInt("storlekID")));
                temp.setFärg(färger.get(rs.getInt("färgID")));
                temp.setAntal(getLagerStatus(rs.getInt("ID")));
                temp.setNamn(m.getNamn());
                temp.setPris(m.getPris());
                temp.setKategorier(m.getKategorier());
                temp.setModellID(m.getModellID());
                allaSkor.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaSkor;
    }
    
    public List<Sko> getSkorAvSpecifikModell(String modellID){
        List<Sko> allaSkor = new ArrayList<>();
        String query = "select * from sko where modellID = ?";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            if (modellID.length() > 0)
                stmt.setString(1, modellID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Map<Integer, Storlek> storlekar = getStorlekar(String.valueOf(rs.getInt("storlekID")));
                Map<Integer, Färg> färger = getFärger(String.valueOf(rs.getInt("färgID")));
                Sko temp = new Sko();
                Modell m = getModeller(String.valueOf(rs.getInt("modellID"))).get(0);
                temp.setSkoID(rs.getInt("ID"));
                temp.setStorlek(storlekar.get(rs.getInt("storlekID")));
                temp.setFärg(färger.get(rs.getInt("färgID")));
                temp.setAntal(getLagerStatus(rs.getInt("ID")));
                temp.setNamn(m.getNamn());
                temp.setPris(m.getPris());
                temp.setKategorier(m.getKategorier());
                temp.setModellID(m.getModellID());
                allaSkor.add(temp);
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
            kundQuery = "select * from beställning where ID = ?";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(kundQuery);
        ){
            if (ID.length() > 0)
                stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Beställning temp = new Beställning();
                temp.setID(rs.getInt("ID"));
                temp.setDatum(LocalDateTime.parse(rs.getString("datum").substring(0, 19), formatter));
                temp.setExpiderad(rs.getBoolean("expiderad"));
                allaBeställningar.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaBeställningar;
    }
    
    public List<Beställning> getBeställningarIKund(String kundID){
        List<Beställning> allaBeställningar = new ArrayList<>();
        String query = "select * from beställning where kundID = ?";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            PreparedStatement stmt = con.prepareStatement(query);
        ){
            stmt.setString(1, kundID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Beställning temp = new Beställning();
                temp.setID(rs.getInt("ID"));
                temp.setDatum(LocalDateTime.parse(rs.getString("datum").substring(0, 19), formatter));
                temp.setExpiderad(rs.getBoolean("expiderad"));
                allaBeställningar.add(temp);
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return allaBeställningar;
    }
    
    public String addToCart(String skoID, String beställningID, String kundID){
        String query = "call AddToCart(?,?,?)";
        try(Connection con = DriverManager.getConnection(logInfo.code, logInfo.name, logInfo.pass);
            CallableStatement stmt = con.prepareCall(query)
        ){
            stmt.setString(1, skoID);
            stmt.setString(2, beställningID);
            stmt.setString(3, kundID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                return rs.getString("message");
            }
        }   catch (SQLException ex) {
            ex.printStackTrace();
        }
        return "";
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