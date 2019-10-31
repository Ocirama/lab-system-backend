package lt.ocirama.labsystembackend.repositories;

import lt.ocirama.labsystembackend.model.OrderLogEntity;
import lt.ocirama.labsystembackend.model.SampleLogEntity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderLogRepository {

    XSSFSheet sheet;
    XSSFWorkbook workbook;
    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;
    String path = "C:\\Users\\lei12\\Desktop\\Output\\Užsakymų Žurnalas.xlsx";

    public OrderLogRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void OrderLogGenerate() {
        try {
            File file = new File(path);
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                sheet = workbook.getSheetAt(sheet.getLastRowNum());
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet();
            }
            EntityManager em = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            for (int i = sheet.getLastRowNum(); i < 5000; i++) {
                System.out.println("Sekantis protokolas? Taip/Ne");
                if (sc.nextLine().equals("Taip")) {
                    Row row = sheet.createRow(i);
                    OrderLogEntity order = new OrderLogEntity();
                    row.createCell(0).setCellValue(i);

                    System.out.println("Protokolo numeris:");
                    order.setProtocolId(sc.nextLine());
                    row.createCell(1).setCellValue(order.getProtocolId());

                    System.out.println("Užsakovas:");
                    order.setCustomer(sc.nextLine());
                    row.createCell(2).setCellValue(order.getCustomer());

                    StringBuilder s = new StringBuilder();
                    String tyrimas;
                    System.out.println("Tyrimai:");
                    do {
                        tyrimas = sc.nextLine();
                        s.append(tyrimas).append(", ");
                    } while (!tyrimas.equals("Baigta"));
                    s.delete(s.length() - 10, s.length() - 1);
                    System.out.println(s);
                    order.setTest(s.toString());
                    String y = s.toString();
                    row.createCell(3).setCellValue(y);

                    System.out.println("Kuro rūšis:");
                    order.setSampleType(sc.nextLine());
                    row.createCell(5).setCellValue(order.getSampleType());

                    System.out.println("Mėginių kiekis:");
                    order.setOrderAmount(sc.nextInt());
                    row.createCell(6).setCellValue(order.getOrderAmount());
                    sc.nextLine();

                    System.out.println("Mėginių Id:");
                    List<SampleLogEntity> list = new ArrayList<>();

                    for (int j = 1; j <= order.getOrderAmount(); j++) {
                        SampleLogEntity se = new SampleLogEntity();
                        list.add(se);
                        se.setSampleId(sc.nextLine());
                        se.setOrder(order);
                    }
                    order.setSamples(list);
                    transaction.begin();
                    try {
                        em.persist(order);
                        transaction.commit();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    break;
                }
            }
            FileOutputStream outFile = new FileOutputStream(new File(path));
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





