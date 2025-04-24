package model.Trash;

public class Medarbejdere {
    private int MedarbejderID;
    private String Fornavn;
    private String Efternavn;
    private String Rolle;

    // Constructor
    public Medarbejdere(int MedarbejderID, String Fornavn, String Efternavn, String Rolle) {
        this.MedarbejderID = MedarbejderID;
        this.Fornavn = Fornavn;
        this.Efternavn = Efternavn;
        this.Rolle = Rolle;
    }

    // Getters and Setters
    public int getMedarbejderID() {
        return MedarbejderID;
    }

    public void setMedarbejderID(int MedarbejderID) {
        this.MedarbejderID = MedarbejderID;
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

    public String getRolle() {
        return Rolle;
    }

    public void setRolle(String Rolle) {
        this.Rolle = Rolle;
    }

    @Override
    public String toString() {
        return "Medarbejdere{" +
                "MedarbejderID=" + MedarbejderID +
                ", Fornavn='" + Fornavn + '\'' +
                ", Efternavn='" + Efternavn + '\'' +
                ", Rolle='" + Rolle + '\'' +
                '}';
    }
}
