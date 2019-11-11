package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.AshEntity;
import lt.ocirama.labsystembackend.model.SampleEntity;
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

public class AshRepository {

    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;

    public AshRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void AshLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            String padeklas;
            for (int i = 1; i < 5000; i++) {
                System.out.println("Skenuokitę padėklą:");
                padeklas = sc.nextLine();
                if (!padeklas.equals("Baigta")) {
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("Select te from TrayEntity te where te.trayId=:tray");
                    query.setParameter("tray", padeklas);
                    TrayEntity tray = (TrayEntity) query.getSingleResult();
                    String indukas;

                    List<AshEntity> list = new ArrayList<>();
                    AshEntity ae;
                    for (int j = 1; j <= 2; j++) {
                        ae = new AshEntity();
                        System.out.println("Peleningumo svėrimas mėginiui : " + tray.getSample().getSampleId());
                        System.out.println("Skenuokite " + j + "-ojo induko barkodą mėginiui  " + tray.getSample().getSampleId());
                        indukas = sc.nextLine();
                        ae.setTray(tray);
                        list.add(ae);
                        System.out.println("Sverkite induką " + indukas);
                        ae.setDishId(indukas);
                        //Double dishWeight = FileControllerService.sverimoPrograma();
                        Double dishWeight = 50.00000;
                        System.out.println("Svoris : 50.00000 g");
                        ae.setDishWeight(dishWeight);
                        em.persist(ae);
                    }
                    for (int k = 0; k <= 1; k++) {
                        ae = list.get(k);
                        System.out.println("Įdėkitę " + ae.getTray().getSample().getSampleId() + " mėginį į " + tray.getSample().getSampleId() + " induką ir sverkite:");
                        //Double trayWeight2 = FileControllerService.sverimoPrograma();
                        Double trayWeight2 = 50.00000;
                        System.out.println("Svoris : 50.00000 g");
                        ae.setDishAndSampleWeightBefore(trayWeight2);
                        em.persist(ae);
                        AshExcelUpdate(ae, ae.getTray().getSample().getOrder().getProtocolId(), 1);
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

    public void AshSecondGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            for (int i = 1; i < 5000; i++) {
                System.out.println("Skenuokitę induką:");
                String padeklas = sc.nextLine();
                if (!padeklas.equals("Baigta")) {
                    transaction.begin();
                    Session session = em.unwrap(Session.class);
                    Query query = session.createQuery("from AshEntity where dishId=:padeklas");
                    query.setParameter("padeklas", padeklas);
                    List<AshEntity> samples = query.getResultList();
                    for (AshEntity ae :samples) {
                    System.out.println("Sverkite padėklą po džiovinimo: ");
                    //Double trayWeight = FileControllerService.sverimoPrograma();
                        Double trayWeight = 50.00000;
                        System.out.println("Svoris : 50.00000 g");
                        ae.setDishAndSampleWeightAfter(trayWeight);
                        em.persist(ae);
                        AshExcelUpdate(ae, ae.getTray().getSample().getOrder().getProtocolId(), 2);
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

    public void AshExcelUpdate(AshEntity ae, String protocol, int sverimoNumeris) {

        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\Justas\\Desktop\\Output\\" + LocalDate.now() + " (Peleningumas).xlsx";
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
}

