package model;

import utilities.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.NormalvaerdierModel;

public class BeslutningsstotteModel {

    public static Double[] hentKreatininOgCarbamid(String cprNr, String dato) {
        String query = "SELECT kreatinin, carbamid FROM Blodprøve WHERE CPR_nr = ? AND tidspunkt LIKE ?";
        Double kreatinin = null;
        Double carbamid = null;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cprNr);
            stmt.setString(2, dato + "%"); // f.eks. "2025-05-06%"
            System.out.println(dato + ", " + cprNr);
            ResultSet rs = stmt.executeQuery();
            System.out.println(query);

            if (rs.next()) {
                kreatinin = rs.getDouble("kreatinin");
                carbamid = rs.getDouble("carbamid");
            }
            System.out.println("Kreatinin: " + kreatinin + "   Carbamid: " + carbamid);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Double[] { kreatinin, carbamid };
    }

    public String analyserAcidBase(Object pH, Object BE, Object HCO3) {
        try {
            double pHVal = Double.parseDouble(pH.toString());
            double hco3Val = Double.parseDouble(HCO3.toString());

            if (pHVal < 7.35 || hco3Val < 22) // OBS!!! Disse værdier skal hentes fra databasen i stedet
                return "Acidose mistænkes – lav pH eller lav HCO₃.";
            if (pHVal > 7.45 || hco3Val > 26)
                return "Alkalose mistænkes – høj pH eller høj HCO₃.";

        } catch (Exception e) {
            return "Fejl i acid-base analyse.";
        }

        return "Normal syre-base status.";
    }

    public String analyserSystemiskCa(Object systemiskCa) {
        try {
            double sysCa = Double.parseDouble(systemiskCa.toString().replace(",", "."));
            StringBuilder sb = new StringBuilder();

            if (sysCa > 1.35) {
                sb.append("➤ Reducer Calciumdosis med 0,4 mmol/l og informer læge.\n");
            } else if (sysCa >= 1.21 && sysCa <= 1.35) {
                sb.append("➤ Reducer Calciumdosis med 0,2 mmol/l.\n");
            } else if (sysCa >= 1.12 && sysCa <= 1.20) {
                sb.append("➤ Ingen ændring i Calciumdosis.\n");
            } else if (sysCa >= 1.00 && sysCa <= 1.11) {
                sb.append("➤ Øg Calciumdosis med 0,2 mmol/l.\n");
            } else { // sysCa < 1.00
                sb.append("➤ Øg Calciumdosis med 0,4 mmol/l og informer læge.\n");
            }

            return sb.toString();
        } catch (Exception e) {
            return "Fejl i systemisk calcium-analyse – kunne ikke tolke værdien.";
        }
    }

    public String analyserPostfilterCa(Object postfilterCa) {
        try {
            double postCa = Double.parseDouble(postfilterCa.toString().replace(",", "."));
            StringBuilder sb = new StringBuilder();

            if (postCa > 0.4) {
                sb.append("➤ Øg citratdosis med 0,2 mmol/l og informer læge.\n");
            } else if (postCa >= 0.35 && postCa <= 0.4) {
                sb.append("➤ Øg citratdosis med 0,1 mmol/l.\n");
            } else if (postCa >= 0.25 && postCa < 0.35) {
                sb.append("➤ Ingen ændring i citratdosis.\n");
            } else if (postCa >= 0.20 && postCa < 0.25) {
                sb.append("➤ Reducer citratdosis med 0,1 mmol/l.\n");
            } else { // postCa < 0.20
                sb.append("➤ Reducer citratdosis med 0,2 mmol/l og informer læge.\n");
            }

            return sb.toString();
        } catch (Exception e) {
            return "Fejl i postfilter calcium-analyse – kunne ikke tolke værdien.";
        }
    }
}
