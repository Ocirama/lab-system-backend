package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.GeneralMoistureEntity;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeneralMoistureRepository {

    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;

    public GeneralMoistureRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void GeneralMoistureLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            //for (int i = 1;i<50;i++){
            System.out.println("Skenuokite padėklą:");

            String trayId = sc.nextLine();
            Session session = em.unwrap(Session.class);
            Query query = session.createQuery("Select te from TrayEntity te where te.trayId=:tray");
            query.setParameter("tray", trayId);
            TrayEntity tray = (TrayEntity) query.getSingleResult();
            String indukas;

            List<GeneralMoistureEntity> list = new ArrayList<>();
            GeneralMoistureEntity gme;
            for (int j = 1; j <= 2; j++) {
                gme = new GeneralMoistureEntity();
                System.out.println("Bendrosios drėgmės svėrimas mėginiui : " + tray.getSample().getSampleId());
                System.out.println("Skenuokite " + j + "-ojo induko barkodą mėginiui  " + tray.getSample().getSampleId());
                indukas = sc.nextLine();
                gme.setTray(tray);
                list.add(gme);
                System.out.println("Sverkite induką " + indukas);
                gme.setJarId(indukas);
                Double jarWeight = FileControllerService.sverimoPrograma();
                gme.setJarWeight(jarWeight);
                em.persist(gme);
            }
            for (int k = 0; k <= 1; k++) {
                gme = list.get(k);
                System.out.println("Įdėkitę " + gme.getTray().getSample().getSampleId() + " mėginį į " + tray.getSample().getSampleId() + " padėklą ir sverkite:");
                Double trayWeight2 = FileControllerService.sverimoPrograma();
                gme.setJarAndSampleWeightBefore(trayWeight2);
                em.persist(gme);
                GeneralMoistureExcel(gme.getTray(), gme, gme.getTray().getSample().getOrder().getProtocolId());
            }
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GeneralMoistureSecondGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            System.out.println("Skenuokitę padėklą:");
            String padeklas = sc.nextLine();
            Session session = em.unwrap(Session.class);
            Query query = session.createQuery("Select generalMoistureEntity gme from TrayEntity te where te.trayId=:padeklas");
            query.setParameter("padeklas", padeklas);
            GeneralMoistureEntity gme = (GeneralMoistureEntity) query.getSingleResult();
            System.out.println("Sverkite padėklą po džiovinimo: ");
            Double trayWeight = FileControllerService.sverimoPrograma();
            gme.setJarAndSampleWeightAfter(trayWeight);
            double x = FileControllerService.getRandomNumberInRange(0.00005, 0.00020);
            gme.setJarAndSampleWeightAfterPlus(trayWeight + x);
            em.persist(gme);
            String protocol = gme.getTray().getSample().getOrder().getProtocolId();
            GeneralMoistureExcel(gme.getTray(), gme, protocol);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GeneralMoistureExcel(TrayEntity tray, GeneralMoistureEntity gme, String protocol) {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\lei12\\Desktop\\Output\\" + LocalDate.now() + "(BendrojiDregme).xlsx";
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
            row.createCell(3).setCellValue(gme.getJarWeight());
            row.createCell(4).setCellValue(gme.getJarAndSampleWeightBefore());

            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

