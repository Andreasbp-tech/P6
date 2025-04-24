package model.Trash;

public class Biokemi {
    private String CPR_nr;
    private String tidspunkt;

    // Constructor
    public Biokemi(String CPR_nr, String tidspunkt) {
        this.CPR_nr = CPR_nr;
        this.tidspunkt = tidspunkt;
    }

    // Getters and Setters
    public String getCPR_nr() {
        return CPR_nr;
    }

    public void setCPR_nr(String CPR_nr) {
        this.CPR_nr = CPR_nr;
    }

    public String getTidspunkt() {
        return tidspunkt;
    }

    public void setTidspunkt(String tidspunkt) {
        this.tidspunkt = tidspunkt;
    }

    @Override
    public String toString() {
        return "Biokemi{" +
                "CPR_nr='" + CPR_nr + '\'' +
                ", tidspunkt='" + tidspunkt + '\'' +
                '}';
    }
}
