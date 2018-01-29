package DbModell;

public class Ort {
    
    private String namn;

    public Ort(String namn) {
        this.namn = namn;
    }

    public Ort() {
    }
    
    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

}