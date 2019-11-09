package lt.ocirama.labsystembackend.services;

import com.fazecast.jSerialComm.SerialPort;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public final class FileControllerService {

    public static Sheet findSheetByProtocol(XSSFWorkbook workbook, String protocolName) {
        for (Sheet sheet : workbook)
            if (sheet.getSheetName().trim().equals(protocolName + "-19-8b")) {
                return sheet;
            }
        return null;
    }

    public static String findSheet(XSSFWorkbook workbook, String cellContent) {
        for (Sheet sheet : workbook)
            for (Row row : sheet) {
                for (Cell cell : row) {
                    cell.setCellType(CellType.STRING);
                    if (cell.getCellType() == CellType.STRING) {
                        if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
                            return sheet.getSheetName();

                        }
                    }
                }
            }
        return null;
    }

    public static int findRow(XSSFWorkbook workbook, String cellContent) {

        for (Sheet sheet : workbook)
            for (Row row : sheet) {
                for (Cell cell : row) {
                    cell.setCellType(CellType.STRING);
                    if (cell.getCellType() == CellType.STRING) {
                        if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
                            return row.getRowNum();

                        }
                    }
                }
            }
        return 0;
    }


    public static Double getRandomNumberInRange(double min, double max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return min + r.nextDouble() * max;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String changeToComma(String value) {
        return value.replace(".", ",");

    }

    public static Double sverimoPrograma() {
        ScaleService ss = new ScaleService();
        SerialPort serialPort = ss.SvarstykliuJungtis();
        double trayWeight = ss.Pasverti(serialPort);
        ss.ClosePort(serialPort);
        return trayWeight;
    }
}
