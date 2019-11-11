package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.ReferenceTrayEntity;
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
import java.util.Scanner;

public class ReferenceTrayRepository {
    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;

    public ReferenceTrayRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void ReferenceTrayLogGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            System.out.println("Skenuokite padėklą");
            String padeklas = sc.nextLine();
            ReferenceTrayEntity rte = new ReferenceTrayEntity();
            rte.setReferenceTrayId(padeklas);

            System.out.println("Sverkitę " + padeklas + " padėklą:");
            //Double trayWeight = FileControllerService.sverimoPrograma();
            Double trayWeight = 50.00000;
            System.out.println("Svoris : 50.00000 g");
            rte.setReferenceTrayWeightBefore(trayWeight);
            em.persist(rte);
            transaction.commit();
            ReferenceTrayExcelUpdate(rte, 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReferenceTrayLogSecondGenerate() {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            String padeklas;
            transaction.begin();
            System.out.println("Skenuokitę padėklą:");
            padeklas = sc.nextLine();
            Session session = em.unwrap(Session.class);
            Query query = session.createQuery("from ReferenceTrayEntity  where referenceTrayId=:padeklas");
            query.setParameter("padeklas", padeklas);
            ReferenceTrayEntity rte = (ReferenceTrayEntity) query.getSingleResult();
            System.out.println("Sverkite padėklą po džiovinimo: ");
            //Double trayWeight = FileControllerService.sverimoPrograma();
            Double trayWeight = 50.00000;
            System.out.println("Svoris : 50.00000 g");
            rte.setReferenceTrayWeightAfter(trayWeight);
            em.persist(rte);
            ReferenceTrayExcelUpdate(rte, 2);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReferenceTrayExcelUpdate(ReferenceTrayEntity rte, int sverimoNumeris) {
        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\lei12\\Desktop\\Output\\" + LocalDate.now() + " (VisumineDregme).xlsx";
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
}

