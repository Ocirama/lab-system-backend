package lt.ocirama.labsystembackend.services;

import lt.ocirama.labsystembackend.model.OrderEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class ExcelService {
   /* public void OrderLogExcelUpdate(OrderEntity order, String excelDirectory) {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\lei12\\Desktop\\Output\\Užsakymų Žurnalas (" + LocalDate.now().getYear() + ").xlsx";
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
            FileOutputStream outFile = new FileOutputStream(new File(path));
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue(sheet.getLastRowNum());
            row.createCell(1).setCellValue(order.getProtocolId());
            row.createCell(2).setCellValue(order.getCustomer());
            row.createCell(3).setCellValue(order.getTest());
            row.createCell(4).setCellValue(order.getSampleType());
            row.createCell(5).setCellValue(order.getOrderAmount());
            row.createCell(6).setCellValue(order.getDate());


    }*/
}
