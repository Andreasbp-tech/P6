package model.Trash;

public class Patienter {
    private String CPR_nr;
    private String Fornavn;
    private String Efternavn;
    private String PatientID;
    private int Stue;
    private double Vægt;

    // Constructor
    public Patienter(String CPR_nr, String Fornavn, String Efternavn, String PatientID, int Stue, double Vægt) {
        this.CPR_nr = CPR_nr;
        this.Fornavn = Fornavn;
        this.Efternavn = Efternavn;
        this.PatientID = PatientID;
        this.Stue = Stue;
        this.Vægt = Vægt;
    }

    // Getters and Setters
    public String getCPR_nr() {
        return CPR_nr;
    }

    public void setCPR_nr(String CPR_nr) {
        this.CPR_nr = CPR_nr;
    }

    public String getFornavn() {
        return Fornavn;
    }

    public void setFornavn(String Fornavn) {
        this.Fornavn = Fornavn;
    }

    public String getEfternavn() {
        return Efternavn;
    }

    public void setEfternavn(String Efternavn) {
        this.Efternavn = Efternavn;
    }

    public String getPatientID() {
        return PatientID;
    }

    public void setPatientID(String PatientID) {
        this.PatientID = PatientID;
    }

    public int getStue() {
        return Stue;
    }

    public void setStue(int Stue) {
        this.Stue = Stue;
    }

    public double getVægt() {
        return Vægt;
    }

    public void setVægt(double Vægt) {
        this.Vægt = Vægt;
    }

    @Override
    public String toString() {
        return "Patienter{" +
                "CPR_nr='" + CPR_nr + '\'' +
                ", Fornavn='" + Fornavn + '\'' +
                ", Efternavn='" + Efternavn + '\'' +
                ", PatientID='" + PatientID + '\'' +
                ", Stue=" + Stue +
                ", Vægt=" + Vægt +
                '}';
    }
}
