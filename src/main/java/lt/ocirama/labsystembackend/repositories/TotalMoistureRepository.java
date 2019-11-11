package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.SampleEntity;
import lt.ocirama.labsystembackend.model.TotalMoistureEntity;
import lt.ocirama.labsystembackend.model.TrayEntity;
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


    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;

    public TotalMoistureRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void TotalMoistureLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            for (int i = 1; i < 5000; i++) {
                System.out.println("Naujo protokolo visuminės drėgmės svėrimas: Taip/Ne");
                if (sc.nextLine().equals("Taip")) {
                    transaction.begin();
                    System.out.println("Užsakymo numeris ?");
                    String protocol = sc.nextLine();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select ol.samples from OrderEntity ol where ol.protocolId=:protocol");
                    query.setParameter("protocol", protocol);
                    List<SampleEntity> samples = query.getResultList();
                    for (SampleEntity sampleEntity : samples) {
                        String padeklas;
                        String sampleName = sampleEntity.getSampleId();
                        System.out.println("Visuminės drėgmės svėrimas mėginiui : " + sampleName);
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
                            //Double trayWeight = FileControllerService.sverimoPrograma();
                            Double trayWeight = 50.00000;
                            System.out.println("Svoris : 50.00000 g");
                            tme.setTrayWeight(trayWeight);
                            em.persist(te);
                            em.persist(tme);
                        }
                        for (int k = 0; k <= 1; k++) {
                            tme = list.get(k);
                            System.out.println("Įdėkitę " + tme.getTray().getSample().getSampleId() + " mėginį į " + tme.getTray().getTrayId() + " padėklą ir sverkite:");
                            //Double trayWeight2 = FileControllerService.sverimoPrograma();
                            Double trayWeight2 = 50.00000;
                            System.out.println("Svoris : 50.00000 g");
                            tme.setTrayAndSampleWeightBefore(trayWeight2);
                            em.merge(sampleEntity);
                            em.persist(tme);
                            TotalMoistureExcel(tme.getTray(), tme, protocol, 1, null);
                        }
                    }

                    transaction.commit();
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TotalMoistureSecondGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            String padeklas;
            LocalDate laikas;
            for (int i = 1; i < 5000; i++) {
                System.out.println("Skenuokitę padėklą:");
                padeklas = sc.nextLine();
                if(padeklas.equals("Savaitgalis")){
                    laikas = LocalDate.now().minusDays(sc.nextInt());
                }else {
                    laikas = LocalDate.now().minusDays(1);
                }
                if (!padeklas.equals("Baigta")) {
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select te from TrayEntity te where te.trayId=:padeklas AND te.date=:data");
                    query.setParameter("padeklas", padeklas);
                    query.setParameter("data",laikas);
                    TrayEntity te = (TrayEntity) query.getSingleResult();
                    System.out.println("Sverkite padėklą po džiovinimo: ");
                    //Double trayWeight = FileControllerService.sverimoPrograma();
                    Double trayWeight = 50.00000;
                    System.out.println("Svoris : 50.00000 g");
                    List<TotalMoistureEntity> tmeList = te.getTotalMoistureEntities();
                    for(TotalMoistureEntity tme:tmeList) {
                        tme.setTrayAndSampleWeightAfter(trayWeight);
                        double x = FileControllerService.getRandomNumberInRange(0.00005, 0.00030);
                        tme.setTrayAndSampleWeightAfterPlus(trayWeight + x);
                        em.persist(tme);
                        String protocol = tme.getTray().getSample().getOrder().getProtocolId();
                        TotalMoistureExcel(tme.getTray(), tme, protocol, 2, tme.getTray().getTrayId());
                        transaction.commit();
                    }
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TotalMoistureExcel(TrayEntity tray, TotalMoistureEntity tme, String protocol, int sverimoNumeris, String trayId) {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\lei12\\Desktop\\Output\\" + LocalDate.now() + " (VisumineDregme).xlsx";
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

}
