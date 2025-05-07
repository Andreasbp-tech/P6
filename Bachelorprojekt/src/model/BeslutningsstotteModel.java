package model;

import utilities.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BeslutningsstotteModel {
    private NormalvaerdierModel normalvaerdierModel;

    public BeslutningsstotteModel(NormalvaerdierModel normalvaerdierModel) {
        this.normalvaerdierModel = normalvaerdierModel;
    }

    public static Double[] hentKreatininOgCarbamid(String cprNr, String dato) {
        String query = "SELECT kreatinin, carbamid FROM Blodprøve WHERE CPR_nr = ? AND tidspunkt LIKE ?";
        Double kreatinin = null;
        Double carbamid = null;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cprNr);
            stmt.setString(2, dato + "%"); // f.eks. "2025-05-06%"
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                kreatinin = rs.getDouble("kreatinin");
                carbamid = rs.getDouble("carbamid");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Double[] { kreatinin, carbamid };
    }

    public String analyserAcidBase(Object pH, Object BE, Object HCO3, Object kreatinin, Object carbamid) {
        try {
            double pHVal = Double.parseDouble(pH.toString());
            double hco3Val = Double.parseDouble(HCO3.toString());
            double kreatininVal = Double.parseDouble(kreatinin.toString());
            double carbamidVal = Double.parseDouble(carbamid.toString());

            double[] kreatininRange = normalvaerdierModel.getRange("Kreatinin");
            double[] carbamidRange = normalvaerdierModel.getRange("Carbamid");
            double[] hco3Range = normalvaerdierModel.getRange("HCO3");
            double[] pHRange = normalvaerdierModel.getRange("pH");
            double[] HandlingMetaboliskAlkaloseHojKreatininCarbamidDialysatflowRange = normalvaerdierModel
                    .getRange("HandlingMetaboliskAlkaloseHøjKreatininCarbamidDialysatflow");
            double[] HandlingMetaboliskAlkaloseNormalKreatininCarbamidBlodflowRange = normalvaerdierModel
                    .getRange("HandlingMetaboliskAlkaloseNormalKreatininCarbamidBlodflow");
            double[] HandlingMetaboliskAcidoseHojKreatininCarbamidBlodflowRange = normalvaerdierModel
                    .getRange("HandlingMetaboliskAcidoseHøjKreatininCarbamidBlodflow");
            double[] HandlingMetaboliskAcidoseNormalKreatininCarbamidDialysatflowRange = normalvaerdierModel
                    .getRange("HandlingMetaboliskAcidoseNormalKreatininCarbamidDialysatflow");

            boolean acidose = pHVal < pHRange[0] || hco3Val < hco3Range[0];
            boolean alkalose = pHVal > pHRange[1] || hco3Val > hco3Range[1];
            boolean nyresvigt = kreatininVal > kreatininRange[1] || carbamidVal > carbamidRange[1];

            if (acidose && !nyresvigt)
                return "<html>Acidose mistænkes – lav pH eller HCO₃, normal kreatinin og carbamid.<br>"
                        + "<font color='red'>➤ Reducer dialysatflow ("
                        + HandlingMetaboliskAcidoseNormalKreatininCarbamidDialysatflowRange[0]
                        + " ml/t)</font></html>";

            if (acidose && nyresvigt)
                return "<html>Acidose mistænkes – lav pH eller HCO₃, forhøjet kreatinin eller carbamid.<br>"
                        + "<font color='red'>➤ Øg blodflow ("
                        + HandlingMetaboliskAcidoseHojKreatininCarbamidBlodflowRange[0]
                        + "-" + HandlingMetaboliskAcidoseHojKreatininCarbamidBlodflowRange[1]
                        + " ml/min)</font></html>";

            if (alkalose && !nyresvigt)
                return "<html>Alkalose mistænkes – høj pH eller HCO₃, normal kreatinin og carbamid.<br>"
                        + "<font color='red'>➤ Reducer blodflow ("
                        + HandlingMetaboliskAlkaloseNormalKreatininCarbamidBlodflowRange[0]
                        + "-" + HandlingMetaboliskAlkaloseNormalKreatininCarbamidBlodflowRange[1]
                        + " ml/min)</font></html>";

            if (alkalose && nyresvigt)
                return "<html>Alkalose mistænkes – høj pH eller HCO₃, forhøjet kreatinin eller carbamid.<br>"
                        + "<font color='red'>➤ Øg dialysatflow ("
                        + HandlingMetaboliskAlkaloseHojKreatininCarbamidDialysatflowRange[0]
                        + " ml/t)</font></html>";

        } catch (Exception e) {
            return "Fejl i acid-base analyse.";
        }

        return "Normal syre-base status.";
    }

    public String analyserSystemiskCa(Object systemiskCa) {
        try {
            double sysCa = Double.parseDouble(systemiskCa.toString().replace(",", "."));
            StringBuilder sb = new StringBuilder();

            double[] systemiskCaLavRange = normalvaerdierModel.getRange("SystemiskCaLav");
            double[] systemiskCaRange = normalvaerdierModel.getRange("SystemiskCa");
            double[] systemiskCaHojRange = normalvaerdierModel.getRange("SystemiskCaHøj");
            double[] CadosisaendringRange = normalvaerdierModel.getRange("CadosisÆndring");

            if (sysCa > systemiskCaRange[1]) {
                sb.append("➤ Reducer Calciumdosis med " + CadosisaendringRange[1] + " mmol/l og informer læge.\n");
            } else if (sysCa >= systemiskCaHojRange[0] && sysCa <= systemiskCaHojRange[1]) {
                sb.append("➤ Reducer Calciumdosis med " + CadosisaendringRange[0] + " mmol/l.\n");
            } else if (sysCa >= systemiskCaRange[0] && sysCa <= systemiskCaRange[1]) {
                sb.append("➤ Ingen ændring i Calciumdosis.\n");
            } else if (sysCa >= systemiskCaLavRange[0] && sysCa <= systemiskCaLavRange[1]) {
                sb.append("➤ Øg Calciumdosis med " + CadosisaendringRange[0] + " mmol/l.\n");
            } else {
                sb.append("➤ Øg Calciumdosis med " + CadosisaendringRange[1] + " mmol/l og informer læge.\n");
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
            double[] postfilterCaLavRange = normalvaerdierModel.getRange("PostfilterCaLav");
            double[] postfilterCaRange = normalvaerdierModel.getRange("PostfilterCa");
            double[] postfilterCaHojRange = normalvaerdierModel.getRange("PostfilterCaHøj");
            double[] CitratdosisaendringRange = normalvaerdierModel.getRange("CitratdosisÆndring");

            if (postCa > postfilterCaHojRange[1]) {
                sb.append("➤ Øg citratdosis med " + CitratdosisaendringRange[1] + " mmol/l og informer læge.\n");
            } else if (postCa >= postfilterCaHojRange[0] && postCa <= postfilterCaHojRange[1]) {
                sb.append("➤ Øg citratdosis med " + CitratdosisaendringRange[0] + " mmol/l.\n");
            } else if (postCa >= postfilterCaRange[0] && postCa <= postfilterCaRange[1]) {
                sb.append("➤ Ingen ændring i citratdosis.\n");
            } else if (postCa >= postfilterCaLavRange[0] && postCa < postfilterCaLavRange[1]) {
                sb.append("➤ Reducer citratdosis med " + CitratdosisaendringRange[0] + " mmol/l.\n");
            } else {
                sb.append("➤ Reducer citratdosis med " + CitratdosisaendringRange[1] + " mmol/l og informer læge.\n");
            }

            return sb.toString();
        } catch (Exception e) {
            return "Fejl i postfilter calcium-analyse – kunne ikke tolke værdien.";
        }
    }
}
