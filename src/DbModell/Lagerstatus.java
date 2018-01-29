package DbModell;

public class Lagerstatus {
    
    private final int id;
    private int antal;
    private Sko sko;

    public Lagerstatus(int id, int antal, Sko sko) {
        this.id = id;
        this.antal = antal;
        this.sko = sko;
    }

    public int getId() {
        return id;
    }
    
    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }

    public Sko getSko() {
        return sko;
    }

    public void setSko(Sko sko) {
        this.sko = sko;
    }

}