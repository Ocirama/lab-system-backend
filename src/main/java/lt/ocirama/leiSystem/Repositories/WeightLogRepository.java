package lt.ocirama.leiSystem.Repositories;

import com.fazecast.jSerialComm.SerialPort;
import lt.ocirama.leiSystem.Models.OrderEntity;
import lt.ocirama.leiSystem.Models.SampleEntity;
import lt.ocirama.leiSystem.Services.ScaleService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class WeightLogRepository {

    XSSFSheet sheet;
    XSSFWorkbook workbook;
    Scanner sc = new Scanner(System.in);
    ScaleService ss = new ScaleService();
    String path = "C:\\Users\\Justas\\Desktop\\Output\\" + LocalDate.now() + "(Svoriai).xlsx";
    private final EntityManagerFactory entityManagerFactory;

    public WeightLogRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public void WeightLogGenerate() {
        SampleEntity sample = new SampleEntity();
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
            List<SampleEntity> samples = (List<SampleEntity>) session.createQuery("from SampleEntity where protocol_Id=" + protocol).getResultList();
            Row row1 = null;
            for (SampleEntity sampleEntity : samples) {
                for (int i = 1; i <= samples.size(); i++) {
                    row1 = sheet.createRow(i);
                    row1.createCell(0).setCellValue(sampleEntity.getSampleId());
                    System.out.println("Sverkite mėginį : " + sampleEntity.getSampleId());
                    /*SerialPort serialPort = ss.SvarstykliuJungtis();
                    sampleEntity.setSampleWeight(ss.Pasverti(serialPort));*/
                    sampleEntity.setSampleWeight(sc.nextDouble());
                    row1.createCell(1).setCellValue(sampleEntity.getSampleWeight());
                    //ss.ClosePort(serialPort);
                    em.merge(sampleEntity);
                    transaction.commit();
                }
            }
            row1.createCell(2).setCellValue(String.valueOf(LocalDate.now()));

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


