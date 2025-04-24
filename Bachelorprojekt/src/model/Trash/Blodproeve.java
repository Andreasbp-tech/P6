package model.Trash;

public class Blodproeve extends Biokemi {
    private double totalCa;
    private double kreatinin;
    private double carbamid;
    private double kalium;
    private double infektionstal;

    // Constructor
    public Blodproeve(String CPR_nr, String tidspunkt, double totalCa, double kreatinin, double carbamid, double kalium,
            double infektionstal) {
        super(CPR_nr, tidspunkt);
        this.totalCa = totalCa;
        this.kreatinin = kreatinin;
        this.carbamid = carbamid;
        this.kalium = kalium;
        this.infektionstal = infektionstal;
    }

    // Getters and Setters
    public double getTotalCa() {
        return totalCa;
    }

    public void setTotalCa(double totalCa) {
        this.totalCa = totalCa;
    }

    public double getKreatinin() {
        return kreatinin;
    }

    public void setKreatinin(double kreatinin) {
        this.kreatinin = kreatinin;
    }

    public double getCarbamid() {
        return carbamid;
    }

    public void setCarbamid(double carbamid) {
        this.carbamid = carbamid;
    }

    public double getKalium() {
        return kalium;
    }

    public void setKalium(double kalium) {
        this.kalium = kalium;
    }

    public double getInfektionstal() {
        return infektionstal;
    }

    public void setInfektionstal(double infektionstal) {
        this.infektionstal = infektionstal;
    }

    @Override
    public String toString() {
        return "Blodprøve{" +
                "totalCa=" + totalCa +
                ", kreatinin=" + kreatinin +
                ", carbamid=" + carbamid +
                ", kalium=" + kalium +
                ", infektionstal=" + infektionstal +
                '}';
    }
}
