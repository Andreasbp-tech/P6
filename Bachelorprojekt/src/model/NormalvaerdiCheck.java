package model;

import model.NormalvaerdierModel;

import java.util.ArrayList;
import java.util.List;

public class NormalvaerdiCheck {
    private NormalvaerdierModel normalvaerdierModel;

    public NormalvaerdiCheck() {
        normalvaerdierModel = new NormalvaerdierModel();
    }

    /**
     * Returnerer en 2D boolean-matrix med samme struktur som data-arrayet.
     * true = værdi uden for normalområde.
     * false = værdi indenfor normalområde.
     */
    public boolean[][] analyserCRRTData(Object[][] data) {
        int rows = data.length;
        int cols = data[0].length;
        boolean[][] outliers = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            String parameterNavn = data[i][0].toString(); // første kolonne = parameternavn
            for (int j = 1; j < cols; j++) { // start fra 1, da kolonne 0 er navnet
                Object val = data[i][j];
                if (val instanceof Number) {
                    double value = ((Number) val).doubleValue();
                    boolean erUdenfor = !normalvaerdierModel.isValueNormal(parameterNavn, value);
                    outliers[i][j] = erUdenfor;
                } else {
                    outliers[i][j] = false; // hvis værdien er null eller ikke et tal
                }
            }
        }
        return outliers;
    }

    public void printOutliers(Object[][] data, List<String> timestamps, String[] parameterNames) {
        boolean[][] outliers = analyserCRRTData(data);

        for (int row = 0; row < outliers.length; row++) {
            for (int col = 1; col < outliers[row].length; col++) { // start from 1 to skip parameter name
                if (outliers[row][col]) {
                    System.out.printf("Outlier detected: %s at %s -> Value: %s%n",
                            parameterNames[row],
                            timestamps.get(col - 1), // adjust index because col=1 is first timestamp
                            data[row][col]);
                }
            }
        }
    }

}
