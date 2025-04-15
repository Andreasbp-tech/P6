package model;

public class Patienter {
    private String cprNr;
    private String fornavn;
    private String efternavn;
    private String patientID;
    private int stue;
    private double vaegt;

    // Constructor
    public Patienter(String cprNr, String fornavn, String efternavn, String patientID, int stue, double vaegt) {
        this.cprNr = cprNr;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.patientID = patientID;
        this.stue = stue;
        this.vaegt = vaegt;
    }

    // Getters and Setters
    public String getCprNr() {
        return cprNr;
    }

    public void setCprNr(String cprNr) {
        this.cprNr = cprNr;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public int getStue() {
        return stue;
    }

    public void setStue(int stue) {
        this.stue = stue;
    }

    public double getVaegt() {
        return vaegt;
    }

    public void setVaegt(double vaegt) {
        this.vaegt = vaegt;
    }

    @Override
    public String toString() {
        return "Patienter{" +
                "cprNr='" + cprNr + '\'' +
                ", fornavn='" + fornavn + '\'' +
                ", efternavn='" + efternavn + '\'' +
                ", patientID='" + patientID + '\'' +
                ", stue=" + stue +
                ", vaegt=" + vaegt +
                '}';
    }
}
