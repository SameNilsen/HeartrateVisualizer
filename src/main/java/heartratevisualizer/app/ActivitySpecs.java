package heartratevisualizer.app;

public class ActivitySpecs {

    String endDate;
    String startDate;
    String perPage;

    public ActivitySpecs(String endDate, String startDate, String perPage) {
        this.endDate = endDate;
        this.startDate = startDate;
        this.perPage = perPage;
    }

    public ActivitySpecs() {

    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPerPage() {
        return this.perPage;
    }

    public void setPerPage(String perPage) {
        this.perPage = perPage;
    }
    
}
