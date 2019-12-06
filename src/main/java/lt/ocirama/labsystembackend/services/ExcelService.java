package lt.ocirama.labsystembackend.services;

import lt.ocirama.labsystembackend.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public final class ExcelService {

    public static String excelSaveDirectory = "C:\\Users\\lei12\\Desktop\\Output\\";

    public static void OrderLogExcelUpdate(OrderEntity order) {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = excelSaveDirectory + "Užsakymų Žurnalas (" + LocalDate.now().getYear() + ").xlsx";
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                sheet = workbook.getSheetAt(0);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet();
                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("Id");
                row.createCell(1).setCellValue("Užsakymo Nr.");
                row.createCell(2).setCellValue("Užsakovas");
                row.createCell(3).setCellValue("Tyrimai");
                row.createCell(4).setCellValue("Kuro rūšis");
                row.createCell(5).setCellValue("Mėginių kiekis");
                row.createCell(6).setCellValue("Data");
            }
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue(sheet.getLastRowNum());
            row.createCell(1).setCellValue(order.getProtocolId());
            row.createCell(2).setCellValue(order.getCustomer());
            row.createCell(3).setCellValue(order.getTest());
            row.createCell(4).setCellValue(order.getSampleType());
            row.createCell(5).setCellValue(order.getOrderAmount());
            row.createCell(6).setCellValue(order.getDate());

            FileOutputStream outFile = new FileOutputStream(new File(path));
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void AshExcelUpdate(AshEntity ae, String protocol, int sverimoNumeris) {

        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = excelSaveDirectory + LocalDate.now() + " (Peleningumas).xlsx";
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                if (workbook.getSheet(protocol) == null) {
                    sheet = workbook.createSheet(protocol);
                } else
                    sheet = workbook.getSheet(protocol);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(protocol);
            }
            Row rowhead = sheet.createRow(0);
            rowhead.createCell(0).setCellValue("Užsakymo Nr.");
            rowhead.createCell(1).setCellValue("Mėginio Nr.");
            rowhead.createCell(2).setCellValue("Induko Nr.");
            rowhead.createCell(3).setCellValue("Tuščio induko masė, g");
            rowhead.createCell(4).setCellValue("Tuščio induko ir ėminio masė PRIEŠ bandymą, g");
            rowhead.createCell(5).setCellValue("Tuščio induko ir ėminio masė PO bandymo, g");
            rowhead.createCell(6).setCellValue("Data");
            Row row;
            if (sverimoNumeris == 1) {
                int sheetNumber = sheet.getLastRowNum() + 1;
                row = sheet.createRow(sheetNumber);
                row.createCell(0).setCellValue(protocol);
                row.createCell(1).setCellValue(ae.getTray().getSample().getSampleId());
                row.createCell(2).setCellValue(ae.getDishId());
                row.createCell(3).setCellValue(ae.getDishWeight());
                row.createCell(4).setCellValue(ae.getDishAndSampleWeightBefore());
            } else if (sverimoNumeris == 2) {
                row = sheet.getRow(FileControllerService.findRow(workbook, ae.getDishId()));
                row.createCell(5).setCellValue(ae.getDishAndSampleWeightAfter());
            }

            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void GeneralMoistureExcelUpdate(GeneralMoistureEntity gme, String protocol, int sverimoNumeris) {

        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = excelSaveDirectory + LocalDate.now() + " (BendrojiDregme).xlsx";
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                if (workbook.getSheet(protocol) == null) {
                    sheet = workbook.createSheet(protocol);
                } else
                    sheet = workbook.getSheet(protocol);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(protocol);
            }
            Row rowhead = sheet.createRow(0);
            rowhead.createCell(0).setCellValue("Užsakymo Nr.");
            rowhead.createCell(1).setCellValue("Mėginio Nr.");
            rowhead.createCell(2).setCellValue("Induko Nr.");
            rowhead.createCell(3).setCellValue("Tuščio induko masė, g");
            rowhead.createCell(4).setCellValue("Tuščio induko ir ėminio masė PRIEŠ bandymą, g");
            rowhead.createCell(5).setCellValue("Tuščio induko ir ėminio masė PO bandymo, g");
            rowhead.createCell(6).setCellValue("Tuščio induko ir ėminio masė PO bandymo+n val, g");
            rowhead.createCell(7).setCellValue("Data");
            Row row;
            if (sverimoNumeris == 1) {
                int sheetNumber = sheet.getLastRowNum() + 1;
                row = sheet.createRow(sheetNumber);
                row.createCell(0).setCellValue(protocol);
                row.createCell(1).setCellValue(gme.getTray().getSample().getSampleId());
                row.createCell(2).setCellValue(gme.getJarId());
                row.createCell(3).setCellValue(gme.getJarWeight());
                row.createCell(4).setCellValue(gme.getJarAndSampleWeightBefore());
            } else if (sverimoNumeris == 2) {
                row = sheet.getRow(FileControllerService.findRow(workbook, gme.getJarId()));
                row.createCell(5).setCellValue(gme.getJarAndSampleWeightAfter());
                row.createCell(6).setCellValue(gme.getJarAndSampleWeightAfterPlus());
            }

            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void WeightLogExcelUpdate(SampleEntity sampleEntity, String protocol) {

        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = excelSaveDirectory + LocalDate.now() + " (Svoriai).xlsx";
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                if (workbook.getSheet(protocol) == null) {
                    sheet = workbook.createSheet(protocol);
                } else
                    sheet = workbook.getSheet(protocol);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(protocol);
            }
            Row rowhead = sheet.createRow(0);
            rowhead.createCell(0).setCellValue("Mėginio Nr.");
            rowhead.createCell(1).setCellValue("Svoris, g");
            rowhead.createCell(2).setCellValue("Data");

            Row row1 = sheet.createRow(sheet.getLastRowNum() + 1);
            row1.createCell(0).setCellValue(sampleEntity.getSampleId());
            row1.createCell(1).setCellValue(sampleEntity.getSampleWeight());
            row1.createCell(2).setCellValue(String.valueOf(LocalDate.now()));
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void TotalMoistureExcel(TrayEntity tray, TotalMoistureEntity tme, String protocol, int sverimoNumeris, String trayId, String date) {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = excelSaveDirectory + date + " (VisumineDregme).xlsx";
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                if (workbook.getSheet(protocol) == null) {
                    sheet = workbook.createSheet(protocol);
                } else
                    sheet = workbook.getSheet(protocol);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(protocol);
            }
            Row rowhead = sheet.createRow(0);
            rowhead.createCell(0).setCellValue("Užsakymo Nr.");
            rowhead.createCell(1).setCellValue("Mėginio Nr.");
            rowhead.createCell(2).setCellValue("Padėklo Nr.");
            rowhead.createCell(3).setCellValue("Tuščio padėklo masė, g");
            rowhead.createCell(4).setCellValue("Tuščio padėklo ir ėminio masė PRIEŠ bandymą, g");
            rowhead.createCell(5).setCellValue("Tuščio padėklo ir ėminio masė PO bandymo, g");
            rowhead.createCell(6).setCellValue("Tuščio padėklo ir ėminio masė PO bandymo+n val, g");
            rowhead.createCell(7).setCellValue("Data");
            Row row;
            if (sverimoNumeris == 1) {
                int sheetNumber = sheet.getLastRowNum() + 1;
                row = sheet.createRow(sheetNumber);
                row.createCell(0).setCellValue(protocol);
                row.createCell(1).setCellValue(tray.getSample().getSampleId());
                row.createCell(2).setCellValue(tray.getTrayId());
                row.createCell(3).setCellValue(tme.getTrayWeight());
                row.createCell(4).setCellValue(tme.getTrayAndSampleWeightBefore());
            } else if (sverimoNumeris == 2) {
                row = sheet.getRow(FileControllerService.findRow(workbook, trayId));
                row.createCell(5).setCellValue(tme.getTrayAndSampleWeightAfter());
                row.createCell(6).setCellValue(tme.getTrayAndSampleWeightAfterPlus());
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void ReferenceTrayExcelUpdate(ReferenceTrayEntity rte, int sverimoNumeris, String date) {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = excelSaveDirectory + date + " (VisumineDregme).xlsx";
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                if (workbook.getSheet("PamatinisPadeklas") == null) {
                    sheet = workbook.createSheet("PamatinisPadeklas");
                } else
                    sheet = workbook.getSheet("PamatinisPadeklas");
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("PamatinisPadeklas");
            }
            Row rowhead = sheet.createRow(0);
            rowhead.createCell(0).setCellValue("Pamatinio padėklo Nr.");
            rowhead.createCell(1).setCellValue("Pamatinio padėklo svoris PRIEŠ");
            rowhead.createCell(2).setCellValue("Pamatinio padėklo svoris P0");
            rowhead.createCell(3).setCellValue("Data");

            if (sverimoNumeris == 1) {
                Row row1 = sheet.createRow(sheet.getLastRowNum() + 1);
                row1.createCell(0).setCellValue(rte.getReferenceTrayId());
                row1.createCell(1).setCellValue(rte.getReferenceTrayWeightBefore());
                row1.createCell(3).setCellValue(String.valueOf(LocalDate.now()));
            }
            if (sverimoNumeris == 2) {
                Row row = sheet.getRow(FileControllerService.findRow(workbook, rte.getReferenceTrayId()));
                row.createCell(2).setCellValue(rte.getReferenceTrayWeightAfter());
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void QualityControlExcelUpdate(QualityControlEntity qte, int sverimoNumeris, String tyrimas) {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\lei12\\Desktop\\Output\\(KokybesKontrole).xlsx";
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                if (workbook.getSheet(tyrimas) == null) {
                    sheet = workbook.createSheet(tyrimas);
                } else
                    sheet = workbook.getSheet(tyrimas);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(tyrimas);
            }
            Row rowhead = sheet.createRow(0);
            rowhead.createCell(0).setCellValue("Tyrimas");
            rowhead.createCell(1).setCellValue("Induko Nr.");
            rowhead.createCell(2).setCellValue("Induko svoris PRIEŠ");
            rowhead.createCell(3).setCellValue("Induko svoris PO");

            if (sverimoNumeris == 1) {
                Row row1 = sheet.createRow(sheet.getLastRowNum() + 1);
                row1.createCell(0).setCellValue(qte.getTestType());
                row1.createCell(1).setCellValue(qte.getQualityTrayId());
                row1.createCell(3).setCellValue(qte.getQualityTrayWeightBefore());
                row1.createCell(5).setCellValue(qte.getDate());
            }
            if (sverimoNumeris == 2) {
                Row row = sheet.getRow(FileControllerService.findRow(workbook, qte.getQualityTrayId()));
                row.createCell(4).setCellValue(qte.getQualityTrayWeightAfter());
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
