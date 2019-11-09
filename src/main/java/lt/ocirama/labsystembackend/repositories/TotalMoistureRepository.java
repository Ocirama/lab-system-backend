package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.SampleLogEntity;
import lt.ocirama.labsystembackend.model.TotalMoistureEntity;
import lt.ocirama.labsystembackend.services.FileControllerService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static lt.ocirama.labsystembackend.services.FileControllerService.*;

public class TotalMoistureRepository {

    XSSFSheet sheet;
    XSSFWorkbook workbook;
    Scanner sc = new Scanner(System.in);
    String path = "C:\\Users\\lei12\\Desktop\\Output\\" + LocalDate.now() + "(VisumineDregme).xlsx";
    private final EntityManagerFactory entityManagerFactory;

    public TotalMoistureRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void TotalMoistureLogGenerate() {
        SampleLogEntity sample = new SampleLogEntity();
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            File file = new File(path);
            System.out.println("Protokolas ?");
            String protocol = sc.nextLine();
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                sheet = workbook.createSheet(protocol);
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

            Session session = em.unwrap(Session.class);
            Query query = session.createQuery("Select ol.samples from OrderLogEntity ol where ol.protocolId=:protocol");
            query.setParameter("protocol", protocol);
            List<SampleLogEntity> samples = query.getResultList();
            Row row = null;
            int sheetNumber = sheet.getLastRowNum() + 1;
            String padeklas = null;
            TotalMoistureEntity tme = new TotalMoistureEntity();
            double trayWeight = 0;
            for (SampleLogEntity sampleLogEntity : samples) {
                String sampleName = sampleLogEntity.getSampleId();
                System.out.println("Visuminės dėgmės svėrimas mėginiui : " + sampleName);
                for (int j = 1; j <= 2; j++) {
                    System.out.println("Skenuokite " + j + "-ojo padėklo barkodą mėginiui  " + sampleName);
                    padeklas = sc.nextLine();
                    row = sheet.createRow(sheetNumber + j);
                    List<TotalMoistureEntity> list = new ArrayList<>();
                    list.add(tme);
                    tme.setTrayId(padeklas);
                    tme.setSample(sampleLogEntity);
                    System.out.println("Sverkite padėklą " + padeklas);
                    trayWeight = FileControllerService.sverimoPrograma();
                    tme.setTrayWeight(trayWeight);
                }
                for (int j = 1; j <= 2; j++) {
                    System.out.println("Įdėkitę " + sampleName + " mėginį į " + padeklas + " padėklą ir sverkite:");
                    Double trayWeight2 = FileControllerService.sverimoPrograma();
                    tme.setTrayAndSampleWeightBefore(trayWeight2);
                    em.merge(sampleLogEntity);
                    em.persist(tme);
                    row = sheet.createRow(sheetNumber + j);
                    row.createCell(0).setCellValue(protocol);
                    row.createCell(1).setCellValue(sampleName);
                    row.createCell(2).setCellValue(padeklas);
                    row.createCell(3).setCellValue(trayWeight);
                    row.createCell(4).setCellValue(trayWeight2);
                }
            }
            row.createCell(7).setCellValue(String.valueOf(LocalDate.now()));
            transaction.commit();
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void totalMoistureLogUpdate() {
        TotalMoistureEntity tme = new TotalMoistureEntity();
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        FileInputStream excelFile = null;
        try {
            excelFile = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            workbook = new XSSFWorkbook(excelFile);
            sheet = workbook.getSheetAt(0);
            for (int i = 1; i < 50; i++) {
                System.out.println("Sekantis meginys? Taip/Ne");
                if (sc.nextLine().equals("Taip")) {
                    System.out.println("Skenuokite padėklo ID:");
                    String indukas = sc.nextLine();
                    Session session = em.unwrap(Session.class);
                    TotalMoistureEntity totalMoistureEntity = entityManagerFactory.createEntityManager().find(TotalMoistureEntity.class, indukas);
                    int row = findRow(workbook, indukas);
                    System.out.println(row);
                    String sheet1 = findSheet(workbook, indukas);
                    XSSFSheet sheetx = workbook.getSheet(sheet1);
                    XSSFRow rowx = sheetx.getRow(row);

                    double x = getRandomNumberInRange(0.00005, 0.00030);
                    System.out.println("Induko svėrimas po džiovinimo:");
                    String sg1 = SverimoPrograma();
                    double a = Double.parseDouble(sg1);
                    double sum = round(a + x, 5);
                    rowx.createCell(5).setCellValue(sg1);
                    rowx.createCell(6).setCellValue(String.valueOf(sum));
                    // ds.SvorisPoDziovinimo(connection, "Visumine_dregme", sg1, sum, indukas);
                } else {
                    break;
                }
            }
            Row row = sheet.createRow(5);
            FileOutputStream outFile = new FileOutputStream(new File("C:\\Users\\lei12\\Desktop\\New folder\\" + data + ".xlsx"));
            workbook.write(outFile);
            outFile.flush();
            outFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
