/*package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import utilities.DatabaseConnection;
import model.ValgStueModel;

public class NormalvaerdierModel {
    private Map<String, double[]> ranges;
    private final Map<String, double[]> latestValuesCache = new HashMap<>();
    ValgStueModel valgStueModel = new ValgStueModel();

    public NormalvaerdierModel() {
        ranges = new HashMap<>();
        loadFromDatabase();
    }

    private void loadFromDatabase() {
        String query = "SELECT parameter_name, min_value, max_value FROM Normalværdier";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String parameter = rs.getString("parameter_name");
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
        return ranges.get(parameterName);
    }

    public boolean isValueNormal(String parameterName, double value) {
        parameterName = parameterName.trim();

        double[] range = ranges.get(parameterName);
        if (range == null) {
            throw new IllegalArgumentException("Ukendt parameter: " + parameterName);
        }
        return value >= range[0] && value <= range[1];
    }

    public boolean isValueNormalVaeske(String parameterName, double nyVaerdi, String cprNr) {
        parameterName = parameterName.trim();

        boolean calledFromCitratRegistrering = false;
        for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
            if (elem.getClassName().contains("TabelCitratmetabolismeController")
                    || elem.getClassName().contains("RegistrerCitratmetabolismeController")) {
                calledFromCitratRegistrering = true;
                break;
            }
        }

        if (calledFromCitratRegistrering) {
            double[] tidligereVaerdier = latestValuesCache.computeIfAbsent(cprNr, k -> {
                RegistrerCitratmetabolismeModel citratModel = new RegistrerCitratmetabolismeModel();
                return citratModel.getLatestValues(k);
            });

            double tidligereVaerdi = -1;

            if (parameterName.equalsIgnoreCase("Calciumdosis")) {
                tidligereVaerdi = tidligereVaerdier[0];
            } else if (parameterName.equalsIgnoreCase("Citratdosis")) {
                tidligereVaerdi = tidligereVaerdier[1];
            } else {
                return isValueNormal(parameterName, nyVaerdi);
            }

            return isChangeAcceptable(parameterName, nyVaerdi, tidligereVaerdi);
        } else {
            return isValueNormal(parameterName, nyVaerdi);
        }
    }

    public void updateRange(String parameterName, double min, double max) {
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

    public boolean[][] analyserDataNormalvaerdi(Object[][] data, String cprNr) {
        int rows = data.length;
        int cols = data[0].length;
        boolean[][] outliers = new boolean[rows][cols];

        boolean calledFromCitratRegistrering = false;
        for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
            if (elem.getClassName().contains("RegistrerCitratmetabolismeController")) {
                calledFromCitratRegistrering = true;
                break;
            }
        }

        for (int i = 0; i < rows; i++) {
            String parameterNavn = data[i][0].toString();
            Double tidligereVaerdi = null;

            for (int j = 1; j < cols; j++) {
                Object val = data[i][j];
                if (val instanceof Number) {
                    double nyVaerdi = ((Number) val).doubleValue();
                    boolean erUdenfor;

                    if (calledFromCitratRegistrering) {
                        erUdenfor = !isValueNormalVaeske(parameterNavn, nyVaerdi, cprNr);
                    } else {
                        if (tidligereVaerdi != null) {
                            erUdenfor = !isChangeAcceptable(parameterNavn, nyVaerdi, tidligereVaerdi);
                        } else {
                            erUdenfor = false; // Første værdi antages OK
                        }
                        tidligereVaerdi = nyVaerdi;
                    }

                    outliers[i][j] = erUdenfor;
                } else {
                    outliers[i][j] = false;
                }
            }
        }
        return outliers;
    }

    private boolean isChangeAcceptable(String parameterName, double ny, double tidligere) {
        if (parameterName.equalsIgnoreCase("Calciumdosis")) {
            System.out.println("IsChangeAcceptable: Calcium");
            return Math.abs(ny - tidligere) < 0.4;
        } else if (parameterName.equalsIgnoreCase("Citratdosis")) {
            System.out.println("IsChangeAcceptable: Citrat");
            return Math.abs(ny - tidligere) < 0.2;
        } else {
            System.out.println("IsChangeAcceptable: Else");
            return isValueNormal(parameterName, ny);
        }
    }

    public void clearLatestValuesCache() {
        latestValuesCache.clear();
    }
}
*/

package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import utilities.DatabaseConnection;
import model.ValgStueModel;
import java.util.ArrayList;
import java.util.List;

public class NormalvaerdierModel {
    private Map<String, double[]> ranges;
    private final Map<String, double[]> latestValuesCache = new HashMap<>();
    ValgStueModel valgStueModel = new ValgStueModel();

