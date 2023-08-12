package heartratevisualizer.app;

public class IDnLimit {

    String id;
    int limit;


    public IDnLimit(String id, int limit) {
        this.id = id;
        this.limit = limit;

    }

    public IDnLimit() {

    }

    public String getID() {
        return this.id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
}
