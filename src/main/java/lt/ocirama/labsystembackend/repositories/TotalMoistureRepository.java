package lt.ocirama.labsystembackend.repositories;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lt.ocirama.labsystembackend.model.SampleEntity;
import lt.ocirama.labsystembackend.model.TotalMoistureEntity;
import lt.ocirama.labsystembackend.model.TrayEntity;
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


    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;

    public TotalMoistureRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void TotalMoistureLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            System.out.println("Protokolas ?");
            String protocol = sc.nextLine();
            Session session = em.unwrap(Session.class);
            Query query = session.createQuery("Select ol.samples from OrderEntity ol where ol.protocolId=:protocol");
            query.setParameter("protocol", protocol);
            List<SampleEntity> samples = query.getResultList();
            for (SampleEntity sampleEntity : samples) {
                String padeklas;
                String sampleName = sampleEntity.getSampleId();
                System.out.println("Visuminės dėgmės svėrimas mėginiui : " + sampleName);
                List<TotalMoistureEntity> list = new ArrayList<>();
                TotalMoistureEntity tme;
                TrayEntity te;

                for (int j = 1; j <= 2; j++) {
                    tme = new TotalMoistureEntity();
                    te = new TrayEntity();
                    System.out.println("Skenuokite " + j + "-ojo padėklo barkodą mėginiui  " + sampleName);
                    padeklas = sc.nextLine();

                    te.setSample(sampleEntity);
                    te.setTrayId(padeklas);

                    list.add(tme);
                    tme.setTray(te);
                    System.out.println("Sverkite padėklą " + padeklas);
                    Double trayWeight = FileControllerService.sverimoPrograma();
                    tme.setTrayWeight(trayWeight);
                    em.persist(te);
                    em.persist(tme);
                }
                for (int k = 0; k <= 1; k++) {
                    tme = list.get(k);
                    System.out.println("Įdėkitę " + tme.getTray().getSample().getSampleId() + " mėginį į " + tme.getTray().getTrayId() + " padėklą ir sverkite:");
                    Double trayWeight2 = FileControllerService.sverimoPrograma();
                    tme.setTrayAndSampleWeightBefore(trayWeight2);
                    em.merge(sampleEntity);
                    em.persist(tme);
                    TotalMoistureExcel(tme.getTray(),tme, protocol);
                }
            }

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TotalMoistureExcel(TrayEntity tray, TotalMoistureEntity tme, String protocol) {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\lei12\\Desktop\\Output\\" + LocalDate.now() + "(VisumineDregme).xlsx";
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
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
            int sheetNumber = sheet.getLastRowNum() + 1;
            row = sheet.createRow(sheetNumber);
            row.createCell(0).setCellValue(protocol);
            row.createCell(1).setCellValue(tray.getSample().getSampleId());
            row.createCell(2).setCellValue(tray.getTrayId());
            row.createCell(3).setCellValue(tme.getTrayWeight());
            row.createCell(4).setCellValue(tme.getTrayAndSampleWeightBefore());

            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch ( IOException e) {
            e.printStackTrace();
        }

    }
}
