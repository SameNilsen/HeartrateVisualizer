package heartratevisualizer.app;

public class IDnZones {

    String id;
    boolean customZones;
    int zone1End;
    int zone2End;
    int zone3End;
    int zone4End;
    int zone5End;

    public IDnZones(String id, boolean customZones, int zone1End, int zone2End, int zone3End, int zone4End, int zone5End) {
        this.id = id;
        this.customZones = customZones;
        this.zone1End = zone1End;
        this.zone2End = zone2End;
        this.zone3End = zone3End;
        this.zone4End = zone4End;
        this.zone5End = zone5End;

    }

    public IDnZones() {

    }

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public boolean getCustomZones() {
        return this.customZones;
    }

    public void setCustomZones(boolean customZones) {
        this.customZones = customZones;
    }

    public int getzone1End() {
        return this.zone1End;
    }

    public void setzone1End(int zone1End) {
        this.zone1End = zone1End;
    }

    public int getzone2End() {
        return this.zone2End;
    }

    public void setzone2End(int zone2End) {
        this.zone2End = zone2End;
    }

    public int getzone3End() {
        return this.zone3End;
    }

    public void setzone3End(int zone3End) {
        this.zone3End = zone3End;
    }

    public int getzone4End() {
        return this.zone4End;
    }

    public void setzone4End(int zone4End) {
        this.zone4End = zone4End;
    }

    public int getzone5End() {
        return this.zone5End;
    }

    public void setzone5End(int zone5End) {
        this.zone5End = zone5End;
    }
    
}