    public NormalvaerdierModel() {
        ranges = new HashMap<>();
        loadFromDatabase();
    }

    private void loadFromDatabase() {
        String query = "SELECT parameter_name, min_value, max_value FROM Normalværdier";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String parameter = rs.getString("parameter_name");
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
        return ranges.get(parameterName);
    }

    public boolean isValueNormal(String parameterName, double value) {
        parameterName = parameterName.trim();

        double[] range = ranges.get(parameterName);
        if (range == null) {
            throw new IllegalArgumentException("Ukendt parameter: " + parameterName);
        }
        return value >= range[0] && value <= range[1];
    }

    public boolean isValueNormalVaeske(String parameterName, double nyVaerdi, String cprNr) {
        parameterName = parameterName.trim();

        boolean calledFromCitratRegistrering = false;
        for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
            if (elem.getClassName().contains("TabelCitratmetabolismeController")
                    || elem.getClassName().contains("RegistrerCitratmetabolismeController")) {
                calledFromCitratRegistrering = true;
                break;
            }
        }

        if (calledFromCitratRegistrering) {
            // Kun hente den seneste værdi for det pågældende CPR-nummer
            double[] tidligereVaerdier = latestValuesCache.computeIfAbsent(cprNr, k -> {
                RegistrerVaeskekoncentrationModel citratModel = new RegistrerVaeskekoncentrationModel();
                return citratModel.getLatestValues(k); // Henter kun den nyeste værdi
            });

            double tidligereVaerdi = -1;

            // Brug kun den seneste værdi i stedet for at sammenligne med alle tidligere
            // værdier
            if (parameterName.equalsIgnoreCase("Calciumdosis")) {
                tidligereVaerdi = tidligereVaerdier[0];
            } else if (parameterName.equalsIgnoreCase("Citratdosis")) {
                tidligereVaerdi = tidligereVaerdier[1];
            } else {
                return isValueNormal(parameterName, nyVaerdi); // Hvis parameteren ikke er speciel, brug normal range
                                                               // check
            }

            // Sammenlign kun med den sidste værdi
            return isChangeAcceptable(parameterName, nyVaerdi, tidligereVaerdi);
        } else {
            return isValueNormal(parameterName, nyVaerdi); // Hvis ikke kaldt fra citratregistrering, check normal værdi
        }
    }

    public void updateRange(String parameterName, double min, double max) {
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

    public boolean[][] analyserDataNormalvaerdi(Object[][] data, String cprNr) {
        int rows = data.length;
        int cols = data[0].length;
        boolean[][] outliers = new boolean[rows][cols];

        boolean calledFromCitratRegistrering = false;
        for (StackTraceElement elem : Thread.currentThread().getStackTrace()) {
            if (elem.getClassName().contains("RegistrerCitratmetabolismeController")) {
                calledFromCitratRegistrering = true;
                break;
            }
        }

        for (int i = 0; i < rows; i++) {
            String parameterNavn = data[i][0].toString();
            List<Double> tidligereVaerdier = new ArrayList<>();

            for (int j = 1; j < cols; j++) {
                Object val = data[i][j];
                if (val instanceof Number) {
                    double nyVaerdi = ((Number) val).doubleValue();
                    boolean erUdenfor;

                    if (calledFromCitratRegistrering) {
                        erUdenfor = !isValueNormalVaeske(parameterNavn, nyVaerdi, cprNr);
                    } else {
                        if (!tidligereVaerdier.isEmpty()) {
                            double tidligereVaerdi = tidligereVaerdier.get(tidligereVaerdier.size() - 1);
                            erUdenfor = !isChangeAcceptable(parameterNavn, nyVaerdi, tidligereVaerdi);
                        } else {
                            erUdenfor = false; // Første værdi antages OK
                        }
                        tidligereVaerdier.add(nyVaerdi); // Tilføj ny værdi til listen over tidligere værdier
                    }

                    outliers[i][j] = erUdenfor;
                } else {
                    outliers[i][j] = false;
                }
            }
        }
        return outliers;
    }

    private boolean isChangeAcceptable(String parameterName, double ny, double tidligere) {
        if (parameterName.equalsIgnoreCase("Calciumdosis")) {
            System.out.println("IsChangeAcceptable: Calcium");
            return Math.abs(ny - tidligere) < 0.4;
        } else if (parameterName.equalsIgnoreCase("Citratdosis")) {
            System.out.println("IsChangeAcceptable: Citrat");
            return Math.abs(ny - tidligere) < 0.2;
        } else {
            System.out.println("IsChangeAcceptable: Else");
            return isValueNormal(parameterName, ny);
        }
    }

    public void clearLatestValuesCache() {
        latestValuesCache.clear();
    }
}
