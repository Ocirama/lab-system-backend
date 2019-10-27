package lt.ocirama.leiSystem.Repositories;

import com.fazecast.jSerialComm.SerialPort;
import lt.ocirama.leiSystem.Models.OrderEntity;
import lt.ocirama.leiSystem.Models.SampleEntity;
import lt.ocirama.leiSystem.Services.ScaleService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeightLogRepository {

    XSSFSheet sheet;
    XSSFWorkbook workbook;
    Scanner sc = new Scanner(System.in);
    ScaleService ss = new ScaleService();
    String path = "C:\\Users\\Justas\\Desktop\\Lab-system output\\" + LocalDate.now() + "(Svoriai).xlsx";
    private final EntityManagerFactory entityManagerFactory;

    public WeightLogRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public SampleEntity WeightLogGenerate() {
        SampleEntity sample = new SampleEntity();
        OrderEntity order = new OrderEntity();
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
            rowhead.createCell(0).setCellValue("Mėginio Nr.");
            rowhead.createCell(1).setCellValue("Svoris, g");
            rowhead.createCell(2).setCellValue("Data");
            Session session = em.unwrap(Session.class);
            MultiIdentifierLoadAccess<SampleEntity> multiLoadAccess = session.byMultipleIds(SampleEntity.class);
            List<SampleEntity> samples = multiLoadAccess.multiLoad(1L, 2L, 3L);
            session.createQuery("from sample where protocol_Id="+protocol);
           /* List<SampleEntity> samples = entityManagerFactory.createEntityManager().createQuery("from sample where protocol_Id =" + protocol, SampleEntity.class).getResultList();*/

            for (SampleEntity sampleEntity : samples) {
                System.out.println(sample);
            }

            for (int i = 1; i < 5; i++) {
                //order.getProtocolId().equals(protocol);
                System.out.println("-------");
                System.out.println();
                System.out.println("Sekantis mėginys? Taip/Ne");
                String TaipNe = sc.nextLine();
                if (TaipNe.equals("Taip")) {
                    System.out.println("Skenuokite mėginio ID:");
                    //weightLog.setSampleId(sc.nextLine());
                    Row row1 = sheet.createRow(i);
                    //row1.createCell(0).setCellValue(weightLog.getSampleId());
                    System.out.println("Mėginio masės svėrimas:");
                    SerialPort serialPort = ss.SvarstykliuJungtis();
                    // weightLog.setSampleWeight(ss.Pasverti(serialPort));
                    //row1.createCell(1).setCellValue(weightLog.getSampleWeight());
                    ss.ClosePort(serialPort);
                    row1.createCell(2).setCellValue(String.valueOf(LocalDate.now()));
                } else if (TaipNe.equals("Ne")) {
                    break;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(SampleEntity sample) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            em.persist(sample);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
}
