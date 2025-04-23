package model;

import java.sql.*;
import utilities.DatabaseConnection;

public class ValgStueModel {
    private int valgtStue;
    private String fornavn;
    private String efternavn;
    private String cprNr;

    public int getValgtStue() {
        return valgtStue;
    }

    public void setValgtStue(int valgtStue) {
        this.valgtStue = valgtStue;
    }

    public String getFornavn() {
        return fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public String getCprNr() {
        return cprNr;
    }

    public void getPatientData(int valgtStue) {
        String query = "SELECT Fornavn, Efternavn, CPR_nr FROM Patienter WHERE Stue = ?";
        boolean found = false;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, valgtStue);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                do {
                    fornavn = rs.getString("Fornavn");
                    efternavn = rs.getString("Efternavn");
                    cprNr = rs.getString("CPR_nr");
                    found = true;
                } while (rs.next());
            }

            if (!found) {
                fornavn = " ";
                efternavn = " ";
                cprNr = " ";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
