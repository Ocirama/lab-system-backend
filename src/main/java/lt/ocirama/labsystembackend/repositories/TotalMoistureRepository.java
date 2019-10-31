package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.SampleLogEntity;
import lt.ocirama.labsystembackend.model.TotalMoistureEntity;
import lt.ocirama.labsystembackend.services.FileControllerService;
import org.apache.poi.ss.usermodel.Row;
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
}
