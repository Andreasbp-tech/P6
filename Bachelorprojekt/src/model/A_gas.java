package model;

public class A_gas extends Biokemi {
    private double pH;
    private double BE;
    private double HCO3;
    private double systemiskCa;
    private double postfilterCa;

    // Constructor
    public A_gas(String CPR_nr, String tidspunkt, double pH, double BE, double HCO3, double systemiskCa, double postfilterCa) {
        super(CPR_nr, tidspunkt);
        this.pH = pH;
        this.BE = BE;
        this.HCO3 = HCO3;
        this.systemiskCa = systemiskCa;
        this.postfilterCa = postfilterCa;
    }

    // Getters and Setters
    public double getPH() {
        return pH;
    }

    public void setPH(double pH) {
        this.pH = pH;
    }

    public double getBE() {
        return BE;
    }

    public void setBE(double BE) {
        this.BE = BE;
    }

    public double getHCO3() {
        return HCO3;
    }

    public void setHCO3(double HCO3) {
        this.HCO3 = HCO3;
    }

    public double getSystemiskCa() {
        return systemiskCa;
    }

    public void setSystemiskCa(double systemiskCa) {
        this.systemiskCa = systemiskCa;
    }

    public double getPostfilterCa() {
        return postfilterCa;
    }

    public void setPostfilterCa(double postfilterCa) {
        this.postfilterCa = postfilterCa;
    }

    @Override
    public String toString() {
        return "A_gas{" +
                "pH=" + pH +
                ", BE=" + BE +
                ", HCO3=" + HCO3 +
                ", systemiskCa=" + systemiskCa +
                ", postfilterCa=" + postfilterCa +
                '}';
    }
}
