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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderLogRepository {

    Scanner sc = new Scanner(System.in);
    private final EntityManagerFactory entityManagerFactory;

    public OrderLogRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public OrderLogEntity OrderLogUpdate() {

        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        OrderLogEntity order = new OrderLogEntity();
        for (int i = 1; i < 5000; i++) {
            System.out.println("Sekantis protokolas? Taip/Ne");
            if (sc.nextLine().equals("Taip")) {
                System.out.println("Protokolo numeris:");
                order.setProtocolId(sc.nextLine());

                System.out.println("Užsakovas:");
                order.setCustomer(sc.nextLine());

                System.out.println("Tyrimai:");
                StringBuilder s = new StringBuilder();
                String tyrimas;
                do {
                    tyrimas = sc.nextLine();
                    s.append(tyrimas).append(", ");
                } while (!tyrimas.equals("Baigta"));
                s.delete(s.length() - 10, s.length() - 1);
                System.out.println(s);
                order.setTest(s.toString());

                System.out.println("Kuro rūšis:");
                order.setSampleType(sc.nextLine());

                System.out.println("Mėginių kiekis:");
                order.setOrderAmount(sc.nextInt());
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
        return order;
    }

    public void OrderLogExcel(OrderLogEntity order) {

        XSSFSheet sheet;
        XSSFWorkbook workbook;
        String path = "C:\\Users\\lei12\\Desktop\\Output\\Užsakymų Žurnalas.xlsx";
        File file = new File(path);
        try {
            if (file.exists()) {
                FileInputStream fsip = new FileInputStream(path);
                workbook = new XSSFWorkbook(fsip);
                sheet = workbook.getSheet(sheet.getLastRowNum());
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet();
            }

                Row row = sheet.createRow(sheet.getLastRowNum());
                row.createCell(0).setCellValue();
                row.createCell(1).setCellValue(order.getProtocolId());
                row.createCell(2).setCellValue(order.getCustomer());
                row.createCell(3).setCellValue(order.getTest());
                row.createCell(5).setCellValue(order.getSampleType());
                row.createCell(6).setCellValue(order.getOrderAmount());

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





