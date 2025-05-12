package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import utilities.DatabaseConnection;

public class NormalvaerdierModel {
    private final Map<String, double[]> ranges = new HashMap<>();
    private final Map<String, double[]> latestValuesCache = new HashMap<>();
    private static final Set<String> citratParametre = Set.of("Calciumdosis", "Citratdosis");

    public NormalvaerdierModel() {
        loadFromDatabase();
    }

    /**
     * Henter normalværdier fra databasen og gemmer dem i ranges.
     */
    private void loadFromDatabase() {
        String query = "SELECT parameter_name, min_value, max_value FROM Normalværdier";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String parameter = rs.getString("parameter_name").trim();
                double min = rs.getDouble("min_value");
                double max = rs.getDouble("max_value");
                ranges.put(parameter, new double[] { min, max });
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kunne ikke hente normalområder fra databasen.");
        }
    }

    public double[] getRange(String parameterName) {
        return ranges.get(parameterName.trim());
    }

    /**
     * Tjekker om en værdi er inden for normalområdet.
     */
    public boolean isValueNormal(String parameterName, double value) {
        parameterName = parameterName.trim();
        double[] range = ranges.get(parameterName);

        if (range == null) {
            throw new IllegalArgumentException("Ukendt parameter: " + parameterName);
        }

        if (parameterName.equalsIgnoreCase("Heparin")) {
            // Heparin er kun 'normal' ved 0 eller maksimumsværdi
            return value == 0 || value == range[1];
        }

        return value >= range[0] && value <= range[1];
    }

    /**
     * Tjekker om en værdi er normal, afhængigt af om den kommer fra
     * citrat-registrering.
     */
    public boolean isValueNormalVaeske(String parameterName, double nyVaerdi, String cprNr) {
        parameterName = parameterName.trim();

        if (kalderFraCitratRegistrering()) {
            // Brug tidligere værdi fra cache
            double tidligereVaerdi = hentTidligereVaerdi(parameterName, cprNr);

            if (Double.isNaN(tidligereVaerdi)) {
                return isValueNormal(parameterName, nyVaerdi); // Fald tilbage på normalområdet
            }

            return isChangeAcceptable(parameterName, nyVaerdi, tidligereVaerdi);
        } else {
            return isValueNormal(parameterName, nyVaerdi);
        }
    }

    /**
     * Opdaterer normalområde og gemmer til database.
     */
    public void updateRange(String parameterName, double min, double max) {
        parameterName = parameterName.trim();
        ranges.put(parameterName, new double[] { min, max });
        saveRangeToDatabase(parameterName, min, max);
    }

    private void saveRangeToDatabase(String parameterName, double min, double max) {
        String query = "UPDATE Normalværdier SET min_value = ?, max_value = ? WHERE parameter_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, min);
            pstmt.setDouble(2, max);
            pstmt.setString(3, parameterName);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kunne ikke opdatere normalområde i databasen.");
        }
    }

    /**
     * Analyserer et datasæt og markerer værdier uden for normalområdet.
     */
    public boolean[][] analyserDataNormalvaerdi(Object[][] data, String cprNr) {
        int rows = data.length;
        int cols = data[0].length;
        boolean[][] outliers = new boolean[rows][cols];
        boolean citratContext = kalderFraCitratRegistrering();

        for (int i = 0; i < rows; i++) {
            String parameterNavn = data[i][0].toString();
            List<Double> tidligereVaerdier = new ArrayList<>();

            for (int j = 1; j < cols; j++) {
                Object val = data[i][j];
                boolean erUdenfor = false;

                if (val instanceof Number) {
                    double nyVaerdi = ((Number) val).doubleValue();

                    if (citratContext) {
                        erUdenfor = !isValueNormalVaeske(parameterNavn, nyVaerdi, cprNr);
                    } else if (!tidligereVaerdier.isEmpty()) {
                        double tidligere = tidligereVaerdier.get(tidligereVaerdier.size() - 1);
                        erUdenfor = !isChangeAcceptable(parameterNavn, nyVaerdi, tidligere);
                    }

                    tidligereVaerdier.add(nyVaerdi);
                }

                outliers[i][j] = erUdenfor;
            }
        }
        return outliers;
    }

    /**
     * Vurderer om ændringen mellem to værdier er acceptabel.
     */
    private boolean isChangeAcceptable(String parameterName, double ny, double tidligere) {
        double[] CaRange = getRange("CadosisÆndring");
        double[] CitratRange = getRange("CitratdosisÆndring");

        if (parameterName.equalsIgnoreCase("Calciumdosis")) {
            return Math.abs(ny - tidligere) < CaRange[1];
        } else if (parameterName.equalsIgnoreCase("Citratdosis")) {
            return Math.abs(ny - tidligere) < CitratRange[1];
        }
        return isValueNormal(parameterName, ny);
    }

    /**
     * Rydder cache for tidligere værdier (f.eks. ved nyt cprNr).
     */
    public void clearLatestValuesCache() {
        latestValuesCache.clear();
    }

    /**
     * Returnerer tidligere værdi fra cache for et bestemt parameter.
     */
    private double hentTidligereVaerdi(String parameterName, String cprNr) {
        if (!citratParametre.contains(parameterName))
            return Double.NaN;

        double[] tidligereVaerdier = latestValuesCache.computeIfAbsent(cprNr, k -> {
            RegistrerVaeskekoncentrationModel model = new RegistrerVaeskekoncentrationModel();
            return model.getLatestValues(k);
        });

        return parameterName.equalsIgnoreCase("Calciumdosis") ? tidligereVaerdier[0]
                : parameterName.equalsIgnoreCase("Citratdosis") ? tidligereVaerdier[1]
                        : Double.NaN;
    }

    /**
     * Returnerer true hvis kaldet sker fra en citrat-registreringsklasse.
     */
    private boolean kalderFraCitratRegistrering() {
        for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
            String className = elem.getClassName();
            if (className.contains("TabelCitratmetabolismeController") ||
                    className.contains("RegistrerCitratmetabolismeController")) {
                return true;
            }
        }
        return false;
    }
}
